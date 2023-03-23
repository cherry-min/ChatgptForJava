package com.github.cherryNo1.exception;


import com.github.cherryNo1.utils.Error;

/**
 * @author maqingbo
 * @date 2023/3/11 14:48
 * @email qingbo.my@gmail.com
 */
public class BaseException extends RuntimeException {
    private final Integer statusCode;
    private final String msg;

    public BaseException(Integer code, String msg) {
        super(msg);
        this.statusCode = code;
        this.msg = msg;
    }

    public BaseException(Error error) {
        super(error.getMsg());
        this.statusCode = error.getCode();
        this.msg = error.getMsg();
    }
}
