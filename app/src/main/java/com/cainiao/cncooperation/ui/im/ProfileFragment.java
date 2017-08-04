package com.cainiao.cncooperation.ui.im;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    ImageView mIvAvatar;
    TextView mTvName;
    ImageView mIvSex;
    TextView mTvFxid;
    RelativeLayout mReMyinfo;
    RelativeLayout mReXiangce;
    RelativeLayout mReShoucang;
    RelativeLayout mReMoneyBag;
    RelativeLayout mReCardBag;
    RelativeLayout mReSetting;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(View view) {

        mIvAvatar = (ImageView) view.findViewById(R.id.iv_avatar);

        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mIvSex = (ImageView) view.findViewById(R.id.iv_sex);
        mTvFxid = (TextView) view.findViewById(R.id.tv_fxid);

        mReMyinfo = (RelativeLayout) view.findViewById(R.id.re_myinfo);
        mReXiangce = (RelativeLayout) view.findViewById(R.id.re_xiangce);
        mReShoucang = (RelativeLayout) view.findViewById(R.id.re_shoucang);
        mReMoneyBag = (RelativeLayout) view.findViewById(R.id.re_money_bag);
        mReCardBag = (RelativeLayout) view.findViewById(R.id.re_card_bag);
        mReSetting = (RelativeLayout) view.findViewById(R.id.re_setting);

    }


    @Override
    protected void initListener() {
        super.initListener();

        mReMyinfo.setOnClickListener(this);
        mReXiangce.setOnClickListener(this);
        mReShoucang.setOnClickListener(this);
        mReMoneyBag.setOnClickListener(this);
        mReCardBag.setOnClickListener(this);
        mReSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_myinfo:

                break;

            case R.id.re_xiangce:

                break;

            case R.id.re_shoucang:

                break;

            case R.id.re_money_bag:

                break;

            case R.id.re_card_bag:

                break;

            case R.id.re_setting:

                break;
        }
    }
}
