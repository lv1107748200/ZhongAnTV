package com.hr.zhongantv.ui.activity;

import android.content.Intent;
import android.view.View;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseActivity;
import com.hr.zhongantv.entiy.ItemDatas;
import com.hr.zhongantv.ui.adapter.GridAdapter;
import com.hr.zhongantv.ui.adapter.MenuAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import butterknife.BindView;

/**
 * Created by 吕 on 2018/3/13.
 */
@Deprecated
public class MineActivity extends BaseActivity {

    private CommonRecyclerViewAdapter mAdapter;
    private MenuAdapter mMenuAdapter;

    @BindView(R.id.list)
    TvRecyclerView mRecyclerView;
    @BindView(R.id.list_menu)
    TvRecyclerView mMenuRecyclerView;
    @Override
    public int getLayout() {
        return R.layout.activity_mine;
    }

    @Override
    public void init() {
        super.init();

        mMenuRecyclerView.setSpacingWithMargins(14, 0);
        mMenuAdapter = new MenuAdapter(this, ItemDatas.getDatas(3));
        mMenuRecyclerView.setAdapter(mMenuAdapter);


        mMenuRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }

        });


        mRecyclerView.setSpacingWithMargins(15, 15);

        mAdapter = new GridAdapter(this);
        mAdapter.setDatas(ItemDatas.getDatas(60));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                onMoveFocusBorder(itemView, 1.1f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        mMenuRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mRecyclerView.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });

        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mMenuRecyclerView.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });
    }
}
