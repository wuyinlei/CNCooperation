package com.cainiao.cncooperation.ui.dynamic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.FriendCricleDetailCommentAdapter;
import com.cainiao.cncooperation.utils.ShareUtils;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.bottom.BottomDialog;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.comment.CommentEditText;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.common.widget.nineimage.ImageInfo;
import com.cainiao.common.widget.nineimage.NineGridClickViewAdapter;
import com.cainiao.common.widget.nineimage.NineGridView;
import com.cainiao.factory.Account;
import com.cainiao.factory.model.circle.DetailComment;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.presenter.dynamic.DynamicDetailContract;
import com.cainiao.factory.presenter.dynamic.DynamicDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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

    @BindView(R.id.iv_comment_bg)
    ImageView mIvComment;

    @BindView(R.id.iv_share)
    ImageView mIvShare;

    @BindView(R.id.iv_favorite)
    ImageView mIvFavorite;

    private String content;

    //角标提示
    Badge mBadgeCommentView, mBadgeFavorite;

    private BottomDialog mBottomDialog;


    //评论的view
    CheckBox mCircleCommentCheckBox;
    CommentEditText mCommentEditText;
    ImageButton mCircleCmsSend;

    @OnClick(R.id.ic_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.relayout_comment)
    public void comment() {
        addComment();
    }


    private DynamicDetailPresenter mDetailPresenter;
    private FriendCricleDetailCommentAdapter mCommentAdapter;

    private String objectId;
    private int page = 1;


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
        mCommentAdapter = new FriendCricleDetailCommentAdapter(this);
        mRecyclerCommentView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerCommentView.setAdapter(mCommentAdapter);

        mBadgeCommentView = new QBadgeView(this).bindTarget(mIvComment);
        mBadgeFavorite = new QBadgeView(this).bindTarget(mIvFavorite);
    }

    @Override
    protected void initData() {
        super.initData();

        mDetailPresenter.requestDetailData(objectId);
        mDetailPresenter.requestCommentData(Common.Constance.LIMIT_COUNT, page, objectId);


    }

    /**
     * 模拟更新此篇动态的查看
     *
     * @param viewCount 当前查看的个数
     * @param objectId  当前动态的objectId
     */
    private void updateViewCount(String viewCount, String objectId) {

        mDetailPresenter.updateViewCount(viewCount, objectId);

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


        if (viewBeen.size() > 0) {
            mLlComment.setVisibility(View.VISIBLE);
            mCommentAdapter.addData(viewBeen);
        } else {
            mLlComment.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMoreCommentDataSuccess(List<DetailComment> viewBeen) {

    }


    /**
     * 添加评论
     */
    private void addComment() {
        mBottomDialog = BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        initCommentView(view);
                    }
                }).setLayoutRes(R.layout.mind_circle_comment_edit_view)
                .setDimAmount(0.5f)
                .setTag("BottomDialog");
        mBottomDialog.show();
    }


    /**
     * 初始化评论的View
     *
     * @param view view
     */
    private void initCommentView(View view) {
        mCircleCommentCheckBox = (CheckBox) view.findViewById(R.id.circle_comment_is_anonymous_ck);
        mCommentEditText = (CommentEditText) view.findViewById(R.id.circle_comment_content_edit);
        mCircleCmsSend = (ImageButton) view.findViewById(R.id.circle_comment_send_btn);


        initSoftInput();

        hideSoftInput();

        sendComment();

    }

    /**
     * 发送评论
     */
    private void sendComment() {
        mCircleCmsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAlias = mCircleCommentCheckBox.isChecked();
                String content = mCommentEditText.getText().toString();
                mDetailPresenter.publishComment(objectId, Account.getUser(),content,isAlias);
            }
        });

    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        mCommentEditText.setOnCancelDialogImp(new CommentEditText.OnCancelDialogImp() {
            @Override
            public void onCancelDialog() {
                if (mBottomDialog != null) {
                    mBottomDialog.dismiss();
                    mBottomDialog = null;
                }
            }
        });
    }

    /**
     * 吊起软键盘
     */
    private void initSoftInput() {
        mCommentEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mCommentEditText, 0);
            }
        });

    }

    private void updateUi(FriendCircle friendCircle) {

        ImageLoader.load(friendCircle.getAuthor().getAvatar(), mCircleIvatar);
        mTvName.setText(friendCircle.getAuthor().getUsername());

        String createTime = friendCircle.getCreatedAt();
        createTime = createTime.substring(5, 16);
        mTvTime.setText(createTime);

        content = friendCircle.getContent();
        mTvContent.setText(friendCircle.getContent());

        mTvCommentCount.setText(friendCircle.getComment() + "");

        mTvViewCount.setText(friendCircle.getViewcount());

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

        //only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP , Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM , Gravity.CENTER , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ,Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END
        mBadgeCommentView.setBadgeNumber(Integer.parseInt(friendCircle.getCommentSize()));
        mBadgeCommentView.setBadgeGravity(Gravity.END | Gravity.TOP);
        mBadgeFavorite.setBadgeNumber(friendCircle.getLove());
        mBadgeFavorite.setBadgeGravity(Gravity.END | Gravity.TOP);

        updateViewCount(friendCircle.getViewcount(), friendCircle.getObjectId());
    }

    @OnClick(R.id.iv_share)
    public void share() {
        ShareUtils.showShare(this, getString(R.string.dynamic_detail), Common.Constance.GITHUB_URL, content);
    }


}
