package com.cainiao.cncooperation.ui.account;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.activity.MainActivity;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.utils.HashUtil;
import com.cainiao.common.utils.SharedUtils;
import com.cainiao.factory.model.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {


    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.keep_password)
    CheckBox mKeepPassword;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.btn_registered)
    Button mBtnRegistered;
    @BindView(R.id.tv_forget)
    TextView mTvForget;
    @BindView(R.id.login_qq)
    TextView mLoginQq;
    Unbinder unbinder;
    private Fragment mCurrentFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        boolean isKeep = SharedUtils.getBoolean(getContext(), "keeppass", false);
        mKeepPassword.setChecked(isKeep);
        if (isKeep) {
            String name = SharedUtils.getString(getContext(), "name", "");
            String password = SharedUtils.getString(getContext(), "password", "");
            mEtName.setText(name);
            mEtPassword.setText(password);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        assert rootView != null;
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btnLogin, R.id.btn_registered, R.id.tv_forget, R.id.login_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                //1.获取输入框的值
                String name = mEtName.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            //判断结果
                            if (e == null) {

                                //保存状态
                                SharedUtils.putBoolean(getContext(), "keeppass", mKeepPassword.isChecked());

                                //是否记住密码
                                if (mKeepPassword.isChecked()) {
                                    //记住用户名和密码
                                    SharedUtils.putString(getContext(), "name", mEtName.getText().toString().trim());
                                    SharedUtils.putString(getContext(), "password", mEtPassword.getText().toString().trim());
                                } else {
                                    SharedUtils.deleShare(getContext(), "name");
                                    SharedUtils.deleShare(getContext(), "password");
                                }
                                //跳转
//                                startActivity(new Intent(getContext(), MainActivity.class));

                                //结束
                                getActivity().finish();

                            } else {
                                Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_registered:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                mCurrentFragment = new RegisterFragment();
                transaction.replace(R.id.lay_container, mCurrentFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;

            case R.id.tv_forget:
                Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.login_qq:
                Toast.makeText(getActivity(), "菜鸟是个禽兽,社交账号打死不给进", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


}
