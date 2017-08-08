package com.cainiao.cncooperation.ui.account;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.PresenterFragment;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.presenter.account.RegisterContract;
import com.cainiao.factory.presenter.account.RegisterPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View, View.OnClickListener {

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
    TextView mBtSend;

    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.txt_go_login)
    TextView mTxtGoLogin;
    @BindView(R.id.fg_login)
    FrameLayout mFgLogin;
    Unbinder unbinder;

    private Fragment mCurrentFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;
    private FragmentTransaction transaction;

    @OnClick(R.id.fg_login)
    public void Login() {
        mAccountTrigger.triggerView();
    }

    private AccountTrigger mAccountTrigger;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mTxtGoLogin.setOnClickListener(this);
    }

    //    @OnClick(R.id.bt_login)
    //    void toLogin() {
    //        mBtLogin.setClickable(false);
    //        mBtLogin.setBackgroundResource(R.color.white);
    //        mBtLogin.setTextColor(R.color.colorPrimary);
    //        mBtLogin.setText(getActivity().getString(R.string.register_ing));
    //        String name = mEtName.getText().toString().trim();
    //        String phone = mEtPhone.getText().toString().trim();
    //        String password = mEtPassword.getText().toString().trim();
    //        String code = mEtCode.getText().toString().trim();
    //        mRegisterPresenter.register(phone, name, password, code);
    //    }

    @OnClick(R.id.bt_login)
    void toLogin() {

        String name = mEtName.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();

        //判断是否为空
        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(phone) &
                !TextUtils.isEmpty(code) &
                !TextUtils.isEmpty(password)) {

            //判断两次输入的密码是否一致
            if (password.equals(password)) {
                //注册
                MyUser user = new MyUser();
                user.setUsername(name);
                user.setPassword(password);
                user.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), R.string.register_success, Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStackImmediate();
                        } else {
                            Toast.makeText(getContext(), getString(R.string.register_failure) + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), R.string.change_password_def, Toast.LENGTH_SHORT).show();
            }
        }

    }


    @OnClick(R.id.bt_send_code)
    void sendVerityCode() {
        mPresenter.sendVerifyCode(mEtPhone.getText().toString().trim());

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
                        Toast.makeText(getContext(),
                                getActivity().getString(R.string.retry_msg_code_error),
                                Toast.LENGTH_SHORT).show();
                        mBtSend.setClickable(false);
                    }
                }).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                mBtSend.setClickable(true);
                mBtSend.setText(getActivity().getString(R.string.retry_msg_code_error));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                mBtSend.setText(String.format(getActivity().getString(R.string.interval_msg_code), aLong));
            }
        });
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_register;
    }


    @Override
    public void registerSuccess(MyUser user) {
//        Observable.interval(1, TimeUnit.SECONDS)
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        //注册成功
//                        mBtLogin.setClickable(true);
//                        mBtLogin.setBackgroundResource(R.color.white);
//                        mBtLogin.setTextColor(R.color.springgreen);
//                        mBtLogin.setText(getActivity().getString(R.string.login_ing));
//                        getActivity().getFragmentManager().popBackStack();
//                    }
//                }).subscribe(new Subscriber<Long>() {
//            @Override
//            public void onCompleted() {
//                getActivity().finish();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//
//            }
//        });
   //     getFragmentManager().popBackStackImmediate();

    }

    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onFailure(int code, String msg) {
        //注册失败
        mBtLogin.setClickable(true);
        mBtLogin.setBackgroundResource(R.color.white);
        mBtLogin.setTextColor(R.color.colorAccent);
        mBtLogin.setText(getActivity().getString(R.string.register_failure));
    }

    @Override
    public void showPasswordError(@StringRes int str) {
        mTvInputPw.setError(getActivity().getString(str));
    }


    @Override
    public void showPhoneError(@StringRes int str) {
        mTvInputPhone.setError(getActivity().getString(str));
    }

    @Override
    public void showNameError(@StringRes int str) {
        mTvInputName.setError(getActivity().getString(str));
    }

    @Override
    public void verifyCodeError(@StringRes final int str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), getActivity().getString(str), Toast.LENGTH_SHORT).show();
            }
        });
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
    public void registering(@StringRes int str) {
        mBtLogin.setClickable(false);
        mBtLogin.setBackgroundResource(R.color.white);
        mBtLogin.setTextColor(R.color.colorPrimary);
        mBtLogin.setText(getActivity().getString(str));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_go_login:
                transaction = getFragmentManager().beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                mCurrentFragment = loginFragment;
                transaction.replace(R.id.lay_container, mCurrentFragment);
                transaction.commit();
                break;
            default:
                break;
        }
    }
}
