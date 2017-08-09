package com.cainiao.cncooperation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cainiao.cncooperation.ui.im.RecentSessionFragment;
import com.cainiao.cncooperation.ui.im.ContactFragment;
import com.cainiao.cncooperation.ui.im.DiscoverFragment;
import com.cainiao.cncooperation.ui.im.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public MainAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new RecentSessionFragment());
        fragments.add(new ContactFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new ProfileFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
