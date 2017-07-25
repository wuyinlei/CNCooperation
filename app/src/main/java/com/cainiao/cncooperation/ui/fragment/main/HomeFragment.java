package com.cainiao.cncooperation.ui.fragment.main;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.factory.model.Index;
import com.cainiao.factory.net.HttpManager;
import com.cainiao.factory.net.compat.RxResponseCompat;
import com.cainiao.factory.net.rx.ProgressDialogSubscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @function 首页界面
 */
public class HomeFragment extends BaseFragment {


    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }


    @BindView(R.id.btnRequest)
    Button mButton;


    @Override
    protected void initView(View view) {
        super.initView(view);

    }


    @OnClick(R.id.btnRequest)
    public void btnRequest() {

        HttpManager.getInstance().getApps("consultant")
                .compose(RxResponseCompat.<List<Index>>compatResult())
                .subscribe(new ProgressDialogSubscriber<List<Index>>(getContext()) {
                    @Override
                    public void onNext( List<Index> indices) {
                        Log.d("HomeFragment", "indices.size():" + indices.size());
                    }
                });


    }
}
