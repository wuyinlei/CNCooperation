package com.cainiao.cncooperation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.FriendCircleAdapter;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.factory.Account;
import com.cainiao.factory.Factory;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.CircleViewBean;
import com.cainiao.factory.presenter.dynamic.FriendCircleContract;
import com.cainiao.factory.presenter.dynamic.FriendCirclePresenter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FriendCircleActivity extends BaseActivity implements FriendCircleContract.View {

    @BindView(R.id.action_mindcirrcle_message)
    RelativeLayout mActionBack;

    @BindView(R.id.action_mindcircle_view)
    ImageView mActionImageBack;

    @BindView(R.id.action_bar_title)
    TextView mActionBarTitle;

    @BindView(R.id.action_mindcirrcle_publish)
    RelativeLayout mActionPublish;

    @BindView(R.id.action_create_btn)
    ImageView mActionImagePublish;

    @BindView(R.id.circle_banner)
    ImageView mCircleBanner;

    @BindView(R.id.circle_name_txt)
    TextView mCircleNameTxt;

    @BindView(R.id.circle_avatar)
    ImageView mCircleAvatar;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;

    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarlayout;

    @BindView(R.id.recycler_view)
    RecyclerView mCircleRecycler;

    private FriendCirclePresenter mCirclePresenter;
    private FriendCircleAdapter mAdapter;

    /**
     * 显示这个view
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, FriendCircleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_friend_circle;
    }

    @Override
    protected void initView() {
        super.initView();

        mActionImageBack.setBackgroundResource(R.drawable.neardynamic_top);
        mActionBarTitle.setText(getString(R.string.circle_content_publish));
        mActionImagePublish.setBackgroundResource(R.drawable.mind_circle_action_camera);
        mRefreshLayout.setTargetView(mCircleRecycler);

        mCirclePresenter = new FriendCirclePresenter(this);

    }

    @Override
    protected void initData() {
        super.initData();

        setupRecyclerView(mCircleRecycler);

        mCirclePresenter.requestData(Common.Constance.LIMIT_COUNT);


    }

    @Override
    protected void initListener() {
        super.initListener();

    }


    /**
     * 相关设置
     *
     * @param recyclerView RecyclerView
     */
    private void setupRecyclerView(RecyclerView recyclerView) {
        mAdapter = new FriendCircleAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        ProgressLayout header = new ProgressLayout(this);
        mRefreshLayout.setHeaderView(header);
        mRefreshLayout.setFloatRefresh(true);
        mRefreshLayout.setEnableOverScroll(false);
        mRefreshLayout.setHeaderHeight(140);
        mRefreshLayout.setMaxHeadHeight(240);
        mRefreshLayout.setTargetView(recyclerView);


        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mRefreshLayout.setEnableRefresh(true);
                    mRefreshLayout.setEnableOverScroll(false);
                } else {
                    mRefreshLayout.setEnableRefresh(false);
                    mRefreshLayout.setEnableOverScroll(false);
                }
            }
        });
    }


    @OnClick(R.id.action_mindcirrcle_publish)
    public void jumpToPublish() {
        if (Account.isLogin())
            DynamicPublishActivity.show(this);
        else {
            //跳转到登录界面

        }
    }


    @OnClick(R.id.action_mindcirrcle_message)
    public void jumpToMessage() {
        MessageActivity.show(this);
    }


    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void onLikesFailure(int code, String msg) {

    }

    @Override
    public void onLikesSuccess(@StringRes int str) {

    }

    @Override
    public void onCommentFailure(int code, String msg) {

    }

    @Override
    public void onCommentSuccess(@StringRes int str, String content) {

    }

    @Override
    public void requestDataSuccess(List<CircleViewBean> viewBeen) {
        mAdapter.addData(viewBeen);
    }

    @Override
    public void loadMoreDataSuccess(List<CircleViewBean> viewBeen) {

    }
}
