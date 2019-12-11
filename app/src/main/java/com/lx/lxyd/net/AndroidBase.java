package com.lx.lxyd.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.heixiu.errand.net.OkHttpFactory;
import com.heixiu.errand.net.RetrofitFactory;

import org.jetbrains.annotations.NotNull;

/**
 * Created by FU on 2019/1/4.
 */
public class AndroidBase {
    private static final String TAG = "AndroidBase";
    @NotNull
    public static String BASE_HTTPS_URL;
    @NotNull
    public static String BASE_HTTP_URL;
    public static Context mContext;

    public AndroidBase() {

    }

    public static void init(Context context, String baseHttpUrl, String baseHttpsUrl) {
        String errorMessage = checkStatus();
        if (!TextUtils.isEmpty(errorMessage)) {
            Log.i(TAG, "init: " + errorMessage);
        }
        mContext = context;
        BASE_HTTP_URL = baseHttpUrl;
        BASE_HTTPS_URL = baseHttpsUrl;
        OkHttpFactory.INSTANCE.init(context);
        RetrofitFactory.INSTANCE.init();
    }

    private static String checkStatus() {
        String result = "";
        if (mContext == null) {
            result = "context is null";
        } else if (TextUtils.isEmpty(BASE_HTTP_URL)) {
            result = "http path is null";
        } else if (TextUtils.isEmpty(BASE_HTTPS_URL)) {
            result = "https path is null";
        }

        return result;
    }
}
