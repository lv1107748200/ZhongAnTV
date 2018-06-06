package com.hr.zhongantv.widget.layout;

/**
 * Created by 吕 on 2018/3/22.
 */

public interface ControlWay {
    void setControlMainLayout(int show);
     void setLoad_relayout(int show);
     void startUpSeekBar();
     void stopUpSeekBar();
     void getData();
     void getDataEveryTime();
    void  changeVideo(String url);
    void start();
    void pause();
    void resume();
    void stop();
    void replay();
    void destroy();
    void onCompleted();
    void setInSeek(boolean inSeek);
    void setLoadingLayout(int cas,LoadingLayout.ShowMain showMain);

    void seepKey();
    void backKey();
    void seepOrkackTask();
    void stopSeepOrBackTask();
}
