package com.lx.lxyd.bean;

import org.litepal.crud.DataSupport;

/**
 * Description:
 * Data：2019/12/3-19:57
 * Author: fushuaige
 */
public class maintainData extends DataSupport {

    private String mainCode;
    private String mainName;
    private String mainId;

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
