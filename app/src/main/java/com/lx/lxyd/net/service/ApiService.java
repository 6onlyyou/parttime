package com.lx.lxyd.net.service;


import com.lx.lxyd.bean.homeData;
import com.lx.lxyd.net.bean.ResponseBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by FU on 2019//4.
 */

public interface ApiService {
    @GET("zhonglian/api/findResources?size=6")
    Observable<ResponseBean<ArrayList<homeData>>> getData();

}

