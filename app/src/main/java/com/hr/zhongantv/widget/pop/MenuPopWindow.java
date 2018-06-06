package com.hr.zhongantv.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hr.zhongantv.R;
import com.hr.zhongantv.entiy.ItemBean;
import com.hr.zhongantv.entiy.ItemDatas;
import com.hr.zhongantv.ui.activity.VideoActivity;
import com.hr.zhongantv.ui.adapter.MenuAdapter;
import com.hr.zhongantv.utils.NLog;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 吕 on 2018/1/17.
 */

public class MenuPopWindow extends BasePopupWindow {


    private VideoActivity context;

    private MenuAdapter mMenuAdapter;
    @BindView(R.id.list_menu)
    TvRecyclerView mMenuRecyclerView;

    private List<ItemBean> beanList;

    private MenuCallBack menuCallBack;
    public void setMenuCallBack(MenuCallBack menuCallBack) {
        this.menuCallBack = menuCallBack;
    }

    public MenuPopWindow(VideoActivity context, CustomPopuWindConfig config) {
        super(config);
        this.context = context;
        mMenuRecyclerView.setSpacingWithMargins(14, 0);

        beanList = new ArrayList<>();
        setData("直播");
        setData("回看");
        setData("用户中心");
        setData("退出");



        mMenuAdapter = new MenuAdapter( context, beanList);
        mMenuRecyclerView.setAdapter(mMenuAdapter);


        mMenuRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                if(null != MenuPopWindow.this.menuCallBack){
                    MenuPopWindow.this.menuCallBack.onItemSelected(parent,itemView,position);
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
    }

    @Override
    public View getView(Context context ) {

        final View view = LayoutInflater.from(context).inflate(R.layout.pop_menu,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void show(View parent) {
        super.show(parent);

        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.LEFT,0, 0);

//            int[] location = new int[2];
//            parent.getLocationOnScreen(location);
//            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]+ DisplayUtils.dip2px(context,15), location[1]-this.getHeight());

        } else {
            this.dismiss();
        }
    }

    @Override
    public void onDiss() {

    }

    private void setData( String t ){
        beanList.add(new ItemBean(t));
    }


    public interface MenuCallBack{
         void onItemSelected(TvRecyclerView parent, View itemView, int position);
    }
}
