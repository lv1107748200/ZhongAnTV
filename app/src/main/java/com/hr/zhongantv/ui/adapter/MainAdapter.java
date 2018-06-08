package com.hr.zhongantv.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.widget.view.vhpager.YFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吕 on 2017/12/11.
 */

public class MainAdapter extends YFragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(!CheckUtil.isEmpty(fragmentList)){
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(!CheckUtil.isEmpty(fragmentList)){
            return fragmentList.size();
        }
        return 0;
    }

    public void upData(List list){
        if(CheckUtil.isEmpty(list))
            return;
        if(CheckUtil.isEmpty(fragmentList)){
            fragmentList = new ArrayList<>();
        }else {
            fragmentList.clear();
        }
        fragmentList.addAll(list);
        notifyDataSetChanged();
    }
}
