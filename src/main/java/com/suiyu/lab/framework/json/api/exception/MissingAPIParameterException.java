package com.suiyu.lab.framework.json.api.exception;

/**
 * Created by BingyuYin on 2016/11/19.
 */
public class MissingAPIParameterException extends APIModelParseException {
    private static final long serialVersionUID = -4228111619606667907L;
    public MissingAPIParameterException(String msg) {
        super(msg);
    }
}
