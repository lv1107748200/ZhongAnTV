package com.hr.zhongantv.widget.single;


import android.content.Context;
import android.media.AudioManager;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.hr.mylibrary.playerview.CusTomSurfaceView;
import com.hr.mylibrary.playerview.IGSYSurfaceListener;
import com.hr.mylibrary.utils.GSYVideoType;
import com.hr.mylibrary.utils.MeasureHelper;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by 吕 on 2018/3/28.
 */

public class IjkPlayerMger{

    volatile private static IjkPlayerMger instance = null;

    public  IjkMediaPlayer mediaPlayer;

    public static IjkPlayerMger getInstance(){
        if(instance == null){
            synchronized (IjkPlayerMger.class) {
                if(instance == null){
                    instance = new IjkPlayerMger();
                }
            }
        }

        return instance;
    }

    public  IjkMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(IjkMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public IjkMediaPlayer initMediaPlayer(String url){
            if(null == mediaPlayer){
                mediaPlayer = new IjkMediaPlayer();
            }else {
                mediaPlayer.reset();
            }
            mediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //循环播放
           // mediaPlayer.setLooping(true);
        //SeekTo的时候，会跳回到拖动前的位置
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
            //屏幕常亮
            mediaPlayer.setScreenOnWhilePlaying(true);


        //开启硬解码
            if (GSYVideoType.isMediaCodec()) {
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
            }

            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  mediaPlayer;
    }

    public void setOnDesry(){
        if(null != mediaPlayer){

            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            mediaPlayer = null;

            IjkMediaPlayer.native_profileEnd();
        }
    }

}
