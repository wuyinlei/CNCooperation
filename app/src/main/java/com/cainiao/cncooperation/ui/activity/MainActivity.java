package com.cainiao.cncooperation.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.PublishImageAdapter;
import com.cainiao.cncooperation.ui.main.CartFragment;
import com.cainiao.cncooperation.ui.main.HomeFragment;
import com.cainiao.cncooperation.ui.main.MineFragment;
import com.cainiao.cncooperation.ui.main.NewsFragment;
import com.cainiao.cncooperation.ui.main.VideoFragment;
import com.cainiao.cncooperation.ui.im.IMActivity;
import com.cainiao.cncooperation.utils.ShareUtils;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.utils.SharedUtils;
import com.cainiao.common.widget.BottomNavigationViewHelper;
import com.cainiao.common.widget.BottomViewPagerAdapter;
import com.cainiao.common.widget.logger.CNLogger;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.utils.rongyun.FakeServer;
import com.cainiao.factory.utils.rongyun.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    public ViewPager mViewPager;

    public MenuItem mMenuItem;

    @BindView(R.id.re_title_search)
    public RelativeLayout mReTitleSearch;

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView mNavigationView;

    @BindView(R.id.tv_title)
    public TextView mTvTitle;

    @BindView(R.id.iv_message)
    public ImageView mIvMessage;

    @BindView(R.id.iv_scan)
    public ImageView mIvScan;

    @BindView(R.id.appbar_layout)
    public AppBarLayout mBarLayout;

    @BindView(R.id.re_bg_layout)
    public RelativeLayout mReBgLayout;

    private long EXIT_FLAG = 0;

    private String token;

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    @Override
    protected void initView() {
        super.initView();
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

        navigationViewSetting(mNavigationView);

        viewPagerSetting(mViewPager);

//        if (!Account.isLogin()) {
//            BmobUser bu2 = new BmobUser();
//            bu2.setUsername("若兰明月");
//            bu2.setPassword("123456asd");
//            bu2.login(new SaveListener<BmobUser>() {
//
//                @Override
//                public void done(BmobUser bmobUser, BmobException e) {
//                    if (e == null) {
//                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                        //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
//                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
//                    } else {
////                        loge(e);
//                    }
//                }
//            });
//        }

        //判断本地是否有这个token
        token = SharedUtils.getString(this, Common.Constance.RONGYUNG_IM_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            FakeServer.getRongYunToken(this);
        }

        if (!TextUtils.isEmpty(token))
            getConnectIm(token);

    }

    private void getConnectIm(String token) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                CNLogger.d("HomeFragment", "Token 错误---onTokenIncorrect---" + '\n');

            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
//                Toast.makeText(getContext(), "连接融云成功---onSuccess---用户ID:" + userid + '\n', Toast.LENGTH_SHORT).show();
                Log.d("HomeFragment", "连接融云成功---onSuccess---用户ID:" + userid + '\n');

                //链接成功之后开启聊天 80cf8a6a7e       f168dd00b6   大白思密达


            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("HomeFragment", "连接融云失败, 错误码: " + errorCode + '\n');


            }
        });


    }


//
//    /**
//     * 获取到链接的tokan
//     */
//    private void getRongYunToken() {
//        //80cf8a6a7e     f168dd00b6
//        FakeServer.getToken(Account.getUser().getObjectId(), Account.getUserName(), "http://loveruolan.oss-cn-shanghai.aliyuncs.com/portrait/201707/6e8ad1e62302f9c446b78c00d51b9cff.jpg", new HttpUtil.OnResponse() {
//            @Override
//            public void onResponse(int code, String body) {
//                if (code == 200) {
//                    JSONObject jsonObj = null;
//                    try {
//                        jsonObj = new JSONObject(body);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    token = jsonObj.optString("token");
//                    SharedUtils.putString(MainActivity.this, Common.Constance.RONGYUNG_IM_TOKEN, token);
//
//                    Log.i("HomeFragment", "获取的 token 值为:\n" + token + '\n');
//                } else {
//                    Log.i("HomeFragment", "获取 token 失败" + '\n');
//                }
//            }
//        });
//
//    }

    private void viewPagerSetting(ViewPager viewPager) {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mNavigationView.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        setupViewPager(viewPager);

    }

    private void navigationViewSetting(BottomNavigationView navigationView) {
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                mViewPager.setCurrentItem(0);
                                mTvTitle.setVisibility(View.GONE);
                                mIvScan.setVisibility(View.VISIBLE);
                                mReTitleSearch.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_dashboard:
                                mViewPager.setCurrentItem(1);
                                mTvTitle.setVisibility(View.GONE);
                                mIvScan.setVisibility(View.GONE);
                                mReTitleSearch.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_video:
                                mViewPager.setCurrentItem(2);
                                mTvTitle.setVisibility(View.GONE);
                                mIvScan.setVisibility(View.GONE);
                                mReTitleSearch.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_cart:
                                mViewPager.setCurrentItem(3);
                                mTvTitle.setVisibility(View.VISIBLE);
                                mIvScan.setVisibility(View.GONE);
                                mReTitleSearch.setVisibility(View.GONE);
                                break;
                            case R.id.navigation_mine:
                                mViewPager.setCurrentItem(4);
                                mTvTitle.setVisibility(View.GONE);
                                mIvScan.setVisibility(View.GONE);
                                mReTitleSearch.setVisibility(View.GONE);
                                break;
                        }
                        return false;
                    }
                });

    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 设置和ViewPager进行联动
     *
     * @param viewPager viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        BottomViewPagerAdapter adapter = new BottomViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new NewsFragment());
        adapter.addFragment(new VideoFragment());
        adapter.addFragment(new CartFragment());
        adapter.addFragment(new MineFragment());
        viewPager.setAdapter(adapter);
    }


    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @OnClick(R.id.iv_message)
    public void message() {
        IMActivity.show(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {

            if ((System.currentTimeMillis() - EXIT_FLAG) > 2000) {
                EXIT_FLAG = System.currentTimeMillis();
                Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
            } else {

                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
