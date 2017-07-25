package com.cainiao.cncooperation.ui.fragment.account;


import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.presenter.account.RegisterContract;
import com.cainiao.factory.presenter.account.RegisterPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    @BindView(R.id.tv_inputLayout_name)
    TextInputLayout mTvInputName;
    @BindView(R.id.tv_inputLayout_phone)
    TextInputLayout mTvInputPhone;
    @BindView(R.id.tv_inputLayout_pw)
    TextInputLayout mTvInputPw;

    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.bt_send_code)
    Button mBtSend;

    @BindView(R.id.bt_login)
    Button mBtLogin;


    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRegisterPresenter = new RegisterPresenter(this);
    }

    @OnClick(R.id.bt_login)
    void toLogin() {
        String name = mEtName.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        mRegisterPresenter.register(phone, name, password, code);
    }

    @OnClick(R.id.bt_send_code)
    void sendVerityCode() {
        mRegisterPresenter.sendVerifyCode(mEtPhone.getText().toString().trim());

        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mBtSend.setClickable(false);
                    }
                }).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                mBtSend.setClickable(true);
                mBtSend.setText("重新发送验证码");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                mBtSend.setText(aLong + "秒后重新发送");
            }
        });
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void registerSuccess(MyUser user) {
        //注册成功
        Log.d("RegisterFragment", user.getUsername());
        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void onFailure(int code, String msg) {
        //注册失败
    }

    @Override
    public void showPasswordError(String errorMsg) {
        mTvInputPw.setError(errorMsg);
    }

//    @Override
//    public void showPhoneError(String errorMsg) {
//
//    }


    @Override
    public void showPhoneError(@StringRes int str) {
        mTvInputPhone.setError(getActivity().getString(str));
    }

    @Override
    public void showNameError(String errorMsg) {
        mTvInputName.setError(errorMsg);
    }

    @Override
    public void verifyCodeError(@StringRes int str) {
        Toast.makeText(getContext(), getActivity().getString(str), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void verifyCodeFailed(@StringRes int str) {
        Toast.makeText(getContext(), getActivity().getString(str), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void verifyCodeSuccess(@StringRes int str) {
        Toast.makeText(getContext(), getActivity().getString(str), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRegisterPresenter = null;
    }
}
