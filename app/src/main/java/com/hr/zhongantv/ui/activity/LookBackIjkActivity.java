package com.hr.zhongantv.ui.activity;

import android.view.KeyEvent;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseActivity;
import com.hr.zhongantv.net.entry.LookBackData;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.widget.layout.ControlPlayer;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_MATCH_FULL;

/**
 * Created by 吕 on 2018/5/15.
 */
public class LookBackIjkActivity extends BaseActivity {
    private boolean shortPress = false;
    private String url;

    private LookBackData lookBackData;
    @BindView(R.id.ControlPlayer)
    ControlPlayer controlPlayer;
    @Override
    public int getLayout() {
        return R.layout.activity_look_back_ijk;
    }
    @Override
    public void init() {
        super.init();
        controlPlayer.setContext(this);

        lookBackData = (LookBackData) getIntent().getSerializableExtra("LookBackData");
        if(null != lookBackData){
            url = lookBackData.getUrl();

           //
            controlPlayer.setVideoUrl(url);
            controlPlayer.initConPlay();//
            controlPlayer.setTitle_video(lookBackData.getName());
        }
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键

                Logger.d("onKeyUp left--->" + shortPress);

                if(shortPress){
                    controlPlayer.stopSeepOrBackTask(false);
                }else {

                    controlPlayer.stopSeepOrBackTask(true);

                }

                shortPress = false;

                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键


                Logger.d("onKeyUp right--->" + shortPress);


                if(shortPress){
                    controlPlayer.stopSeepOrBackTask(false);
                }else {
                    controlPlayer.stopSeepOrBackTask(true);
                }
                shortPress = false;

                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //    NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Logger.d("enter--->");

                controlPlayer.playOrstopOrRe();//遥控确定键


                break;

            case KeyEvent.KEYCODE_BACK:    //返回键
                Logger.d("back--->");


                break;
            case KeyEvent.KEYCODE_MENU:
                Logger.d("MENU--->");
            //    controlPlayer.onClickKey(KeyEvent.KEYCODE_MENU);

                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN){

                          Logger.d("down--->");
                 //   controlPlayer.onClickKey(KeyEvent.ACTION_DOWN);

                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
                //  NLog.d(NLog.TAGOther,"up--->");

                break;

            case     KeyEvent.KEYCODE_0:   //数字键0
                Logger.d("KEYCODE_0--->");

              //  finish();

                break;
            case     KeyEvent.KEYCODE_1:   //数字键0
                Logger.d("KEYCODE_1--->");
               // controlPlayer.playOrstopOrRe();//遥控确定键

                break;
            case     KeyEvent.KEYCODE_2:   //数字键0
                Logger.d("KEYCODE_2--->");
              //  controlPlayer.onClickKey(KeyEvent.KEYCODE_MENU);

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键

                  Logger.d("lonKeyDown eft--->" +  event.getRepeatCount());


                if(event.getRepeatCount() == 0){
                    event.startTracking();
                    shortPress = true;
                    controlPlayer.backKey(false,event.getRepeatCount());
                }else {

                    shortPress = false;
                    controlPlayer.backKey(true,event.getRepeatCount());
                    return true;
                }


                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
                Logger.d("onKeyDown right---> " + event.getRepeatCount());


                if(event.getRepeatCount() == 0){

                    event.startTracking();
                    shortPress = true;
                    controlPlayer.seepKey(false,event.getRepeatCount());

                }else {

                    shortPress = false;
                    controlPlayer.seepKey(true,event.getRepeatCount());
                    return true;

                }

                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        controlPlayer.destroy();
    }

}
