package com.suiyu.lab.framework.json.api.model;

import java.lang.reflect.Method;

/**
 * Created by BingyuYin on 2016/11/27.
 */
public class InvokeCandidate {
    private Method method;
    private Object instance;
    private Object[] args;
    private int gap = -1;
    public InvokeCandidate(Method method, Object instance, Object[] args, int gap) {
        this.method = method;
        this.instance = instance;
        this.args = args;
        this.gap = gap;
    }

    public int getGap() {
        return gap;
    }


    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    public Object[] getArgs() {
        return args;
    }
}
