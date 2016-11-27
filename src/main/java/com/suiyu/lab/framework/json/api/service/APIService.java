package com.suiyu.lab.framework.json.api.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.gson.*;
import com.suiyu.lab.framework.json.api.annotation.API;
import com.suiyu.lab.framework.json.api.annotation.APIComponent;
import com.suiyu.lab.framework.json.api.annotation.APIParameter;
import com.suiyu.lab.framework.json.api.exception.APIModelParseException;
import com.suiyu.lab.framework.json.api.model.InvokeCandidate;
import com.suiyu.lab.framework.json.api.model.InvokeModel;
import com.suiyu.lab.framework.json.api.exception.APIInvokeException;
import com.suiyu.lab.framework.json.api.exception.AmbiguousAPIInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class APIService {
    private Logger logger = LoggerFactory.getLogger(APIService.class);
    private String basePackage = null;
    private ClassLoader classLoader = APIService.class.getClassLoader();
    private APIParserService parserService = APIParserService.create();

    private APIService(String basePackage) {
        this.basePackage = basePackage;
    }

    private APIService(ClassLoader classLoader, String basePackage) {
        this(basePackage);
        this.classLoader = classLoader;
    }

    public static APIService create(String basePackage) {
        return APIService.create(APIService.class.getClassLoader(), basePackage);
    }

    public static APIService create(ClassLoader classLoader, String basePackage) {
        return new APIService(classLoader, basePackage);
    }

    public void setParserService(APIParserService parserService) {
        if (null != parserService) {
            this.parserService = parserService;
        }
    }

    public void setBasePackage (String basePackage) {
        if (null != basePackage) {
            this.basePackage = basePackage;
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        if (null != classLoader) {
            this.classLoader = classLoader;
        }
    }

    public void invoke(String var, Class<?> clazz) throws APIModelParseException, APIInvokeException{
        InvokeModel invokeModel = parserService.parse(var, clazz);
        if (null == invokeModel) {
            throw new APIModelParseException("Failed to parse the api.");
        }
        doInvoke(invokeModel.getServiceComponent(), invokeModel.getApi(), invokeModel.getArgsMap());
    }

    private void doInvoke(String serviceComponentName, String apiName, Map<String, Object> source) throws APIInvokeException{
        try {
            ClassPath classPath = ClassPath.from(classLoader);
            ImmutableSet<ClassPath.ClassInfo> classInfoSet;
            if (null == basePackage) {
                classInfoSet = classPath.getTopLevelClasses();
            } else {
                classInfoSet = classPath.getTopLevelClassesRecursive(basePackage);
            }
            List<InvokeCandidate> candidateList = new ArrayList<InvokeCandidate>();
            for (ClassPath.ClassInfo classInfo: classInfoSet) {
                Class clazz = Class.forName(classInfo.getName());
                if (!clazz.isAnnotationPresent(APIComponent.class)) {
                    continue;
                }
                final APIComponent apiComponent = (APIComponent)clazz.getAnnotation(APIComponent.class);
                if (!apiComponent.value().trim().equalsIgnoreCase(serviceComponentName.trim())) {
                    continue;
                }
                Method[] methods = clazz.getDeclaredMethods();

                for (Method method: methods) {
                    if (!method.isAnnotationPresent(API.class)) {
                        continue;
                    }
                    if (!method.getAnnotation(API.class).value().trim().equalsIgnoreCase(apiName.trim())) {
                        continue;
                    }
                    Parameter[] parameters = method.getParameters();
                    if (parameters.length == 0) {
                        candidateList.add(new InvokeCandidate(method, clazz.newInstance(), null, source.size()));
                        continue;
                    }
                    Object[] targetArgs = new Object[parameters.length];
                    int cur = 0;
                    Map<String, Object> sourceCpy = new HashMap<String, Object>();
                    sourceCpy.putAll(source);
                    for (Parameter parameter: parameters) {
                        if (!parameter.isAnnotationPresent(APIParameter.class)) {
                            break;
                        }
                        final APIParameter param = parameter.getAnnotation(APIParameter.class);
                        String name = param.value();
                        if (!sourceCpy.containsKey(name) && param.required()) {
                            break;
                        }
                        Object argObj = sourceCpy.get(name);
                        try {
                            if (null == argObj) {
                                targetArgs[cur++] = null;
                            } else {
                                targetArgs[cur++] = parserService.parseParameter(argObj, parameter);
                            }
                            sourceCpy.remove(name);
                        } catch (JsonSyntaxException e) {
                            logger.error("Exception occurs in api invoke: ", e.getMessage());
                            break;
                        }
                    }
                    if (cur != parameters.length) {
                        // not all api parameters consumed
                        continue;
                    }
                    // all api parameters consumed
                    candidateList.add(new InvokeCandidate(method, clazz.newInstance(), targetArgs, source.size() - parameters.length));
                }
            }
            if (candidateList.size() == 0) {
                logger.warn("No invoke method found for {}/{}.", serviceComponentName,apiName);
                return ;
            }
            candidateList.sort(new InvokeCandidateComparator());
            InvokeCandidate invokeCandidate = candidateList.get(0);
            if (candidateList.size() > 1 && candidateList.get(1).getGap() == invokeCandidate.getGap()) {
                throw new AmbiguousAPIInvokeException("Ambiguous api invoke for: "+ serviceComponentName + "/" + apiName);
            }
            invokeCandidate.getMethod().invoke(invokeCandidate.getInstance(), invokeCandidate.getArgs());
        } catch (Exception e) {
            throw new APIInvokeException(e.getMessage());
        }
    }

    private class InvokeCandidateComparator implements Comparator<InvokeCandidate> {
        public int compare(InvokeCandidate o1, InvokeCandidate o2) {
            return o1.getGap() - o2.getGap();
        }
    }
}
