package com.hr.zhongantv.net.base;





import com.hr.zhongantv.base.BaseApplation;
import com.hr.zhongantv.net.Service.VideoService;
import com.hr.zhongantv.net.Service.YouDaoService;
import com.hr.zhongantv.net.entry.AddPostData;
import com.hr.zhongantv.net.entry.LiveData;
import com.hr.zhongantv.net.entry.LookBackData;
import com.hr.zhongantv.net.entry.STBMessage;
import com.hr.zhongantv.net.http.HttpCallback;
import com.hr.zhongantv.net.http.HttpUtils;
import com.hr.zhongantv.net.subscriber.HttpSubscriber;
import com.hr.zhongantv.widget.single.ClientInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;

/**
 * Created by 吕 on 2017/10/27.
 */

public class BaseService {

    public BaseService() {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
    }
    @Inject
    public YouDaoService youDaoService;
    @Inject
    public VideoService videoService;

    //根据机顶盒序列号查询直播情况
    public void selectLiveByBoxNumber(HttpCallback<BaseResponse<LiveData>> httpCallback, ObservableTransformer transformer){
        HttpUtils.toSubscribe(videoService.selectLiveByBoxNumber(ClientInfo.getInstance().getToken()),
                new HttpSubscriber<LiveData>(httpCallback)
        ,transformer);
    }
    //查询回放视频
    public void selectAll(String pageNo,HttpCallback<BaseResponse<List<LookBackData>>> httpCallback,ObservableTransformer transformer){
        HttpUtils.toSubscribe(videoService.selectAll(pageNo,ClientInfo.getInstance().getToken()),
                new HttpSubscriber<List<LookBackData>>(httpCallback)
        ,transformer);
    }
    //根据序列号查询机顶盒信息
    public void selectByNumber(HttpCallback<BaseResponse<STBMessage>> httpCallback,ObservableTransformer transformer){
        HttpUtils.toSubscribe(videoService.selectByNumber(ClientInfo.getInstance().getToken()),
                new HttpSubscriber<STBMessage>(httpCallback)
                ,transformer);
    }
    //上线
    public void boxOnLine(HttpCallback<BaseResponse> httpCallback){
        HttpUtils.toSubscribe(videoService.boxOnLine(ClientInfo.getInstance().getToken()),
                new HttpSubscriber(httpCallback)
        );
    }
    //下线
    public void boxOffLine(HttpCallback<BaseResponse> httpCallback){
        HttpUtils.toSubscribe(videoService.boxOffLine(ClientInfo.getInstance().getToken()),
                new HttpSubscriber(httpCallback)
                );
    }

    @SuppressWarnings("unchecked")
    public void getP(String p, HttpCallback<BaseResponse> httpCallback,ObservableTransformer transformer){
            HttpUtils.toSubscribe(youDaoService.getPostView(p),
                    new HttpSubscriber(httpCallback)
            ,transformer);
    }

}
