package com.lx.lxyd.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2019/12/7-22:54
 * Author: fushuaige
 */
public class itemsTwoData {
    private String code;
    private String name;
    private String id;
    private List<itemsThreeData> items;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<itemsThreeData> getItems() {
        return items;
    }

    public void setItems(List<itemsThreeData> items) {
        this.items = items;
    }
}
