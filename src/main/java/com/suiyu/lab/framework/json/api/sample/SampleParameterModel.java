package com.suiyu.lab.framework.json.api.sample;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class SampleParameterModel {
    private String inner1;
    private String inner2;

    public String getInner1() {
        return inner1;
    }

    public void setInner1(String inner1) {
        this.inner1 = inner1;
    }

    public String getInner2() {
        return inner2;
    }

    public void setInner2(String inner2) {
        this.inner2 = inner2;
    }

    @Override
    public String toString() {
        return "SampleParameterModel=[" +
                "inner1="+inner1 + "," +
                "inner2="+inner2 +
                "]";
    }
}
