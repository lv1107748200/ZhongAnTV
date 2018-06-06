package com.hr.zhongantv.ui.adapter;

import android.content.Context;

import com.hr.zhongantv.R;
import com.hr.zhongantv.entiy.ItemBean;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewHolder;

import java.util.List;

/**
 * Created by 吕 on 2018/3/13.
 */

public class MenuAdapter extends CommonRecyclerViewAdapter {

    public MenuAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_list_menu;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(item instanceof ItemBean){
            helper.getHolder().setText(R.id.title, ((ItemBean) item).title);
        }

    }
}
