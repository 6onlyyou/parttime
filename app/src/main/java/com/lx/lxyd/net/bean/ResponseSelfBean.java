package com.lx.lxyd.net.bean;

/**
 * Created by FU on 2019/1/4.
 */

public class ResponseSelfBean<T> {

    /**
     * 返回代码，0 正确，负数 程序异常，正数 校验错误
     */
    private int code;
    private boolean success;
    /**
     * 返回结果
     */
    private T data;

    public ResponseSelfBean() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseSelfBean(int state, String message, T data) {
        this.data = data;
    }





    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
