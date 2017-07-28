package com.cainiao.cncooperation.ui.fragment.main;


import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.activity.FriendCircleActivity;
import com.cainiao.common.base.BaseFragment;

import butterknife.OnClick;

/**
 * @function 我的信息  管理界面
 */
public class MineFragment extends BaseFragment {


    @OnClick(R.id.ll_friend_circle)
    public void jumpToFriendCircle(){
        FriendCircleActivity.show(getContext());
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

}
