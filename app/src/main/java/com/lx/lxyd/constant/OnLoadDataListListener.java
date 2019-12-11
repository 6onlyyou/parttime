package com.lx.lxyd.constant;


public interface OnLoadDataListListener<T,String> {
    void onSuccess(T data);
    void onSuccess(T data, String type);
    void onFailure(Throwable e);
}