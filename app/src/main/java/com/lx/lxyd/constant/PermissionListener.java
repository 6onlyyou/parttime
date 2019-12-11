package com.lx.lxyd.constant;

import java.util.List;

/**
 * Description:
 * Data：2019/12/4-11:41
 * Author: fushuaige
 */
public interface  PermissionListener {
    void granted();
    void denied(List<String> deniedList);
}
