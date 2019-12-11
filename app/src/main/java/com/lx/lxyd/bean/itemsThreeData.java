package com.lx.lxyd.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2019/12/7-22:55
 * Author: fushuaige
 */
public class itemsThreeData {
    private String class_name;
    private String res_code;
    private List<dataInfo> items;

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public List<dataInfo> getItems() {
        return items;
    }

    public void setItems(List<dataInfo> items) {
        this.items = items;
    }
}
