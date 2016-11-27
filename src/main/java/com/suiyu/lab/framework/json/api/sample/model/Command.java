package com.suiyu.lab.framework.json.api.sample.model;

import com.suiyu.lab.framework.json.api.annotation.APIComponentDefinition;
import com.suiyu.lab.framework.json.api.annotation.APIParameterDefinition;
import com.suiyu.lab.framework.json.api.annotation.APIParametersDefinition;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class Command {
    @APIComponentDefinition
    private String command;
    private Content content;

    @APIParametersDefinition
    private Parameters parameters;

    @APIParameterDefinition(required = false)
    private String singleParameter;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public String getSingleParameter() {
        return singleParameter;
    }

    public void setSingleParameter(String singleParameter) {
        this.singleParameter = singleParameter;
    }
}
