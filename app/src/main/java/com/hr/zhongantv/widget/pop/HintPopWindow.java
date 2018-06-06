package com.hr.zhongantv.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hr.zhongantv.R;
import com.hr.zhongantv.ui.activity.VideoActivity;
import com.hr.zhongantv.utils.FocusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 吕 on 2018/3/20.
 */

public class HintPopWindow extends BasePopupWindow{


    @BindView(R.id.layoutQD)
    LinearLayout layoutQD;
    @BindView(R.id.layoutQX)
    LinearLayout layoutQX;
    @OnClick({R.id.layoutQD,R.id.layoutQX})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.layoutQD:
                if(null != hintCallBack){
                    hintCallBack.onItemSelected(0);
                }
                break;
            case R.id.layoutQX:
                dismiss();
                break;
        }
    }


    private Activity context;

    private HintCallBack hintCallBack;
    public void setHintCallBack(HintCallBack menuCallBack) {
        this.hintCallBack = menuCallBack;
    }

    public HintPopWindow(Activity context, CustomPopuWindConfig config) {
        super(config);
        this.context = context;
    }

    @Override
    public View getView(Context context ) {

        final View view = LayoutInflater.from(context).inflate(R.layout.pop_hint,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void show(View parent) {
        super.show(parent);
        FocusUtil.setFocus(layoutQD);
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER,0, 0);

//            int[] location = new int[2];
//            parent.getLocationOnScreen(location);
//            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]+ DisplayUtils.dip2px(context,15), location[1]-this.getHeight());

        } else {
            this.dismiss();
        }
    }

    @Override
    public void onDiss() {

        if(null != hintCallBack){
            hintCallBack.isDimss();
        }

    }


    public interface HintCallBack{
        void onItemSelected(int position);
        void isDimss();
    }
}
