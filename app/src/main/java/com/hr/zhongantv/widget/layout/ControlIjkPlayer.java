package com.hr.zhongantv.widget.layout;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;

import com.hr.mylibrary.playerview.CusTomSurfaceView;
import com.hr.mylibrary.playerview.IGSYSurfaceListener;
import com.hr.mylibrary.utils.GSYVideoType;
import com.hr.mylibrary.utils.MeasureHelper;
import com.hr.zhongantv.utils.SpanUtils;
import com.hr.zhongantv.widget.single.IjkPlayerMger;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_MATCH_FULL;

/**
 * Created by 吕 on 2018/5/14.
 */

public class ControlIjkPlayer extends FrameLayout implements
        IGSYSurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener,
        IMediaPlayer.OnPreparedListener ,
        IMediaPlayer.OnVideoSizeChangedListener ,
        IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnCompletionListener ,
        IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener ,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnTimedTextListener{

//    private final static  String URLVOIDE = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//    private final static  String URLLIVE = "rtmp://42.159.206.249:1935/1001/31117091";

    CusTomSurfaceView cusTomSurfaceView;

    private String liveUrl;
    private LoadingLayout loadingLayout;

    private int num = -1;

    private ConPlayer conPlayer;

    public void setConPlayer(ConPlayer conPlayer) {
        this.conPlayer = conPlayer;
    }

    public ControlIjkPlayer(@NonNull Context context) {
        this(context,null);
    }

    public ControlIjkPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlIjkPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onSurfaceAvailable(Surface surface) {

    }

    @Override
    public void onSurfaceSizeChanged(Surface surface, int width, int height) {
        Logger.d("onSurfaceSizeChanged--->");

        if(num > 0){
            return;
        }
        num = 1002;
        onCreatPlayerView(liveUrl);//页面初次加载完成时

    }

    @Override
    public boolean onSurfaceDestroyed(Surface surface) {
        return false;
    }

    @Override
    public void onSurfaceUpdated(Surface surface) {

    }

    @Override
    public int getCurrentVideoWidth() {
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            return IjkPlayerMger.getInstance().getMediaPlayer().getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            return IjkPlayerMger.getInstance().getMediaPlayer().getVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            return IjkPlayerMger.getInstance().getMediaPlayer().getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            return IjkPlayerMger.getInstance().getMediaPlayer().getVideoSarDen();
        }
        return 0;
    }


    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
       // Logger.d("onPrepared--->");
        //loadingLayout.setVisibility(View.GONE);
        if(null != conPlayer){
            conPlayer.onPrepared(iMediaPlayer);
        }
        cusTomSurfaceView.getRenderView().requestLayout();
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        //  Logger.d("onVideoSizeChanged--->");
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
       // Logger.d("onSeekComplete--->");
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
       // Logger.d("onCompletion--->");
        if(null != conPlayer){
            conPlayer.onCompletion();
        }

//        stop();
//        if(View.GONE == loadingLayout.getVisibility()){
//            loadingLayout.setVisibility(View.VISIBLE);
//        }
//        loadingLayout.setLoadingLayout(LoadingLayout.THREE,new LoadingLayout.ShowMain(){
//            @Override
//            public SpannableStringBuilder getSpannableStringBuilder() {
//                SpanUtils spanUtils = new SpanUtils();
//                return spanUtils.append("直播结束").create();
//            }
//        });
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
      //  Logger.d("onError--->");
        if(null != conPlayer){
            conPlayer.onError(iMediaPlayer,i,i1);
        }
//        if(View.GONE == loadingLayout.getVisibility()){
//            loadingLayout.setVisibility(View.VISIBLE);
//        }
//        loadingLayout.setLoadingLayout(LoadingLayout.TWO,new LoadingLayout.ShowMain(){
//            @Override
//            public String getText() {
//                return "直播加载失败";
//            }
//        });
        return true;
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
        // Logger.d("onBufferingUpdate--->");
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        // Logger.d("onInfo--->");
        return false;
    }

    @Override
    public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
        Logger.d("onTimedText--->");
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public void setLoadingLayout(LoadingLayout loadingLayout) {
        this.loadingLayout = loadingLayout;
    }


    public void init(){
        if(null == cusTomSurfaceView){
            cusTomSurfaceView =  CusTomSurfaceView.addSurfaceView(getContext(),this,0,this,this);
        }else {
            exchangeCollect();
        }
    }

    private void onCreatPlayerView(String url){
        GSYVideoType.setShowType(SCREEN_MATCH_FULL);
        GSYVideoType.disableMediaCodec();
        IjkMediaPlayer ijkMediaPlayer = IjkPlayerMger.getInstance().initMediaPlayer(url);

//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 8);
//        ijkMediaPlayer.setOption(1, "analyzemaxduration", 100L);
//        ijkMediaPlayer.setOption(1, "flush_packets", 1L);
//        ijkMediaPlayer.setOption(4, "framedrop", 1L);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
        ijkMediaPlayer.setOption(4, "packet-buffering", 0L);
        ijkMediaPlayer.setOption(1, "probesize", 1024L);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "infbuf", 1);


        ijkMediaPlayer.setDisplay(cusTomSurfaceView.getHolder());
        ijkMediaPlayer.setOnPreparedListener(this);
        ijkMediaPlayer.setOnCompletionListener(this);
        ijkMediaPlayer.setOnBufferingUpdateListener(this);
        ijkMediaPlayer.setOnSeekCompleteListener(this);
        ijkMediaPlayer.setOnVideoSizeChangedListener(this);
        ijkMediaPlayer.setOnErrorListener(this);
        ijkMediaPlayer.setOnInfoListener(this);
        ijkMediaPlayer.setOnTimedTextListener(this);


        ijkMediaPlayer.prepareAsync();
    }

    /**
     * 换集
     */
    public void exchangeCollect(){
        //stop();
        onCreatPlayerView(liveUrl);//换集
    }

    /**
     * 选择视频显示比例
     */
    public void switchProportion(int type){

        GSYVideoType.setShowType(type);

        if(null != cusTomSurfaceView){
            cusTomSurfaceView.getRenderView().requestLayout();
        }

    }


    /**
     * 播放或暂停
     */
    public void playerOrPause(boolean is){
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){

            if(is){
                IjkPlayerMger.getInstance().getMediaPlayer().stop();
            }else {
//                if(null == cusTomSurfaceView){
//                    cusTomSurfaceView =  CusTomSurfaceView.addSurfaceView(getContext(),this,0,this,this);
//                }
//                exchangeCollect();
                init();
            }

//            if(IjkPlayerMger.mediaPlayer.isPlaying()){
//                IjkPlayerMger.mediaPlayer.stop();
//            }else {
//                exchangeCollect();
//            }
        }
    }

    /**
     * 停止播放
     */
    public void stop(){
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            IjkPlayerMger.getInstance().getMediaPlayer().stop();
        }
    }

    //释放
    public void release(){
        Logger.d("onDestroy--->");

        cusTomSurfaceView = null;

        if (IjkPlayerMger.getInstance().getMediaPlayer() != null) {

            IjkPlayerMger.getInstance().getMediaPlayer().stop();
            IjkPlayerMger.getInstance().getMediaPlayer().reset();
            IjkPlayerMger.getInstance().getMediaPlayer().release();

            IjkPlayerMger.getInstance().setMediaPlayer(null);

            IjkMediaPlayer.native_profileEnd();
        }

    }

    public interface ConPlayer{
        void onPrepared(IMediaPlayer iMediaPlayer);
        void onCompletion();
         void onError(IMediaPlayer iMediaPlayer, int i, int i1);
    }


}
