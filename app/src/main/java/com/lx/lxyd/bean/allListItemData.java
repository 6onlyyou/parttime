package com.lx.lxyd.bean;

import org.litepal.crud.DataSupport;

/**
 * Description:
 * Data：2019/12/3-19:57
 * Author: fushuaige
 */
public class allListItemData extends DataSupport {

    private String calssName;
    private String topId;

    public String getCalssName() {
        return calssName;
    }

    public void setCalssName(String calssName) {
        this.calssName = calssName;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }
}
