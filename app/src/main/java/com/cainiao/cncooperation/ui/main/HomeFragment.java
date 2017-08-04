package com.cainiao.cncooperation.ui.main;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.factory.Account;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.rongyun.FakeServer;
import com.cainiao.factory.rongyun.HttpUtil;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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


        FakeServer.getToken("f168dd00b6", "若兰明月", "http://loveruolan.oss-cn-shanghai.aliyuncs.com/portrait/201707/6e8ad1e62302f9c446b78c00d51b9cff.jpg", new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                Log.d("HomeFragment", body);
                Log.d("HomeFragment", "code:" + code);
            }
        });


    }

}
