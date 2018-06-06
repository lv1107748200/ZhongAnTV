package com.hr.zhongantv.ui.fragment;

import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseFragment;
import com.hr.zhongantv.entiy.ItemDatas;
import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.entry.LiveData;
import com.hr.zhongantv.net.http.HttpCallback;
import com.hr.zhongantv.net.http.HttpException;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.DateUtils;
import com.hr.zhongantv.utils.FocusUtil;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.utils.SpanUtils;
import com.hr.zhongantv.widget.layout.ControlIjkPlayer;
import com.hr.zhongantv.widget.layout.LoadingLayout;
import com.hr.zhongantv.widget.single.ClientInfo;
import com.hr.zhongantv.widget.single.IjkPlayerMger;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 吕 on 2018/3/14.
 * 直播页
 */

public class VideoFragment extends BaseFragment
        implements LoadingLayout.LoadingCallBack , ControlIjkPlayer.ConPlayer
{
    private String url = null;

    //private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    //private String url = "http://42.159.206.249:5080/1001/21348634.m3u8";
    //private String url = "http://live.chinaanxuan.com/AppName/StreamName.flv";

    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.title_video)
    TextView title_video;
    @BindView(R.id.ControlIjkPlayer)
    ControlIjkPlayer controlIjkPlayer;//视图
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    private Disposable mDisposable;//脉搏

    @Override
    public int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void init() {
        super.init();

        loadingLayout.setLoad_layout(R.drawable.gradient_bc);
        loadingLayout.setLoadingCallBack(this);
        controlIjkPlayer.setLoadingLayout(loadingLayout);
        controlIjkPlayer.setConPlayer(this);

        load();

//      url = "rtmp://live.zacm.org/AppName/StreamName?auth_key=1528289632-0-0-2af3a1b8a084a7d2152c978199abb87a";
 //     controlIjkPlayer.setLiveUrl(url);
  //    controlIjkPlayer.init();
    }

    @Override
    public void loadData() {
        super.loadData();
        NLog.d(NLog.TAGOther,"VideoFragment:loadData--->");

        lifecycleSubject.onNext(FragmentEvent.START);

            if(!CheckUtil.isEmpty(url)){
                controlIjkPlayer.playerOrPause(false);
            } else {

            if(View.GONE == loadingLayout.getVisibility()){
                loadingLayout.setVisibility(View.VISIBLE);
            }

            loadingLayout.setLoadingLayout(LoadingLayout.ONE,null);
            load();

        }

    }

    @Override
    public void stopLoad() {
        super.stopLoad();
        NLog.d(NLog.TAGOther,"VideoFragment:stopLoad--->");

        if(!CheckUtil.isEmpty(url)){
            controlIjkPlayer.playerOrPause(true);
        }else {
            dispose();
            lifecycleSubject.onNext(FragmentEvent.PAUSE);
        }

    }
    //重新加载
    @Override
    public void btnCallBack() {

        reSet();

        loadingLayout.setLoadingLayout(LoadingLayout.ONE,null);

        if(!CheckUtil.isEmpty(url)){
            controlIjkPlayer.exchangeCollect();
            setTitle();
        }else {
            load();
        }

    }
    //播放完成
    @Override
    public void onCompletion() {
        reSet();
        url = null;
        boxOffLine();
    }

    private void load(){

        baseService.selectLiveByBoxNumber(new HttpCallback<BaseResponse<LiveData>>() {
            @Override
            public void onError(HttpException e) {

                if(View.GONE == loadingLayout.getVisibility()){
                    loadingLayout.setVisibility(View.VISIBLE);
                }
                if(31003 == e.getCode()){
                    loadingLayout.setLoadingLayout(LoadingLayout.THREE,new LoadingLayout.ShowMain(){
                        @Override
                        public SpannableStringBuilder getSpannableStringBuilder() {
                            SpanUtils spanUtils = new SpanUtils();


                            return spanUtils.append("机顶盒没有注册为直播用户").create();
                        }
                    });
                }else if(31001 == e.getCode()){
                    loadingLayout.setLoadingLayout(LoadingLayout.THREE,new LoadingLayout.ShowMain(){
                        @Override
                        public SpannableStringBuilder getSpannableStringBuilder() {
                            SpanUtils spanUtils = new SpanUtils();


                            return spanUtils.append("当前没有直播").create();
                        }
                    });
                }else{
                    loadingLayout.setLoadingLayout(LoadingLayout.TWO,null);
                   // FocusUtil.setFocus(loadingLayout.getBtnLoad());
                }
            }

            @Override
            public void onSuccess(BaseResponse<LiveData> stringBaseResponse) {

                final LiveData liveData = stringBaseResponse.getData();

                if(stringBaseResponse.getSysCode() == 31002){

                    loadingLayout.setLoadingLayout(LoadingLayout.THREE,new LoadingLayout.ShowMain(){
                        @Override
                        public SpannableStringBuilder getSpannableStringBuilder() {
                            SpanUtils spanUtils = new SpanUtils();


                            return spanUtils.append("最近直播"+
                                    DateUtils.stampToDate(liveData.getBeginTime(),DateUtils.TURN_DATE_FORMAT_M_H)
                                    +" 开始").create();
                        }
                    });
                    doSomething();

                }else {

                    dispose();

                    if(!CheckUtil.isEmpty(liveData)){

                       // url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

                        url = liveData.getWatchAddress();
                        title_video.setText(liveData.getLiveName());

                        if(!CheckUtil.isEmpty(url)){
                            controlIjkPlayer.setLiveUrl(url);
                            controlIjkPlayer.init();
                            setTitle();
                            ClientInfo.getInstance().setURL(url);
                            boxOnLine();
                        }

                    }
                }



            }
        },VideoFragment.this.bindUntilEvent(FragmentEvent.PAUSE));

    }

    private void boxOnLine(){
        baseService.boxOnLine(new HttpCallback<BaseResponse>() {
            @Override
            public void onError(HttpException e) {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                NLog.d(NLog.TAG,"boxOnLine--->");
            }
        });
    }
    private void boxOffLine(){
        baseService.boxOffLine(new HttpCallback<BaseResponse>() {
            @Override
            public void onError(HttpException e) {
               // ClientInfo.getInstance().setURL(null);
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                ClientInfo.getInstance().setURL(null);
            }
        });
    }

    private void setTitle(){
        title_tv.setVisibility(View.GONE);
        title_video.setVisibility(View.VISIBLE);
    }

    private void reSet(){
        title_tv.setVisibility(View.VISIBLE);
        title_video.setVisibility(View.GONE);
    }

    protected void doSomething() {

        if(null != mDisposable){

        }else {
            mDisposable = Flowable.interval(1, TimeUnit.MINUTES)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {

                            load();

                        }
                    });
        }



    }
    private void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            mDisposable = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        dispose();
        IjkPlayerMger.getInstance().setOnDesry();

        if(null != controlIjkPlayer)
        controlIjkPlayer.release();
    }
}
