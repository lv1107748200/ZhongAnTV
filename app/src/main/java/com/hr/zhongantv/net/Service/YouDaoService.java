package com.hr.zhongantv.net.Service;



import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.entry.AddPostData;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 吕 on 2017/12/12.
 * http://fanyi.youdao.com/openapi.do?keyfrom=lvzuhao&key=560647729&type=data&doctype=json&version=1.1&q=你好
 */

public interface YouDaoService {
    @GET("http://fanyi.youdao.com/openapi.do?keyfrom=lvzuhao&key=560647729&type=data&doctype=json&version=1.1")
    Observable<Response<BaseResponse>> getPostView(@Query("q") String q);

}
