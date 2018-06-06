package com.hr.zhongantv.ui.activity;

import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseActivity;
import com.hr.zhongantv.base.BaseFragment;
import com.hr.zhongantv.entiy.ItemDatas;
import com.hr.zhongantv.ui.adapter.GridAdapter;
import com.hr.zhongantv.ui.adapter.MainAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.ui.fragment.LookBackFragment;
import com.hr.zhongantv.ui.fragment.OutFragment;
import com.hr.zhongantv.ui.fragment.UserCenterFragment;
import com.hr.zhongantv.ui.fragment.VideoFragment;
import com.hr.zhongantv.utils.DisplayUtils;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.widget.focus.FocusBorder;
import com.hr.zhongantv.widget.pop.CustomPopuWindConfig;
import com.hr.zhongantv.widget.pop.MenuPopWindow;
import com.hr.zhongantv.widget.view.TvViewPager;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 吕 on 2018/3/14.
 */

public class VideoActivity extends BaseActivity implements MenuPopWindow.MenuCallBack ,
        BaseFragment.FocusBorderHelper {


    private MenuPopWindow menuPopWindow;

    private int  menuSelectedPoint = 0;

    private List<Fragment> fragmentList;

    private FragmentManager mFragmentManager;

    private Disposable mDisposable;//脉搏



    @BindView(R.id.framelayout)
    FrameLayout framelayout;


//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.btn_one:
//                selectFragment(0);
//                break;
//            case R.id.btn_two:
//                selectFragment(1);
//                break;
//            case R.id.btn_three:
//                selectFragment(2);
//                break;
//            case R.id.btn_four:
//                selectFragment(3);
//                break;
//        }
//
//    }

    @Override
    public int getLayout() {
        return R.layout.activity_video;
    }

    @Override
    public void init() {
        super.init();

        mFragmentManager = getSupportFragmentManager();

        fragmentList = new ArrayList<>();
        fragmentList.add(new VideoFragment());
        fragmentList.add(new LookBackFragment());
        fragmentList.add(new UserCenterFragment());
        fragmentList.add(new OutFragment());

        for (Fragment fragment : fragmentList) {
            mFragmentManager.beginTransaction().add(R.id.framelayout, fragment).commit();
        }

        menuPopWindow = new MenuPopWindow(this,new CustomPopuWindConfig
                .Builder(this)
                .setOutSideTouchable(true)
                .setFocusable(true)
                .setAnimation(R.style.popup_hint_anim)
                .setHigh(DisplayUtils.getScreenHeight(this))
                .setTouMing(true)
                .build());
        menuPopWindow.setMenuCallBack(this);

        selectFragment(menuSelectedPoint);

    }
    //菜单
    @Override
    public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

        NLog.d(NLog.TAGOther,"onItemSelected--->" + position);

        if(position == menuSelectedPoint){

        }else {
            doSomething();//重新刷新

            menuSelectedPoint = position;

            selectFragment(menuSelectedPoint);
        }

    }



    private void selectFragment(int point){

        for (int i = 0; i < fragmentList.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = fragmentList.get(i);
            if (i == point) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }

    }

    protected void doSomething() {

        dispose();

        mDisposable = Flowable.interval(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                        if(null != menuPopWindow){
                            if(menuPopWindow.isShowing()){
                                menuPopWindow.dismiss();
                            }
                        }

                        dispose();


                    }
                });

    }
    private void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            mDisposable = null;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

    //    NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:
                NLog.d(NLog.TAGOther,"enter--->");

                break;

            case KeyEvent.KEYCODE_BACK:    //返回键
                NLog.d(NLog.TAGOther,"back--->");
                if(null != menuPopWindow){
                    menuPopWindow.show(framelayout);
                }
                return true;
                // finish();

                // return true;   //这里由于break会退出，所以我们自己要处理掉 不返回上一层

               // break;

            case KeyEvent.KEYCODE_SETTINGS: //设置键
                NLog.d(NLog.TAGOther,"setting--->");

                break;
            case KeyEvent.KEYCODE_MENU:
                NLog.d(NLog.TAGOther,"MENU--->");
                doSomething();
                if(null != menuPopWindow){
                    menuPopWindow.show(framelayout);
                }

                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN){

              //      NLog.d(NLog.TAGOther,"down--->");
                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
              //  NLog.d(NLog.TAGOther,"up--->");

                break;

            case     KeyEvent.KEYCODE_0:   //数字键0
                NLog.d(NLog.TAGOther,"0--->");
                doSomething();
                if(null != menuPopWindow){
                    menuPopWindow.show(framelayout);
                }

                break;

            case KeyEvent.KEYCODE_1:

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键

               NLog.d(NLog.TAGOther,"left--->");

                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
              //  NLog.d(NLog.TAGOther,"right--->");
                break;

            case KeyEvent.KEYCODE_INFO:    //info键
                NLog.d(NLog.TAGOther,"info--->");

                break;

            case KeyEvent.KEYCODE_PAGE_DOWN:     //向上翻页键
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                NLog.d(NLog.TAGOther,"page down--->");

                break;


            case KeyEvent.KEYCODE_PAGE_UP:     //向下翻页键
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                NLog.d(NLog.TAGOther,"page up--->");

                break;

            case KeyEvent.KEYCODE_VOLUME_UP:   //调大声音键
                NLog.d(NLog.TAGOther,"voice up--->");

                break;

            case KeyEvent.KEYCODE_VOLUME_DOWN: //降低声音键
                NLog.d(NLog.TAGOther,"voice down--->");

                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }


    @Override
    public FocusBorder getFocusBorder() {
        return mFocusBorder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }
}
