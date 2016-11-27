package com.suiyu.lab.framework.json.api.sample.model;

import com.suiyu.lab.framework.json.api.annotation.APIDefinition;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class Action {
    @APIDefinition()
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
