package com.cainiao.cncooperation.ui.main;


import android.view.View;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.account.AccountActivity;
import com.cainiao.cncooperation.ui.account.PersonalActivity;
import com.cainiao.cncooperation.ui.dynamic.FriendCircleActivity;
import com.cainiao.cncooperation.ui.setting.SettingActivity;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.factory.Account;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @function 我的信息  管理界面
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.tv_username)
    TextView mTvUserName;

    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;

    @OnClick(R.id.ll_friend_circle)
    public void jumpToFriendCircle() {

        FriendCircleActivity.show(getContext());
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);


    }

    @Override
    protected void initData() {
        super.initData();

        if (Account.isLogin()) {
            ImageLoader.load(Account.getAvatar(), mIvAvatar);
            mTvUserName.setText(Account.getUserName());
        } else {
            mTvUserName.setText(getActivity().getString(R.string.mine_login_immediately));
        }

    }

    @OnClick({R.id.ll_set})
    public void jumpSetting() {
        SettingActivity.show(getContext());

    }

    @OnClick({R.id.tv_username, R.id.iv_avatar})
    public void jumpToLoginOrPersonal() {

        if (Account.isLogin()) {
            PersonalActivity.show(getContext(), Account.getUserName());
        } else {
            AccountActivity.show(getContext());
        }


    }


}
