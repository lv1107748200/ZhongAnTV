package com.hr.zhongantv.net.Service;

import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.entry.LiveData;
import com.hr.zhongantv.net.entry.LookBackData;
import com.hr.zhongantv.net.entry.STBMessage;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 吕 on 2018/3/28.
 */

public interface VideoService {

    //根据机顶盒序列号查询直播情况
    @GET("live/box/selectLiveByBoxNumber")
    Observable<Response<BaseResponse<LiveData>>>
    selectLiveByBoxNumber(@Query("_signToken") String token);
    //查询回放视频
    @GET("live/backVideo/selectAll")
    Observable<Response<BaseResponse<List<LookBackData>>>>
    selectAll(@Query("pageNo") String pageNo,@Query("_signToken") String token);
    //根据序列号查询机顶盒信息
    @GET("live/box/selectByNumber")
    Observable<Response<BaseResponse<STBMessage>>>
    selectByNumber(@Query("_signToken") String token);
    //上线
    @GET("live/box/boxOnLine")
    Observable<Response<BaseResponse>>
    boxOnLine(@Query("_signToken") String token);
    //下线
    @GET("live/box/boxOffLine")
    Observable<Response<BaseResponse>>
    boxOffLine(@Query("_signToken") String token);

}
