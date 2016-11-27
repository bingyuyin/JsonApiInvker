package com.suiyu.lab.framework.json.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * APIParameters definition.
 * Support Map<String, Object>, Pojo class which contains getter method
 * Created by BingyuYin on 2016/11/16.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface APIParametersDefinition {
    boolean required () default true;
}
