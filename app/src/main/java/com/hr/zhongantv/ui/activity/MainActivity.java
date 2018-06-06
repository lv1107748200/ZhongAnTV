package com.hr.zhongantv.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseActivity;
import com.hr.zhongantv.entiy.ItemDatas;
import com.hr.zhongantv.ui.adapter.MetroAdapter;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Deprecated
public class MainActivity extends BaseActivity {

    private MetroAdapter mAdapter;

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.list)
    TvRecyclerView mRecyclerView;


    @OnClick({R.id.et})
    public void onClick(View view){
        onClick();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();

        et.setInputType(EditorInfo.TYPE_NULL);//设置弹出的键盘类型为空


        mAdapter = new MetroAdapter(this);
        mAdapter.setDatas(ItemDatas.getDatas(12));

//        MetroGridLayoutManager layoutManager = new MetroGridLayoutManager
//        (TwoWayLayoutManager.Orientation.VERTICAL, 24, 60, 10);
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new MetroTitleItemDecoration(mAdapter));//设置title
        mRecyclerView.setSpacingWithMargins(10, 10);//设置行列间距
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                onMoveFocusBorder(itemView, 1.1f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MineActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(null != mFocusBorder)
                mFocusBorder.setVisible(hasFocus);
            }
        });
    }


    public void onClick() {
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager)  et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);
    }
}
