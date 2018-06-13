package com.hr.zhongantv.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseFragment;
import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.http.HttpCallback;
import com.hr.zhongantv.net.http.HttpException;
import com.hr.zhongantv.ui.activity.LookBackIjkActivity;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.DisplayUtils;
import com.hr.zhongantv.utils.FocusUtil;
import com.hr.zhongantv.widget.pop.CustomPopuWindConfig;
import com.hr.zhongantv.widget.pop.HintPopWindow;
import com.hr.zhongantv.widget.single.ClientInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 吕 on 2018/3/20.
 */

public class OutFragment extends BaseFragment implements HintPopWindow.HintCallBack {

    private long firstTime=0;
    private HintPopWindow hintPopWindow;

    @BindView(R.id.out_btn)
    Button outBtn;

    @OnClick({R.id.out_btn})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.out_btn:



//                if(!CheckUtil.isEmpty(ClientInfo.getInstance().getURL())){
//                    boxOffLine();
//                }else {
//
//                }
                if (System.currentTimeMillis()-firstTime>2000){
                    Toast.makeText(getContext(),"再按一次退出",Toast.LENGTH_SHORT).show();
                    firstTime=System.currentTimeMillis();
                }else{
                    getActivity().finish();
                }

//                if(null == hintPopWindow){
//                    hintPopWindow = new HintPopWindow(getActivity(),new CustomPopuWindConfig
//                            .Builder(getActivity())
//                            .setOutSideTouchable(true)
//                            .setFocusable(true)
//                            .setAnimation(R.style.popup_hint_anim_one)
//                            .setTouMing(true)
//                            .setWith(DisplayUtils.getScreenWidth(getContext())/2)
//                            .build());
//                    hintPopWindow.setHintCallBack(OutFragment.this);
//
//                    hintPopWindow.show(view);
//                }else {
//                    hintPopWindow.show(view);
//                }

                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_out;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loadData() {
        super.loadData();
      //  FocusUtil.setFocus(outBtn);
    }

    @Override
    public void stopLoad() {
        super.stopLoad();
    }

    @Override
    public void onItemSelected(int position) {

        getActivity().finish();

    }

    @Override
    public void isDimss() {
       // FocusUtil.setFocus(outBtn);
    }

    private void boxOffLine(){
        baseService.boxOffLine(new HttpCallback<BaseResponse>() {
            @Override
            public void onError(HttpException e) {
                getActivity().finish();
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                getActivity().finish();
            }
        });
    }
}
