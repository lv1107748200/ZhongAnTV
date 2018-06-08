package com.hr.zhongantv.ui.fragment;

import android.text.SpannableStringBuilder;
import android.view.View;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseFragment;
import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.entry.LinkManRes;
import com.hr.zhongantv.net.entry.STBMessage;
import com.hr.zhongantv.net.http.HttpCallback;
import com.hr.zhongantv.net.http.HttpException;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.DisplayUtils;
import com.hr.zhongantv.utils.FocusUtil;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.utils.SpanUtils;
import com.hr.zhongantv.widget.layout.LoadingLayout;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 吕 on 2018/3/14.
 * 用户中心
 */

public class UserCenterFragment extends BaseFragment implements LoadingLayout.LoadingCallBack {

    private boolean isLoad = false;

    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    @Override
    public int getLayout() {
        return R.layout.fragment_usercenter;
    }

    @Override
    public void init() {
        super.init();
        loadingLayout.setLoadingCallBack(this);
     //   load();
    }

    @Override
    public void loadData() {
        super.loadData();
       // if(!isLoad){
            load();
      //  }
    }

    @Override
    public void isHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            if(isLoad){
             //   FocusUtil.setFocus(loadingLayout);
            }else {
              //  FocusUtil.setFocus(loadingLayout);
            }
        }
    }

    //重新加载
    @Override
    public void btnCallBack() {
        if(View.GONE == loadingLayout.getVisibility()){
            loadingLayout.setVisibility(View.VISIBLE);
        }
        loadingLayout.setLoadingLayout(LoadingLayout.ONE,null);

        load();
    }
    private void load(){

        baseService.selectByNumber(new HttpCallback<BaseResponse<STBMessage>>() {
            @Override
            public void onError(HttpException e) {
                isLoad = false;
                if(31003 == e.getCode()){
                    loadingLayout.setLoadingLayout(LoadingLayout.THREE,new LoadingLayout.ShowMain(){
                        @Override
                        public SpannableStringBuilder getSpannableStringBuilder() {
                            SpanUtils spanUtils = new SpanUtils();


                            return spanUtils.append("机顶盒没有注册为直播用户").create();
                        }
                    });
                }else {
                    loadingLayout.setLoadingLayout(LoadingLayout.TWO,null);
                   // FocusUtil.setFocus(loadingLayout.getBtnLoad());
                }

            }

            @Override
            public void onSuccess(BaseResponse<STBMessage> stbMessageBaseResponse) {

                isLoad = true;

              final  STBMessage stbMessage = stbMessageBaseResponse.getData();

                final List<CharSequence> sequenceList = new ArrayList<>();
                List<LinkManRes> linkManRes = stbMessage.getLinkManResList();

                if(!CheckUtil.isEmpty(linkManRes)){
                    for(int i =0; i<linkManRes.size();i++){
                        LinkManRes linkManRes1 = linkManRes.get(i);
                        sequenceList.add("联系人"+toChinese(""+(i+1))+" :   "+linkManRes1.getLinkman()+"  "+linkManRes1.getMobile());
                    }
                }

                loadingLayout.setLoadingLayout(LoadingLayout.FOUR,new LoadingLayout.ShowMain(){
                    @Override
                    public SpannableStringBuilder getSpannableStringBuilder() {


                        SpanUtils spanUtils = new SpanUtils();
                        return spanUtils
                                .appendLine("序  列   号 : "+stbMessage.getSerialNumber())
                                .setFontSize(DisplayUtils.sp2px(getContext(),22))
                                .appendLine()
                                .appendLine("公         司 : "+stbMessage.getEnterpriseName())
                                .setFontSize(DisplayUtils.sp2px(getContext(),22))
                                .appendLine()
                                .appendLine("主营单位 : "+stbMessage.getOrganizationName())
                                .setFontSize(DisplayUtils.sp2px(getContext(),22))
                                .appendLine()
                                .appendLine()
                                .appendLine(sequenceList)
                                .create();
                    }
                });

            }
        },UserCenterFragment.this.bindUntilEvent(FragmentEvent.PAUSE));
    }

    private String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  "+result);
        }
        return result;

    }

}
