package com.suiyu.lab.framework.json.api.sample;

import com.suiyu.lab.framework.json.api.exception.APIModelParseException;
import com.suiyu.lab.framework.json.api.sample.model.Command;
import com.suiyu.lab.framework.json.api.service.APIService;
import com.suiyu.lab.framework.json.api.exception.APIInvokeException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by BingyuYin on 2016/11/13.
 */
public class TestApplication {
    public static void main(String[] args) {
        APIService apiService = APIService.create("com.suiyu.lab.framework.json.api");
        try {
            String requestA = IOUtils.toString(TestApplication.class.getClassLoader().getResourceAsStream("requestModelA.json"));
            String requestB = IOUtils.toString(TestApplication.class.getClassLoader().getResourceAsStream("requestModelB.json"));
            String requestC = IOUtils.toString(TestApplication.class.getClassLoader().getResourceAsStream("requestModelC.json"));
            try {
                apiService.invoke(requestA, Command.class);
            } catch (APIModelParseException e) {
                e.printStackTrace();
            } catch (APIInvokeException e) {
                e.printStackTrace();
            }
            try {
                apiService.invoke(requestB, Command.class);
            } catch (APIModelParseException e) {
                e.printStackTrace();
            } catch (APIInvokeException e) {
                e.printStackTrace();
            }
            try {
                apiService.invoke(requestC, Command.class);
            } catch (APIModelParseException e) {
                e.printStackTrace();
            } catch (APIInvokeException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
