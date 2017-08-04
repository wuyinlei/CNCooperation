package com.cainiao.cncooperation.ui.im;


import android.view.View;
import android.widget.RelativeLayout;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;


public class DiscoverFragment extends BaseFragment implements View.OnClickListener {




    @Override
    public int setLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void initView(View view) {
        mReFriends = (RelativeLayout) view.findViewById(R.id.re_friends);
        mReSaoyisao = (RelativeLayout) view.findViewById(R.id.re_saoyisao);
        mReYaoyiyao = (RelativeLayout) view.findViewById(R.id.re_yaoyiyao);
        mReFujin = (RelativeLayout) view.findViewById(R.id.re_fujin);
        mReGouwu = (RelativeLayout) view.findViewById(R.id.re_gouwu);
        mRePiaoliuping = (RelativeLayout) view.findViewById(R.id.re_piaoliuping);
        mReYouxi = (RelativeLayout) view.findViewById(R.id.re_youxi);

    }



    RelativeLayout mReFriends;
    RelativeLayout mReSaoyisao;
    RelativeLayout mReYaoyiyao;
    RelativeLayout mReFujin;
    RelativeLayout mRePiaoliuping;
    RelativeLayout mReGouwu;
    RelativeLayout mReYouxi;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_friends:

                break;

            case R.id.re_saoyisao:

                break;

            case R.id.re_yaoyiyao:

                break;

            case R.id.re_fujin:

                break;

            case R.id.re_gouwu:

                break;

            case R.id.re_piaoliuping:

                break;

            case R.id.re_youxi:

                break;

        }
    }
}
