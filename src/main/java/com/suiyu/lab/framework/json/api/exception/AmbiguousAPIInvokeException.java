package com.suiyu.lab.framework.json.api.exception;

/**
 * Created by BingyuYin on 2016/11/19.
 */
public class AmbiguousAPIInvokeException extends APIInvokeException {

    private static final long serialVersionUID = -8065502266563915382L;
    public AmbiguousAPIInvokeException(String msg) {
        super(msg);
    }
}
