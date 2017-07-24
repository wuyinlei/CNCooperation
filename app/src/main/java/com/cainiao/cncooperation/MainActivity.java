package com.cainiao.cncooperation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.cainiao.cncooperation.ui.fragment.CartFragment;
import com.cainiao.cncooperation.ui.fragment.HomeFragment;
import com.cainiao.cncooperation.ui.fragment.MineFragment;
import com.cainiao.cncooperation.ui.fragment.NewsFragment;
import com.cainiao.cncooperation.ui.fragment.VideoFragment;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.widget.BottomNavigationViewHelper;
import com.cainiao.common.widget.BottomViewPagerAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    public ViewPager mViewPager;

    public MenuItem mMenuItem;

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView mNavigationView;

    @Override
    protected void initView() {
        super.initView();
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

        navigationViewSetting(mNavigationView);

        viewPagerSetting(mViewPager);

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
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_dashboard:
                                mViewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_video:
                                mViewPager.setCurrentItem(2);
                                break;
                            case R.id.navigation_cart:
                                mViewPager.setCurrentItem(3);
                                break;
                            case R.id.navigation_mine:
                                mViewPager.setCurrentItem(4);
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


}
