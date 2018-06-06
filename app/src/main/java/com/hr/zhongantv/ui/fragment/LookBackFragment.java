package com.hr.zhongantv.ui.fragment;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.view.View;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseFragment;
import com.hr.zhongantv.net.base.BaseResponse;
import com.hr.zhongantv.net.entry.LookBackData;
import com.hr.zhongantv.net.http.HttpCallback;
import com.hr.zhongantv.net.http.HttpException;
import com.hr.zhongantv.ui.activity.LookBackIjkActivity;
import com.hr.zhongantv.ui.adapter.GridAdapter;
import com.hr.zhongantv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.DisplayUtils;
import com.hr.zhongantv.utils.FocusUtil;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.utils.NToast;
import com.hr.zhongantv.utils.SpanUtils;
import com.hr.zhongantv.widget.layout.LoadingLayout;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 吕 on 2018/3/14.
 * 回看
 */

public class LookBackFragment extends BaseFragment implements LoadingLayout.LoadingCallBack{

    private CommonRecyclerViewAdapter mAdapter;
    @BindView(R.id.list)
    TvRecyclerView mRecyclerView;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;


    @Override
    public int getLayout() {
        return R.layout.fragment_lookback;
    }

    @Override
    public void init() {
        super.init();
        //loadingLayout.setLoad_layout(R.drawable.video_back);
        loadingLayout.setLoadingCallBack(this);

        mRecyclerView.setSpacingWithMargins(0, DisplayUtils.getDimen(getContext(),R.dimen.x32));

        mAdapter = new GridAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                onMoveFocusBorder(itemView, 1.1f, 0);

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                LookBackData lookBackData = (LookBackData) mAdapter.getItem(position);

                Intent intent = new Intent(getContext(),LookBackIjkActivity.class);
                intent.putExtra("LookBackData",lookBackData);
                startActivity(intent);

            }
        });

        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });


         mRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {

                mRecyclerView.setLoadingMore(true); //正在加载数据
                isLoadMore = true;
                load();
                return isMore; //是否还有更多数据
            }
        });

    }

    @Override
    public void loadData() {
        super.loadData();

        NLog.d(NLog.TAGOther,"LookBackFragment:loadData--->");
        if(CheckUtil.isEmpty(mAdapter.getmDatas())){
            if(View.GONE == loadingLayout.getVisibility()){
                loadingLayout.setVisibility(View.VISIBLE);
            }
            loadingLayout.setLoadingLayout(LoadingLayout.ONE,null);
            load();
        }else {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingLayout.setLoadingLayout(LoadingLayout.ONE,null);
            isLoadMore = false;
            isMore = true;
            pageNo = 1;
            load();
        }
    }

    @Override
    public void stopLoad() {
        super.stopLoad();
        mFocusBorder.setVisible(false);
    }

    //重新加载
    @Override
    public void btnCallBack() {
        loadingLayout. setLoadingLayout(LoadingLayout.ONE,null);
         load();

//        LookBackData lookBackData = new LookBackData();
//
//        lookBackData.setUrl("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4");
//        lookBackData.setName("哈哈");

//        Intent intent = new Intent(getContext(),LookBackIjkActivity.class);
//        intent.putExtra("LookBackData",lookBackData);
//        startActivity(intent);

    }
    private void load(){

        baseService.selectAll(pageNo+"", new HttpCallback<BaseResponse<List<LookBackData>>>() {
            @Override
            public void onError(HttpException e) {
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

//                    List<LookBackData> lookBackDataList = new ArrayList<>();
//                    for(int i = 0 ; i< 19 ; i++){
//                                LookBackData lookBackData = new LookBackData();
//        lookBackData.setUrl("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4");
//        lookBackData.setName("哈哈");
//                            lookBackDataList.add(lookBackData);
//                        }
//                    mAdapter.repaceDatas(lookBackDataList);
//                    FocusUtil.setFocus(mRecyclerView);

                }
            }

            @Override
            public void onSuccess(BaseResponse<List<LookBackData>> listBaseResponse) {

                List<LookBackData> lookBackDataList = listBaseResponse.getData();

                if(isLoadMore){
                    mRecyclerView.setLoadingMore(false);
                }

                if(!CheckUtil.isEmpty(lookBackDataList)){
                    loadingLayout.setVisibility(View.GONE);
                    pageNo = pageNo+1;
                    if(isLoadMore){
                        mAdapter.appendDatas(lookBackDataList);
                    }else {

//                        for(int i = 0 ; i< 19 ; i++){
//                            lookBackDataList.add(lookBackDataList.get(0));
//                        }

                        mAdapter.repaceDatas(lookBackDataList);
                        FocusUtil.setFocus(mRecyclerView);
                    }


                }else {
                    if(isLoadMore){
                        NToast.shortToastBaseApp("暂无更多数据");
                        isMore = false;

                    }else {
                        loadingLayout.setVisibility(View.VISIBLE);

                        loadingLayout.setLoadingLayout(LoadingLayout.TWO,new LoadingLayout.ShowMain(){

                            @Override
                            public String getText() {
                                return "暂无回看记录";
                            }
                        });
                    }

                }

            }
        },LookBackFragment.this.bindUntilEvent(FragmentEvent.PAUSE));

    }
}
