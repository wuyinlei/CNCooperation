package com.cainiao.cncooperation.ui.fragment.account;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.MainActivity;
import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.factory.Factory;
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
    TextView mBtSend;

    @BindView(R.id.bt_login)
    Button mBtLogin;

    @OnClick(R.id.fg_login)
    public void Login(){
        mAccountTrigger.triggerView();
    }

    private AccountTrigger mAccountTrigger;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRegisterPresenter = new RegisterPresenter(this);
    }

    @OnClick(R.id.bt_login)
    void toLogin() {
        mBtLogin.setClickable(false);
        mBtLogin.setBackgroundResource(R.color.white);
        mBtLogin.setTextColor(R.color.colorPrimary);
        mBtLogin.setText(getActivity().getString(R.string.register_ing));
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
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //注册成功
                        mBtLogin.setClickable(true);
                        mBtLogin.setBackgroundResource(R.color.white);
                        mBtLogin.setTextColor(R.color.springgreen);
                        mBtLogin.setText(getActivity().getString(R.string.login_ing));
                    }
                }).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                getActivity().finish();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {

            }
        });


    }

    @Override
    public void showError(@StringRes int str) {

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
    public void onDestroyView() {
        super.onDestroyView();
        mRegisterPresenter = null;
    }
}
