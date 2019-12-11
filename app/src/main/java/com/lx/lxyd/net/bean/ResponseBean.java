package com.lx.lxyd.net.bean;

/**
 * Created by FU on 2019/1/4.
 */

public class ResponseBean<T> {

    /**
     * 返回代码，0 正确，负数 程序异常，正数 校验错误
     */
    private int state;
    private int code;
    private boolean success;
    /**
     * 额外信息
     */
    private String msg;
    /**
     * 返回结果
     */
    private T data;

    public ResponseBean() {
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseBean(int state, String message, T data) {
        this.state = state;
        this.msg = message;
        this.data = data;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (state == 0) {
            if (msg == null) {
                if (data != null) {
                    return data.toString();
                }
            } else {
                if (data != null) {
                    return msg + ": " + data.toString();
                } else {
                    return msg;
                }
            }
        } else {
            return state + " on " + msg;
        }
        return null;
    }
}
