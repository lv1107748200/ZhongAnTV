package com.hr.zhongantv.net.subscriber;


import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.http.HttpCallback;
import com.hr.zhongantv.net.http.HttpException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


/*
31000    success     直播拉流地址
31001    msg         提示当前没有直播
31002    msg         提示最近的直播什么时间开始
31003    msg         提示机顶盒没有注册为直播用户
31004    error       获取拉流地址错误
 */
public class HttpSubscriber<T> implements Observer<Response<BaseResponse<T>>> {
    HttpCallback callback;

    public HttpSubscriber(HttpCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
      //  NToast.shortToastBaseApp("访问失败!");
        if (callback != null) {
            callback.onError(new HttpException(-100,e.getMessage()));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onNext(Response<BaseResponse<T>> httpResultResponse) {
        if(httpResultResponse.code()==200){
           BaseResponse<T> result = httpResultResponse.body();
            if(result.getSysCode() == 200 || result.getSysCode() == 1000 || result.getSysCode() == 31000 || result.getSysCode() == 31002){

                if (callback != null) {
                    callback.onSuccess(result);
                }

            } else {
                    if (callback != null) {
                         callback.onError(new HttpException(result.getSysCode(),result.getMsg()));
                    }
            }
        } else {
            if (callback != null) {
                callback.onError(new HttpException(httpResultResponse.code(),httpResultResponse.message()));
            }
        }
    }



}
