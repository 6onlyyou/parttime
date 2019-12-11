package com.heixiu.errand.net

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by FU on 2019/1/4.
 */

object OkHttpFactory {
    private val DEFAULT_TIME_OUT: Long = 30000
    private lateinit var okHttpClient: OkHttpClient

    fun OkHttpClientManager() {

    }

    fun init(context: Context) {
        val cacheSize = 5242880
        val cache = Cache(context.cacheDir, cacheSize.toLong())
        val okHttpClientBuilder = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(RequestInterceptor(context))
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        okHttpClient = okHttpClientBuilder.build()
    }

    fun getOkHttpClient(): OkHttpClient {
        return okHttpClient
    }
}
