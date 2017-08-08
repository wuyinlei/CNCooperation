package com.cainiao.cncooperation.ui.account;


import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.base.PresenterFragment;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.presenter.account.PersonalContract;
import com.cainiao.factory.presenter.account.RegisterContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wuyinlei
 * @function 这个是用户自己的详情界面
 */
public class PersonalFragment extends PresenterFragment<PersonalContract.Presenter>
        implements PersonalContract.View{


    @BindView(R.id.profile_avatar)
    CircleImageView mIvAvatar;

    @BindView(R.id.profile_nickname_txt)
    TextView mUserAlias;

    @BindView(R.id.profile_address_txt)
    TextView mUserAdress;

    @BindView(R.id.profile_gender_txt)
    TextView mUserGender;

    @BindView(R.id.profile_dob_txt)
    TextView mUserBirth;

    @BindView(R.id.profile_slogan_txt)
    TextView mUserSolgon;

    @BindView(R.id.profile_mobile_txt)
    TextView mUserPhone;

    @Override
    protected void initData() {
        super.initData();

        updateUI(Account.getUser());

    }

    private void updateUI(MyUser user) {

        ImageLoader.load(user.getAvatar(),mIvAvatar);

        mUserAlias.setText(user.getAlias());

        mUserAdress.setText(user.getAddress());

        mUserGender.setText(user.getSex() ? "女" : "男");

        mUserBirth.setText(user.getBirthday());

        mUserSolgon.setText(user.getDescription());

        if (user.getMobilePhoneNumberVerified() != null && user.getMobilePhoneNumberVerified()) {
            mUserPhone.setText(user.getMobilePhoneNumber());
        }

    }

    @OnClick(R.id.ic_back)
    public void close(){
        getActivity().finish();
    }

    @OnClick(R.id.tv_save)
    public void save(){

    }

    //点击了用户
    @OnClick(R.id.profile_avatar_layout)
    public void clickUpdatePortrait(){

    }

    @OnClick(R.id.profile_nickname_layout)
    public void clickUpdateAlias(){
        EditorInfoActivity.show(getActivity(), Common.Constance.USER_NICK_NAME_TYPE,getActivity().getResources().getString(R.string.alias_username));
    }

    //点击更改地址
    @OnClick(R.id.profile_address_layout)
    public void clickUpdateAddress(){

    }

    //更改用户的性别
    @OnClick(R.id.profile_gender_layout)
    public void clickUpdateGender(){

    }

    //点击更改生日
    @OnClick(R.id.profile_dob_layout)
    public void clickUpdateBirth(){

    }

    //点击更改个性签名
    @OnClick(R.id.profile_slogan_layout)
    public void clickUserDesc(){

    }

    //点击绑定手机
    @OnClick(R.id.profile_mobile_layout)
    public void clickBindPhone(){

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void showLoading() {

    }



    @Override
    public void onSaveFailure(int errorCode, String errMsg) {
        Log.d("PersonalFragment", "errorCode:" + errorCode);
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveSuccess(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
