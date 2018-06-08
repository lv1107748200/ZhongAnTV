package com.hr.zhongantv.ui.activity;

import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.YoYo;
import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseActivity;
import com.hr.zhongantv.base.BaseFragment;
import com.hr.zhongantv.entiy.ItemBean;
import com.hr.zhongantv.entiy.ItemDatas;
import com.hr.zhongantv.ui.adapter.GridAdapter;
import com.hr.zhongantv.ui.adapter.MainAdapter;
import com.hr.zhongantv.ui.adapter.MenuAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.ui.event.Event;
import com.hr.zhongantv.ui.fragment.LookBackFragment;
import com.hr.zhongantv.ui.fragment.OutFragment;
import com.hr.zhongantv.ui.fragment.UserCenterFragment;
import com.hr.zhongantv.ui.fragment.VideoFragment;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.DisplayUtils;
import com.hr.zhongantv.utils.FocusUtil;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.widget.focus.FocusBorder;
import com.hr.zhongantv.widget.pop.CustomPopuWindConfig;
import com.hr.zhongantv.widget.pop.MenuPopWindow;
import com.hr.zhongantv.widget.view.TvVHViewPager;
import com.hr.zhongantv.widget.view.TvViewPager;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import org.greenrobot.eventbus.EventBus;

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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.daimajia.androidanimations.library.Techniques.Bounce;
import static com.daimajia.androidanimations.library.Techniques.Shake;

/**
 * Created by 吕 on 2018/3/14.
 */

public class VideoActivity extends BaseActivity implements
        BaseFragment.FocusBorderHelper ,TvVHViewPager.OnPageChangeListener{


    private List<Fragment> fragmentList;

    private Disposable mDisposable;//脉搏
    private MainAdapter mainAdapter;

    private Animation showAnim;
    private Animation hideAnim;

    private MenuAdapter mMenuAdapter;
    @BindView(R.id.list_menu)
    TvRecyclerView mMenuRecyclerView;

    private List<ItemBean> beanList;



    @BindView(R.id.TvViewPager)
    TvVHViewPager tvViewPager;

    @BindView(R.id.menu)
    LinearLayout menuLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_video;
    }

    @Override
    public void init() {
        super.init();

        fragmentList = new ArrayList<>();
        fragmentList.add(new VideoFragment());
        fragmentList.add(new LookBackFragment());
        fragmentList.add(new UserCenterFragment());
        fragmentList.add(new OutFragment());


        mainAdapter = new MainAdapter(getSupportFragmentManager());

        tvViewPager.addOnPageChangeListener(this);
        tvViewPager.setAdapter(mainAdapter);
        tvViewPager.setOffscreenPageLimit(4);
        tvViewPager.setScrollerDuration(400);

        mainAdapter.upData(fragmentList);


        initMenu();//初始化菜单数据
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void initMenu(){


        mMenuRecyclerView.setSpacingWithMargins(14, 0);

        beanList = new ArrayList<>();
        setData("直播");
        setData("回看");
        setData("用户中心");
        setData("退出");

        mMenuRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {



                if(position == tvViewPager.getCurrentItem()){

                }else {
                    doSomething();//重新刷新
                    tvViewPager.setCurrentItem(position,false);
                }

            }

            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

            }
        });

        mMenuRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        mMenuAdapter = new MenuAdapter( this, beanList);
        mMenuRecyclerView.setAdapter(mMenuAdapter);


        showAnim = AnimationUtils.loadAnimation(this,R.anim.pop_enter_anim);
        hideAnim = AnimationUtils.loadAnimation(this,R.anim.pop_exit_anim);

    }
    private void setData( String t ){
        beanList.add(new ItemBean(t));
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

                      //关闭菜单

                        dispose();
                        if(menuLayout.getVisibility() == GONE){
                            return;
                        }
                        setMenuLayout();

                    }
                });

    }
    private void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    private void setMenuLayout(){
        if(menuLayout.getVisibility() == VISIBLE){
            menuLayout.startAnimation(hideAnim);
            menuLayout.setVisibility(View.GONE);
            FocusUtil.clearFocus(mMenuRecyclerView);
            if(tvViewPager.getCurrentItem() == 1){
                EventBus.getDefault().post(new Event.UpFocusEvent(1));
            }
        }else {
            menuLayout.startAnimation(showAnim);
            menuLayout.setVisibility(View.VISIBLE);
            FocusUtil.setFocus(mMenuRecyclerView);
            doSomething();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

    //    NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:
                   // NLog.d(NLog.TAGOther,"enter--->");

                break;

            case KeyEvent.KEYCODE_BACK:    //返回键
              //  NLog.d(NLog.TAGOther,"back--->");

                //显示或关闭菜单
                if(menuLayout.getVisibility() == VISIBLE){
                    setMenuLayout();
                }else {

                }

                return true;
                // finish();

                // return true;   //这里由于break会退出，所以我们自己要处理掉 不返回上一层

               // break;

            case KeyEvent.KEYCODE_SETTINGS: //设置键
             //   NLog.d(NLog.TAGOther,"setting--->");

                break;
            case KeyEvent.KEYCODE_MENU:
              //  NLog.d(NLog.TAGOther,"MENU--->");
             //显示菜单
                setMenuLayout();
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if(menuLayout.getVisibility() == VISIBLE){
                        if(tvViewPager.getCurrentItem() == 3){
                            doSomething();
                            YoYo.with(Bounce)
                                    .duration(400)
                                    .playOn( mMenuRecyclerView.getChildAt(tvViewPager.getCurrentItem()));

                            return true;
                        }
                    }
                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
              //  NLog.d(NLog.TAGOther,"up--->");
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if(menuLayout.getVisibility() == VISIBLE){
                        if(tvViewPager.getCurrentItem() == 0){
                            doSomething();
                            YoYo.with(Bounce)
                                    .duration(400)
                                    .playOn( mMenuRecyclerView.getChildAt(tvViewPager.getCurrentItem()));

                            return true;
                        }
                    }
                }
                break;

            case     KeyEvent.KEYCODE_0:   //数字键0
              //  NLog.d(NLog.TAGOther,"0--->");
            //显示菜单
                setMenuLayout();

                break;

            case KeyEvent.KEYCODE_1:

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键

            //   NLog.d(NLog.TAGOther,"left--->");
                if(menuLayout.getVisibility() == VISIBLE){
                    doSomething();
                    YoYo.with(Shake)
                            .duration(400)
                            .playOn( mMenuRecyclerView.getChildAt(tvViewPager.getCurrentItem()));

                    return true;
                }

                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
              //  NLog.d(NLog.TAGOther,"right--->");
                if(menuLayout.getVisibility() == VISIBLE){
                    setMenuLayout();
                    return true;
                }

                break;


            case KeyEvent.KEYCODE_INFO:    //info键
            //    NLog.d(NLog.TAGOther,"info--->");

                break;

            case KeyEvent.KEYCODE_PAGE_DOWN:     //向上翻页键
            case KeyEvent.KEYCODE_MEDIA_NEXT:
             //   NLog.d(NLog.TAGOther,"page down--->");

                break;


            case KeyEvent.KEYCODE_PAGE_UP:     //向下翻页键
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
              //  NLog.d(NLog.TAGOther,"page up--->");

                break;

            case KeyEvent.KEYCODE_VOLUME_UP:   //调大声音键
              //  NLog.d(NLog.TAGOther,"voice up--->");

                break;

            case KeyEvent.KEYCODE_VOLUME_DOWN: //降低声音键
              //  NLog.d(NLog.TAGOther,"voice down--->");

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
