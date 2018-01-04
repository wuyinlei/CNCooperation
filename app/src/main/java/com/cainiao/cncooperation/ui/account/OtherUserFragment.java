package com.cainiao.cncooperation.ui.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.im.ChatActivity;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.base.PresenterFragment;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.presenter.account.OtherUserContract;
import com.cainiao.factory.presenter.account.OtherUserPresenter;
import com.cainiao.factory.presenter.account.PersonalContract;
import com.cainiao.factory.utils.BmobUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;

/**
 * @function 其他用户的显示界面
 */
public class OtherUserFragment extends PresenterFragment<OtherUserContract.Presenter> implements OtherUserContract.View {

    private String objectId;

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_fxid)
    TextView mTvFxid;

    @BindView(R.id.tv_address)
    TextView mTvAddress;

    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @BindView(R.id.bt_add)
    Button mBtAdd;

    @BindView(R.id.bt_chat)
    Button mBtChat;

    MyUser mTargetUser;

    @BindView(R.id.et_alias)
    EditText mEtAlias;

    PersonalActivity activity;


    @Override
    public int setLayoutId() {
        return R.layout.fragment_other_user;
    }

    public static Fragment newInstance(String username) {
        OtherUserFragment fragment = new OtherUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Common.Constance.OBECJT_ID, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        if (getArguments() != null) {
            objectId = getArguments().getString(Common.Constance.OBECJT_ID);
        }

        activity = (PersonalActivity) getActivity();
        activity.mStateView.showLoading();
    }

    @Override
    protected void initData() {
        super.initData();

        activity.mStateView.showLoading();


        mPresenter.queryUserInfo(objectId);



    }

    @Override
    protected OtherUserContract.Presenter initPresenter() {
        return new OtherUserPresenter(this);
    }

    @Override
    public void onQueryUserInfoFailure(int errorCode, String errMsg) {

    }

    @Override
    public void onQueryUserInfoSuccess(MyUser user) {

        mPresenter.queryIsFriend(objectId);

        activity.mStateView.showContent();
        mTargetUser = user;
        ImageLoader.load(user.getAvatar(), mIvAvatar);
        mTvName.setText(user.getUsername());
        mTvFxid.setText(String.format(getActivity().getResources().getString(R.string.cn_code_tip), user.getObjectId()));
        mTvAddress.setText(user.getAddress() == null ? "" : user.getAddress());
        mTvDesc.setText(user.getDescription() == null ? "" : user.getDescription());
    }

    @Override
    public void isFriend(boolean isFriend) {
        if (isFriend) {
            mBtAdd.setVisibility(View.GONE);
            mBtChat.setVisibility(View.VISIBLE);
        } else {
            mBtAdd.setVisibility(View.VISIBLE);
            mBtChat.setVisibility(View.GONE);
        }
    }

    @Override
    public void addFriendSuccess(String data) {
        mBtAdd.setVisibility(View.GONE);
        mBtChat.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_add)
    public void addFriend() {
        //其实应该进行添加朋友

        String alias = mEtAlias.getText().toString();
        if (!TextUtils.isEmpty(alias)) {

            mPresenter.addFriend(Account.getUser(), mEtAlias.getText().toString(), mTargetUser);

        } else {
            mPresenter.addFriend(Account.getUser(),mTargetUser.getUsername(),mTargetUser);
        }
        //这里直接进行了单聊
        //ChatActivity.show(getContext(),objectId, Conversation.ConversationType.PRIVATE);

    }

    @OnClick(R.id.bt_chat)
    public void chatMessage() {
        //其实应该进行添加朋友

        //这里直接进行了单聊
        ChatActivity.show(getContext(), objectId, Conversation.ConversationType.PRIVATE);


    }


}
