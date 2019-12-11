package com.heixiu.errand.net

import com.lx.lxyd.net.AndroidBase
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Created by FU on 2019/1/4.
 */
object RetrofitFactory {
    private  var retrofit: Retrofit? = null
    private var isHttps: Boolean = true

    fun init() {
        retrofit = Retrofit.Builder()
                .baseUrl(if (isHttps) AndroidBase.BASE_HTTPS_URL else AndroidBase.BASE_HTTP_URL)
                .client(OkHttpFactory.getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <T> getRetrofit(service: Class<T>): T {
        return retrofit!!.create(service)
    }

}