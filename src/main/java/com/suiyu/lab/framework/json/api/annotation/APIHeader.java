package com.suiyu.lab.framework.json.api.annotation;

import java.lang.annotation.*;

/**
 * Created by BingyuYin on 2016/11/27.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(APIHeaders.class)
public @interface APIHeader {
    String value() default "";
}
