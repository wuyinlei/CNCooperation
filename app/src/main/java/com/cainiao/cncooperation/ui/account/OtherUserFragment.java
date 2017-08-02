package com.cainiao.cncooperation.ui.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.constant.Common;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherUserFragment extends BaseFragment {

    private String username;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_other_user;
    }

    public static Fragment newInstance(String username) {
        OtherUserFragment fragment = new OtherUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Common.Constance.USER_NAME, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        if (getArguments() != null) {
            username = getArguments().getString(Common.Constance.USER_NAME);
        }
    }

    @Override
    protected void initData() {
        super.initData();


    }
}
