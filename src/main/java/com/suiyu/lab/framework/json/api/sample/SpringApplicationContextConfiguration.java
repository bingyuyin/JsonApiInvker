package com.suiyu.lab.framework.json.api.sample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by BingyuYin on 2016/11/30.
 */
@Configuration
@ComponentScan(basePackages = "com.suiyu.lab.framework.json.api",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.context.annotation.Configuration.class)})
public class SpringApplicationContextConfiguration {
}
