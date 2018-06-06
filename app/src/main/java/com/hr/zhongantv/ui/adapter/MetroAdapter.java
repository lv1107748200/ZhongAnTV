package com.hr.zhongantv.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseApplation;
import com.hr.zhongantv.entiy.ItemBean;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.zhongantv.utils.GlideUtil;
import com.owen.tvrecyclerview.widget.MetroGridLayoutManager;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;

/**
 * Created by owen on 2017/7/14.
 */

public class MetroAdapter extends CommonRecyclerViewAdapter<ItemBean> implements MetroTitleItemDecoration.Adapter{

    private Context context;

    public MetroAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemBean item, int position) {
        helper.getHolder()
                .setText(R.id.title, String.valueOf(position));


            GlideUtil.setGlideImage(BaseApplation.getBaseApp(),item.imgUrl,
                    (ImageView) helper.getHolder().getView(R.id.image),R.drawable.hehe);


        final View itemView = helper.itemView;
        MetroGridLayoutManager.LayoutParams lp = (MetroGridLayoutManager.LayoutParams)
                itemView.getLayoutParams();

        if(position > 8) {
            lp.sectionIndex = 1;

            lp.rowSpan = 12;
            lp.colSpan = 20;

        } else {
            lp.sectionIndex = 0;
            if(position == 0) {
                lp.rowSpan = 8;//高度比例
                lp.colSpan = 12;//宽度比例
            } else {
                lp.rowSpan = 4;
                lp.colSpan = 6;
            }
        }

        itemView.setLayoutParams(lp);
    }

    @Override
    public View getTitleView(int index, RecyclerView parent) {
        switch (index){
            case 1:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title1,
                        parent, false);
        }

        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent,
                false);
    }
}
