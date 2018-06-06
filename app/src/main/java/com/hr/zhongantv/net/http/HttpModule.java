package com.hr.zhongantv.net.http;


import com.hr.zhongantv.net.Service.VideoService;
import com.hr.zhongantv.net.Service.YouDaoService;
import com.hr.zhongantv.net.base.BaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 吕 on 2017/10/26.
 */
@Module
public class HttpModule {
    protected static final String APP_CODE = "AppCode";
    protected static final String APP_CODE_VALUE = "ANDROID";

    //private final static String URl = "http://192.168.0.113:8081/";
   // private final static String URl = "http://39.104.65.70:8090/";
    private final static String URl = "http://123.56.21.11:7089/";

    @Provides
    @Singleton
    public YouDaoService youDaoService(){
        HttpServiceSetting httpServiceSetting = new HttpServiceSetting("http://fanyi.youdao.com/");
        httpServiceSetting.builderCallback = new OkHttpRequestBuilderCallback() {
            @Override
            public void builder(Request.Builder builder) {
                builder.addHeader(APP_CODE, APP_CODE_VALUE);    //设置请求头
            }
        };
        httpServiceSetting.logLevel = HttpLoggingInterceptor.Level.BODY;
        Retrofit retrofit = OkHttpClientUtils.buildRetrofit(httpServiceSetting);
        return  retrofit.create(YouDaoService.class);
    }

    @Provides
    @Singleton
    public VideoService videoService(){
        HttpServiceSetting httpServiceSetting = new HttpServiceSetting(URl);
        httpServiceSetting.builderCallback = new OkHttpRequestBuilderCallback() {
            @Override
            public void builder(Request.Builder builder) {
                builder.addHeader(APP_CODE, APP_CODE_VALUE);    //设置请求头
            }
        };
        httpServiceSetting.logLevel = HttpLoggingInterceptor.Level.BODY;
        Retrofit retrofit = OkHttpClientUtils.buildRetrofit(httpServiceSetting);
        return  retrofit.create(VideoService.class);
    }


    //
    @Provides
    @Singleton
    public BaseService baseService(){
        return new BaseService();
    }
}
