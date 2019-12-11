package com.lx.lxyd;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.lx.lxyd.constant.AppConstant;
import com.lx.lxyd.net.AndroidBase;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;





/*
 *自定义Application
 * 用于初始化各种数据以及服务
 *  */

public class MyApplication extends Application {
    /**
     * 应用实例
     **/
    private static MyApplication instance;
    public static Context mContext;
    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        LitePal.initialize(this);
        AndroidBase.init(this, AppConstant.INSTANCE.getBASE_URL(), AppConstant.INSTANCE.getBASE_URL());
    }

}
