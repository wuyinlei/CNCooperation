package com.cainiao.cncooperation.ui.dynamic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.common.widget.nineimage.ImageInfo;
import com.cainiao.common.widget.nineimage.NineGridClickViewAdapter;
import com.cainiao.common.widget.nineimage.NineGridView;
import com.cainiao.factory.model.circle.DetailComment;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.presenter.dynamic.DynamicDetailContract;
import com.cainiao.factory.presenter.dynamic.DynamicDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FriendCircleDetailActivity extends BaseActivity implements DynamicDetailContract.View {

    @BindView(R.id.iv_avatar)
    CircleImageView mCircleIvatar;


    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.image_gender)
    ImageView mImageGender;

    @BindView(R.id.tv_content)
    TextView mTvContent;

    @BindView(R.id.nineGrid)
    NineGridView mNineGrid;

    @BindView(R.id.tv_comment_count)
    TextView mTvCommentCount;

    @BindView(R.id.tv_view_count)
    TextView mTvViewCount;

    @BindView(R.id.re_comment)
    LinearLayout mLlComment;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerCommentView;

    @OnClick(R.id.ic_back)
    public void back(){
        finish();
    }

    private DynamicDetailPresenter mDetailPresenter;

    private String objectId;

    public static void show(Context context, String objectId) {
        Intent intent = new Intent(context, FriendCircleDetailActivity.class);
        intent.putExtra(Common.Constance.OBECJT_ID, objectId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        objectId = bundle.getString(Common.Constance.OBECJT_ID);
        return true;
    }

    @Override
    protected void initView() {
        super.initView();

        mDetailPresenter = new DynamicDetailPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();

        mDetailPresenter.requestDetailData(objectId);
        mDetailPresenter.requestCommentData(10, objectId);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_friend_circle_detail;
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
    public void requestDataSuccess(FriendCircle friendCircle) {
        updateUi(friendCircle);
    }



    @Override
    public void requestCommentDataSuccess(List<DetailComment> viewBeen) {
        Toast.makeText(this, "viewBeen.size():" + viewBeen.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreCommentDataSuccess(List<DetailComment> viewBeen) {

    }


    private void updateUi(FriendCircle friendCircle) {
        ImageLoader.load(friendCircle.getAuthor().getAvatar(), mCircleIvatar);
        mTvName.setText(friendCircle.getAuthor().getUsername());

        String createTime = friendCircle.getCreatedAt();
        createTime = createTime.substring(5, 16);
        mTvTime.setText(createTime);

        mTvContent.setText(friendCircle.getContent());

        mTvCommentCount.setText(friendCircle.getComment() + "");


//        mTvViewCount.setText(friendCircle.getViewcount() == null ? "100":friendCircle.getViewcount() + "");


        if (friendCircle.getCircleimages() != null && friendCircle.getCircleimages().size() > 0) {
            List<ImageInfo> infoimages = new ArrayList<>();
            for (int i = 0; i < friendCircle.getCircleimages().size(); i++) {
                ImageInfo image = new ImageInfo();
                image.setThumbnailUrl(friendCircle.getCircleimages().get(i));
                infoimages.add(image);
            }
            mNineGrid.setVisibility(View.VISIBLE);
            mNineGrid.setAdapter(new NineGridClickViewAdapter(this, infoimages));
        } else {
            mNineGrid.setVisibility(View.GONE);
        }
    }

}
