package com.suiyu.lab.framework.json.api.sample.model;

import com.suiyu.lab.framework.json.api.annotation.APIParametersDefinition;

import java.util.Map;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class Content {
    private Action action;
    @APIParametersDefinition
    private Map<String, Object> parameters;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
