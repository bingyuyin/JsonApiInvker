package com.suiyu.lab.framework.json.api.model;

import java.util.Map;

/**
 * Created by BingyuYin on 2016/11/27.
 */
public class InvokeModel {
    private String api;
    private String serviceComponent;
    private Map<String, Object> argsMap;

    public InvokeModel (String serviceComponent,String api, Map<String, Object> argsMap) {
        this.api = api;
        this.serviceComponent = serviceComponent;
        this.argsMap = argsMap;
    }

    public String getApi() {
        return api;
    }

    public String getServiceComponent() {
        return serviceComponent;
    }

    public Map<String, Object> getArgsMap() {
        return argsMap;
    }
}
