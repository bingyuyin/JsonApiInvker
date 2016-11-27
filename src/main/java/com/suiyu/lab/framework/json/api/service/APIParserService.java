package com.suiyu.lab.framework.json.api.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.suiyu.lab.framework.json.api.annotation.APIComponentDefinition;
import com.suiyu.lab.framework.json.api.annotation.APIDefinition;
import com.suiyu.lab.framework.json.api.annotation.APIParameterDefinition;
import com.suiyu.lab.framework.json.api.annotation.APIParametersDefinition;
import com.suiyu.lab.framework.json.api.exception.APIModelParseException;
import com.suiyu.lab.framework.json.api.exception.MissingAPIParameterException;
import com.suiyu.lab.framework.json.api.model.InvokeModel;
import com.suiyu.lab.framework.json.api.utils.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BingyuYin on 2016/11/27.
 */
public class APIParserService {
    private Logger logger = LoggerFactory.getLogger(APIParserService.class);
    private Gson gson = GsonFactory.create();

    private APIParserService() {
    }

    private APIParserService(Gson gson) {
        if (null != gson) {
            this.gson = gson;
        }
    }

    public static APIParserService create() {
        return new APIParserService();
    }

    public static APIParserService create(Gson gson) {
        return new APIParserService(gson);
    }

    public Object parseParameter(Object argObj, Parameter parameter) {
        JsonElement element = gson.toJsonTree(argObj);
        return gson.fromJson(element, parameter.getParameterizedType());
    }
    public InvokeModel parse(String var, Class<?> clazz) throws APIModelParseException {
        Object object = gson.fromJson(var, clazz);
        List<String> apiComponentList = new ArrayList<String>();
        List<String> apiList = new ArrayList<String>();
        Map<String, Object> apiParameters = new HashMap<String, Object>();
        doParseDefinitionModel(object, apiComponentList, apiList, apiParameters);
        if (apiComponentList.size() > 1) {
            throw new APIModelParseException("Multiple api components found in: " + var);
        } else if (apiComponentList.size() == 0) {
            throw new APIModelParseException("No api component found in: " + var);
        }
        if (apiList.size() > 1) {
            throw new APIModelParseException("Multiple apis found in: " + var);
        } else if (apiList.size() == 0) {
            throw new APIModelParseException("No api found in: " + var);
        }
        return new InvokeModel(apiComponentList.get(0), apiList.get(0), apiParameters);
    }
    private void doParseDefinitionModel(Object instance,
                                        List<String> apiComponentList,
                                        List<String> apiList,
                                        Map<String, Object> apiParameters) throws APIModelParseException{
        if (null == instance) {
            return ;
        }
        Class clazz = instance.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(APIComponentDefinition.class)) {
                if (field.getType().equals(String.class)) {
                    try {
                        Method method = reflectGetFieldMethod(clazz, field);
                        String apiComponent = (String)method.invoke(instance);
                        if (apiComponent != null && !apiComponent.isEmpty()) {
                            apiComponentList.add(apiComponent);
                        }
                    } catch (Exception e) {
                        logger.warn("Parse api component definition failed.");
                    }
                } else {
                    logger.warn("Api component definition should be string type");
                }
            }

            if (field.isAnnotationPresent(APIDefinition.class)) {
                if (field.getType().equals(String.class)) {
                    try {
                        Method method = reflectGetFieldMethod(clazz, field);
                        String api = (String)method.invoke(instance);
                        if (api != null && !api.isEmpty()) {
                            apiList.add(api);
                        }
                    } catch (Exception e) {
                        logger.warn("Parse api definition failed.");
                    }
                } else {
                    logger.warn("Api definition should be string type");
                }
            }
            if (field.isAnnotationPresent(APIParametersDefinition.class)) {
                Object fieldInstance = null;
                try {
                    fieldInstance = reflectGetFieldMethod(clazz,field).invoke(instance);
                } catch (Exception e) {
                    logger.warn("Parse api parameters definition failed.");
                }
                if (null == fieldInstance && field.getAnnotation(APIParametersDefinition.class).required()) {
                    logger.error("Missing api parameters {}.", field.getName());
                    throw new MissingAPIParameterException("Missing api parameters: " + field.getName() + " in request");
                }
                if (field.getType().equals(Map.class)) {
                    Type[] types = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
                    if (types[0].equals(String.class) && types.length == 2) {
                        try {
                            apiParameters.putAll((Map)fieldInstance);
                        } catch (Exception e) {
                            logger.warn("Parse api parameters failed.");
                        }
                    }
                } else {
                    Method[] varMethods = field.getType().getDeclaredMethods();
                    try {
                        if (null != fieldInstance) {
                            for (Method varMethod : varMethods) {
                                String methodName = varMethod.getName();
                                if (methodName.startsWith("get") && methodName.length() > 4) {
                                    apiParameters.put(methodName.substring(3, 4).toLowerCase() + methodName.substring(4), varMethod.invoke(fieldInstance));
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.warn("Parse api parameters failed.");
                    }
                }
            }

            if (field.isAnnotationPresent(APIParameterDefinition.class)) {
                Object varObj = null;
                try {
                    Method varMethod = reflectGetFieldMethod(clazz, field);
                    varObj = varMethod.invoke(instance);
                } catch (Exception e) {
                    logger.error("Exception occurs while parse api parameter: {}", field.getName());
                }
                if (null == varObj && field.getAnnotation(APIParameterDefinition.class).required()) {
                    throw new MissingAPIParameterException("Missing api parameter: " + field.getName());
                }
                apiParameters.put(field.getName(),varObj);

            }

            Object instanceVar = null;
            try {
                Method method = reflectGetFieldMethod(clazz, field);
                instanceVar = method.invoke(instance);
            } catch (Exception e) {
                // ignore the message
            }
            if (instanceVar != null) {
                doParseDefinitionModel(instanceVar, apiComponentList, apiList, apiParameters);
            }
        }
    }

    public Method reflectGetFieldMethod(Class<?> clazz, Field field) throws Exception{
        String fieldName = field.getName();
        Method method = clazz.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        method.setAccessible(true);
        return method;
    }
}
