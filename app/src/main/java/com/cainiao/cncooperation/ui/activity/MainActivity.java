package com.cainiao.cncooperation.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.main.CartFragment;
import com.cainiao.cncooperation.ui.main.HomeFragment;
import com.cainiao.cncooperation.ui.main.MineFragment;
import com.cainiao.cncooperation.ui.main.NewsFragment;
import com.cainiao.cncooperation.ui.main.VideoFragment;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.widget.BottomNavigationViewHelper;
import com.cainiao.common.widget.BottomViewPagerAdapter;

import butterknife.BindView;

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

    }

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
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
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
