package com.suiyu.lab.framework.json.api.sample;

import com.suiyu.lab.framework.json.api.annotation.API;
import com.suiyu.lab.framework.json.api.annotation.APIParameter;
import com.suiyu.lab.framework.json.api.annotation.APIComponent;

import java.util.List;

/**
 * Created by BingyuYin on 2016/11/13.
 */
@APIComponent("sampleService")
public class SampleAPIComponent {
    @API("sampleTestA")
    public void sampleApiTestA(@APIParameter("param1") String param1, @APIParameter("param2")SampleParameterModel param2) {
        System.out.println("api component api invoke sample testA: param1= " + param1 + ",\n param2= "+param2);
    }

    @API("sampleTestA")
    public void sampleApiTestAA(@APIParameter("param1")String param1, @APIParameter("param3")String param3, @APIParameter("param4")String param4) {
        System.out.println("api component api invoke sample testAA: param1=" + param1 + ",\nparam3=" + param3 + ",\nparam4=" + param4);
    }

    @API("sampleTestA")
    public void sampleApiTestAAA(@APIParameter("param5")String param5,
            @APIParameter("param1")String param1, @APIParameter("param3")String param3, @APIParameter("param4")String param4) {
        System.out.println("api component api invoke sample testAAA: param1=" + param1 + ",\nparam3=" + param3 + ",\nparam4=" +
                "" + param4 + "\nparam5=" + param5);
    }
    @API("sampleTestA")
    public void sampleApiTestAAAA(@APIParameter("param5")String param5, @APIParameter("param2")SampleParameterModel param2,@APIParameter(value = "nonRequired", required = false)String nonRequired,
                                 @APIParameter("param1")String param1, @APIParameter("param3")String param3, @APIParameter("param4")String param4,
                                  @APIParameter("paramVar1")String paramVar1, @APIParameter("paramVar2")String paramVar2) {
        System.out.println("api component api invoke sample testAAAA:\nparam1=" + param1 + ",\nparam3=" + param3 + ",\nparam4=" +
                "" + param4 + ",\nparam5=" + param5 + ",\nparam2=" + param2.toString() + ",\nnonRequired="+nonRequired +
                ",\nparamVar1=" + paramVar1 + ",\nparamVar2=" + paramVar2);
    }

    @API("sampleTestB")
    public void sampleApiTestB(@APIParameter("list")List<String> list) {
        if (list == null || list.isEmpty()) {
            return ;
        }
        for (String var: list) {
            System.out.println("api component api invoke sample testB: "+ var);
        }
    }

    @API("sampleTestC")
    public void sampleApiTestC(@APIParameter("list")List<List<SampleParameterModel>> list) {
        if (list == null || list.isEmpty()) {
            return ;
        }
        for (List<SampleParameterModel> varList: list) {
            for (SampleParameterModel var : varList) {
                System.out.println("api component api invoke sample testC: " + var.toString());
            }
        }
    }
}
