package com.lx.lxyd.net;

import android.util.Log;

import com.lx.lxyd.net.bean.ResponseBean;
import com.lx.lxyd.net.bean.ResponseSelfBean;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by FU on 2019/1/4.
 */
public class RxUtils {
    public static <T> Single<T> wrapRestCall(final Observable<ResponseSelfBean<T>> call) {
        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseSelfBean<T>, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(ResponseSelfBean<T> tResponseBean) throws Exception {
                        if (tResponseBean.isSuccess() == true) {
                            if (tResponseBean.getData() == null) {
                                tResponseBean.setData((T) " ");
                            }
                            return Observable.just(tResponseBean.getData());
                        } else {
                            return Observable.error(new Exception(tResponseBean.getCode()+""));
                        }
                    }
                }, new Function<Throwable, ObservableSource<? extends T>>() {

                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        Log.e("API ERROR", throwable.toString());
                        return Observable.error(throwable);
                    }
                }, new Callable<ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> call() throws Exception {
                        return Observable.empty();
                    }
                }).singleOrError();
    }


}
