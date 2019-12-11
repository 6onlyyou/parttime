package com.lx.lxyd.bean;

import org.litepal.crud.DataSupport;

/**
 * Description:
 * Data：2019/12/3-19:57
 * Author: fushuaige
 */
public class allListData extends DataSupport {

    private String code;
    private String name;
    private String mainId;
    private String topId;
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


    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
