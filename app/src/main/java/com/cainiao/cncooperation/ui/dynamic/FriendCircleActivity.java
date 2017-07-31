package com.cainiao.cncooperation.ui.dynamic;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.FriendCircleAdapter;
import com.cainiao.cncooperation.ui.message.MessageActivity;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.factory.Account;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.CircleViewBean;
import com.cainiao.factory.presenter.dynamic.FriendCircleContract;
import com.cainiao.factory.presenter.dynamic.FriendCirclePresenter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private int index;
    private boolean hasMore, isFirst;

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

        MyUser user = Account.getUser();

        ImageLoader.load(user.getAvatar(), mCircleAvatar);
        mCircleNameTxt.setText(Account.getUser().getUsername());

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
                mCirclePresenter.requestData(10);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {

                    mCirclePresenter.loadMoreData(10, 10 * ++index);
                } else {
                    //已经加载完所有数据
                    mRefreshLayout.finishLoadmore();
                }
            }
        });

        mRefreshLayout.startRefresh();


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.Constance.DYNAMIC_REQUEST_CODE && resultCode == Common.Constance.DYNAMIC_RESULT_CODE) {
            //模拟本地发动态  但是图片不行现在

            addComment(data);

        }

    }

    /**
     * 添加本地评论  模拟添加 构造的数据由前一个编辑界面带来的数据
     *
     * @param data Data
     */
    private void addComment(Intent data) {
        ArrayList<String> image = data.getStringArrayListExtra(Common.Constance.DYNAMIC_IMAGE);
        String content = data.getStringExtra(Common.Constance.DYNAMIC_CONTENT);
        String objectId = data.getStringExtra(Common.Constance.OBECJT_ID);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        //构造javabean数据
        CircleViewBean viewBean = new CircleViewBean();
        viewBean.setCommentcount("0");
        viewBean.setObjectId(objectId);
        viewBean.setContent(content);
        viewBean.setCreateDate(currentTime);
        viewBean.setUsername(Account.getUserName());
        viewBean.setAvator(Account.getAvatar());
        viewBean.setViewcount("1");
        viewBean.setLikescount("0");

        //刷新数据
        mAdapter.addData(viewBean);
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
        mRefreshLayout.finishLoadmore();
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
        if (!isFirst) {
            isFirst = true;
            return;
        }
        mAdapter.refreshData(viewBeen);
        mRefreshLayout.finishRefreshing();
        hasMore = viewBeen.size() >= 10;
    }

    @Override
    public void loadMoreDataSuccess(List<CircleViewBean> viewBeen) {
        mAdapter.addData(viewBeen);
        hasMore = viewBeen.size() >= 10;
        mRefreshLayout.finishLoadmore();
    }
}
