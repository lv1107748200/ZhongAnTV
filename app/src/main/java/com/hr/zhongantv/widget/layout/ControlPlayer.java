package com.hr.zhongantv.widget.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hr.mylibrary.playerview.CusTomSurfaceView;
import com.hr.mylibrary.playerview.IGSYSurfaceListener;
import com.hr.mylibrary.utils.GSYVideoType;
import com.hr.mylibrary.utils.MeasureHelper;
import com.hr.zhongantv.R;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.DisplayUtils;
import com.hr.zhongantv.utils.Formatter;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.utils.SPUtils;
import com.hr.zhongantv.widget.pop.CustomPopuWindConfig;
import com.hr.zhongantv.widget.pop.FunctionMenuPopWindow;
import com.hr.zhongantv.widget.pop.SelectionsPopWindow;
import com.hr.zhongantv.widget.single.IjkPlayerMger;
import com.orhanobut.logger.Logger;
import com.warkiz.widget.IndicatorSeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_MATCH_FULL;
import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_TYPE_16_9;
import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_TYPE_4_3;
import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_TYPE_DEFAULT;
import static com.hr.zhongantv.widget.layout.ControlView.PLAY_ERROR;
import static com.hr.zhongantv.widget.layout.ControlView.PLAY_SEEKCOMPLETE;
import static com.hr.zhongantv.widget.pop.FunctionMenuPopWindow.DECODE;
import static com.hr.zhongantv.widget.pop.FunctionMenuPopWindow.RATION;

/**
 * Created by 吕 on 2018/5/14.
 */

public class ControlPlayer extends FrameLayout implements
        IGSYSurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener,
        IMediaPlayer.OnPreparedListener ,
        IMediaPlayer.OnVideoSizeChangedListener ,
        IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnCompletionListener ,
        IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener ,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnTimedTextListener
        ,SelectionsPopWindow.SelectionsCallBack
        ,FunctionMenuPopWindow.FunctionMenuBack
,ControlView.ConViewCallBack {

//    private final static  String URLVOIDE = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//    private final static  String URLLIVE = "rtmp://42.159.206.249:1935/1001/31117091";

    private String videoUrl;

    private CusTomSurfaceView cusTomSurfaceView;
    private ControlView controlView;

    private SelectionsPopWindow selectionsPopWindow;//选集
    private FunctionMenuPopWindow functionMenuPopWindow;//功能菜单

    private Activity contextPlayer;
    private int num = -1;

    private int playProgress = -1;

    public ControlPlayer(@NonNull Context context) {
        this(context,null);
    }

    public ControlPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        num = 1001;
        if(!CheckUtil.isEmpty(videoUrl)){
            onCreatPlayerView(videoUrl);
        }
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
        Logger.d("onPrepared--->");
        onPrepared();
        if(null != cusTomSurfaceView)
        cusTomSurfaceView.getRenderView().requestLayout();
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        //  Logger.d("onVideoSizeChanged--->");
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        Logger.d("onSeekComplete--->");
        if(null != controlView){
            controlView.set(PLAY_SEEKCOMPLETE);//快进完成
        }
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        Logger.d("onCompletion--->");
        if(null != controlView){
            controlView.set(ControlView.PLAY_COM);
        }
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Logger.d("onError--->");
        if(null != controlView){
            controlView.set(PLAY_ERROR);
        }
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

    @Override
    public void onItemSelected(String position) {
        if(!CheckUtil.isEmpty(position)){
            videoUrl = position;//更换地址
            changeVideo();//选集
            selectionsPopWindow.dismiss();
        }

    }

    @Override
    public void isDimss() {

    }
    @Override
    public void onItemSelected(int point, int select) {

        Logger.d("point---"+point+"---"+select);

        functionMenuPopWindow.dismiss();
        if(null == IjkPlayerMger.getInstance().getMediaPlayer()){
            return;
        }

        switch (point){
            case RATION://画面比例

                SPUtils.putInt("RATION",select);

                if(select == 0){
                    //元比例填充
                    switchProportion(SCREEN_TYPE_DEFAULT);
                }else if(select == 1){
                    switchProportion(SCREEN_TYPE_16_9);
                }else if(select == 2){
                    switchProportion(SCREEN_TYPE_4_3);
                }else if(select == 3){
                    switchProportion(SCREEN_MATCH_FULL);
                }
                break;
            case DECODE://解码方式  解码器类型。0代表软件解码器；1代表硬件解码器。
                SPUtils.putInt("DECODE",select);
                if(select == 0){
                    GSYVideoType.disableMediaCodec();
                }else if(select == 1){
                    GSYVideoType.enableMediaCodec();
                }
                if(IjkPlayerMger.getInstance().getMediaPlayer().isPlaying()){
                    playProgress = (int) IjkPlayerMger.getInstance().getMediaPlayer().getCurrentPosition();
                }
                changeVideo();
                break;
        }
    }

    @Override
    public void isFunctionMenuDimss() {

    }
    @Override
    public void rePlay() {
        replay();
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setContext(Activity context) {
        this.contextPlayer = context;
    }
    public void setTitle_video(String title) {
        controlView.setTitle_video(title);
    }
    /**
     * @param num 键盘 遥控
     */
    public void onClickKey(int num) {

        switch (num){
            case KeyEvent.KEYCODE_MENU:
                if(null != controlView){
                    controlView.set(ControlView.PLAY_SHOW_MENU);
                }
                showFunctionMenuPopWindow();
                break;
            case KeyEvent.ACTION_DOWN:
                if(null != controlView){
                    controlView.set(ControlView.PLAY_SHOW_LIST);
                }
                showSelectionPop();
                break;
        }

    }

    //准备完成时触发
    public void onPrepared() {
        if(null != controlView){
            controlView.set(ControlView.PLAY_PREPARED);
        }
        if(playProgress > 0){
            if(IjkPlayerMger.getInstance().getMediaPlayer().isPlaying()){
                IjkPlayerMger.getInstance().getMediaPlayer().seekTo(playProgress);
                playProgress = -1;
            }
        }
    }

    //事件处理
    public void  playOrstopOrRe(){
        if(null != controlView)
        controlView.playOrstopOrRe();
    }

    public void replay() {
        if(null != controlView)
        controlView.set(ControlView.PLAY_REPLAY);
        exchangeCollect();//重新播放
    }

    private void  changeVideo(){
        if(null != controlView)
        controlView.set(ControlView.PLAY_SWTICH);//重新播放
        exchangeCollect();//重新播放
    }

    /**
     * 快进
     */
    public void seepKey(boolean is,int num) {
        if(null != controlView)
        controlView.seepKey(is,num);
    }

    /**
     * 快退
     */
    public void backKey(boolean is,int num) {
        if(null != controlView)
       controlView.backKey(is,num);
    }

    /**
     * 停止快进或快推
     */
    public void stopSeepOrBackTask(boolean is) {
        if(null != controlView){
            controlView.stopSeepOrBackTask(is);
        }
    }

    /**
     * 初始化
     */
    public void initConPlay(){

        GSYVideoType.setRATION(SPUtils.getInt("RATION",3));

      //  GSYVideoType.setShowType(SCREEN_TYPE_4_3);

        cusTomSurfaceView =  CusTomSurfaceView.addSurfaceView(getContext(),this,0,this,this);

        View view =  View.inflate(getContext(), R.layout.video_control_layout,null);

        controlView = new ControlView(getContext());

        controlView.initView(view);

        controlView.setConViewCallBack(this);

        addView(controlView);
    }

    private void onCreatPlayerView(String url){

        IjkMediaPlayer ijkMediaPlayer = IjkPlayerMger.getInstance().initMediaPlayer(url);

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
      //  stop();
     //   release();//换集
        if(null != cusTomSurfaceView)
        onCreatPlayerView(videoUrl);
    }

    /**
     * 显示 选集列表
     */
    private void showSelectionPop(){

        if(null == selectionsPopWindow){
            selectionsPopWindow = new SelectionsPopWindow(getContext(),new CustomPopuWindConfig
                    .Builder(contextPlayer)
                    .setOutSideTouchable(true)
                    .setFocusable(true)
                    .setAnimation(R.style.popup_hint_anim_one)
                    .setWith(DisplayUtils.getScreenWidth(getContext()))
                    .setTouMing(true)
                    .build());
            selectionsPopWindow.setHintCallBack(this);

            selectionsPopWindow.show(this);

        }else {
            selectionsPopWindow.show(this);
        }

    }

    /**
     * 显示 功能菜单
     */
    private void showFunctionMenuPopWindow(){

        if(null == functionMenuPopWindow){
            functionMenuPopWindow = new FunctionMenuPopWindow(contextPlayer,new CustomPopuWindConfig
                    .Builder(contextPlayer)
                    .setOutSideTouchable(true)
                    .setFocusable(true)
                    .setAnimation(R.style.popup_hint_anim_two)
                    .setHigh(DisplayUtils.getScreenHeight(getContext()))
                    .setTouMing(true)
                    .build());
            functionMenuPopWindow.setFunctionMenuCallBack(this);

            functionMenuPopWindow.show(this);

        }else {
            functionMenuPopWindow.show(this);
        }

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

    //池底销毁
    public void destroy() {
        Logger.d("controlPlayer onDestroy--->");

        if (IjkPlayerMger.getInstance().getMediaPlayer() != null) {

            if(IjkPlayerMger.getInstance().getMediaPlayer().isPlaying()){
                IjkPlayerMger.getInstance().getMediaPlayer().stop();
            }
        }
        if(null != selectionsPopWindow){
            selectionsPopWindow = null;
        }
        if(null != functionMenuPopWindow){
            functionMenuPopWindow = null;
        }

        controlView.destroy();

        cusTomSurfaceView = null;
    }
}
