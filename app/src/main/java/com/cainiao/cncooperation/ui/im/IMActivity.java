package com.cainiao.cncooperation.ui.im;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.MainAdapter;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.widget.AddPopupWindow;
import com.cainiao.common.widget.alpha.AlphaIndicator;


public class IMActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_im;
    }

    public static void show(Context context) {
        Intent intent = new Intent(context,IMActivity.class);
        context.startActivity(intent);
    }


    private ImageView mIvAdd, mIvSearch;


    @Override
    protected void initView() {
        super.initView();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);

        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
    }

    @Override
    protected void initListener() {
        mIvAdd.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }


    /**
     * 显示Pop
     *
     * @param view 需要显示在的哪个View
     */
    private void showPop(View view) {
        AddPopupWindow popupWindow = new AddPopupWindow(this);
        popupWindow.showAsDropDown(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                showPop(v);
                break;
            case R.id.iv_search:

                break;
        }
    }
}
