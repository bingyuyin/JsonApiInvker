package com.suiyu.lab.framework.json.api.exception;

/**
 * Created by yinbing on 11/29/2016.
 */
public class MissingAPIHeaderException extends APIModelParseException {

    private static final long serialVersionUID = 5767474635703477206L;
    public MissingAPIHeaderException(String msg) {
        super(msg);
    }
}
