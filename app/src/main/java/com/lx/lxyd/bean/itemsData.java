package com.lx.lxyd.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2019/12/7-21:26
 * Author: fushuaige
 */
public class itemsData {
    private String code;
    private String name;
    private String id;
    private List<itemsTwoData> items;

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

    public List<itemsTwoData> getItems() {
        return items;
    }

    public void setItems(List<itemsTwoData> items) {
        this.items = items;
    }
}
