package com.suiyu.lab.framework.json.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class GsonFactory {
    private GsonFactory(){

    }
    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }
}
