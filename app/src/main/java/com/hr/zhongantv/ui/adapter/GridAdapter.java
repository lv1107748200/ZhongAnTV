/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hr.zhongantv.ui.adapter;

import android.content.Context;

import com.hr.zhongantv.R;
import com.hr.zhongantv.entiy.ItemBean;
import com.hr.zhongantv.net.entry.LookBackData;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.zhongantv.utils.CheckUtil;


public class GridAdapter extends CommonRecyclerViewAdapter<LookBackData> {
    public GridAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_one;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, LookBackData item, int position) {
        helper.getHolder()
                .setText(R.id.title, item.getName())
        .showImage(R.id.image,getUrl(item.getFirstPicture()));
    }

    private String getUrl(String url){

        if(!CheckUtil.isEmpty(url))
        url = url.replaceAll("\\\\","/");

        return url;
    }
}
