package com.cainiao.cncooperation.ui.fragment.main;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.factory.model.Index;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.net.HttpManager;
import com.cainiao.factory.net.compat.RxResponseCompat;
import com.cainiao.factory.net.rx.ProgressDialogSubscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

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

        //查询评论
//        BmobQuery<FriendCircleComment> query = new BmobQuery<FriendCircleComment>();
////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//        FriendCircle post = new FriendCircle();
//        post.setObjectId("97d7b10574");
//        query.addWhereEqualTo("post",new BmobPointer(post));
////希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//        query.include("user,post.author");
//        query.findObjects(new FindListener<FriendCircleComment>() {
//
//            @Override
//            public void done(List<FriendCircleComment> objects,BmobException e) {
//                Log.d("HomeFragment", "objects.size():" + objects.size());
//                Log.d("HomeFragment", "objects:" + objects);
//            }
//        });
        //查询一个用户发表的所以帖子
//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
//        BmobQuery<FriendCircle> query = new BmobQuery<FriendCircle>();
//        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
//        query.order("-updatedAt");
//        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
//        query.findObjects(new FindListener<FriendCircle>() {
//
//            @Override
//            public void done(List<FriendCircle> object,BmobException e) {
//                if(e==null){
//                    Log.d("HomeFragment", "object.size():" + object.size());
//                    Log.d("HomeFragment", object.toString());
//                    Log.i("bmob","成功");
//                }else{
//                    Log.i("bmob","失败："+e.getMessage());
//                }
//            }
//
//        });


        //删除帖子
//        FriendCircle p = new FriendCircle();
//        p.remove("author");
//        p.update("ESIt3334", new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Log.i("bmob","成功");
//                }else{
//                    Log.i("bmob","失败："+e.getMessage());
//                }
//            }
//        });




    }
}
