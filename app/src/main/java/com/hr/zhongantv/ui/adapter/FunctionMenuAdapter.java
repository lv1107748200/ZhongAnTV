package com.hr.zhongantv.ui.adapter;

import android.content.Context;

import com.hr.zhongantv.R;
import com.hr.zhongantv.entiy.ItemBean;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewHolder;

/**
 * Created by 吕 on 2018/3/23.
 * item_function_menu
 */

public class FunctionMenuAdapter extends CommonRecyclerViewAdapter {


    public FunctionMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_function_menu;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(item instanceof ItemBean){
            helper.getHolder().setText(R.id.title, ((ItemBean) item).title);
        }

    }


}
