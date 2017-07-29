package com.cainiao.cncooperation.ui.fragment.main;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.factory.model.Index;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.model.circle.FriendCircle;
import com.cainiao.factory.model.circle.FriendCircleComment;
import com.cainiao.factory.net.HttpManager;
import com.cainiao.factory.net.compat.RxResponseCompat;
import com.cainiao.factory.net.rx.ProgressDialogSubscriber;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;

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


        BmobQuery<FriendCircle> query = new BmobQuery<>();
        query.order("-createdAt");
//		query.setCachePolicy(CachePolicy.NETWORK_ONLY);


        query.setLimit(10);  //限制最多10条数据结果作为一页
        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
        query.addWhereLessThan("createdAt", date);
//        LogUtils.i(TAG,"SIZE:"+Constant.NUMBERS_PER_PAGE*pageNum);
        query.setSkip(0);  //忽略前10条数据（即第一页数据结果）
//        LogUtils.i(TAG,"SIZE:"+Constant.NUMBERS_PER_PAGE*pageNum);
        query.include("author,post.author");  // query.include("author,post.author");

        query.findObjects(new FindListener<FriendCircle>() {
            @Override
            public void done(List<FriendCircle> list, BmobException e) {
                Log.d("HomeFragment", "list.size():" + list.size());

                for (int i = 0; i < list.size(); i++) {

                    BmobQuery<FriendCircleComment> query = new BmobQuery<>();
////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                    FriendCircle post = new FriendCircle();
                    post.setObjectId(list.get(i).getObjectId());
                    query.addWhereEqualTo("post", new BmobPointer(post));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
                    query.include("author,post.author");
                    query.findObjects(new FindListener<FriendCircleComment>() {

                        @Override
                        public void done(List<FriendCircleComment> objects, BmobException e) {
                            Log.d("HomeFragment", "objects.size():" + objects.size());
                            Log.d("HomeFragment", "objects:" + objects);
                        }
                    });

                }

            }
        });

        //取消喜欢
        FriendCircle post = new FriendCircle();
//        post.setObjectId("97d7b10574");
//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
//        BmobRelation relation = new BmobRelation();
//        // 取消喜欢也就是从relation中删除一个user对象
//        relation.remove(user);
//        // 重新设置指向
//        post.setLikes(relation);
//        post.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    Toast.makeText(getContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
////                    toast("ok ");
//                } else {
////                    Log.e(TAG, e.toS tring());
//                }
//            }
//        });
//        //查询搜藏该帖子的所有人
//        BmobQuery<MyUser> query = new BmobQuery<>();
//        FriendCircle posts = new FriendCircle();
//        posts.setObjectId("97d7b10574");
//        // TODO 不友好
//        query.addWhereRelatedTo("likes", new BmobPointer(posts));
//        query.findObjects(new FindListener<MyUser>() {
//            @Override
//            public void done(List<MyUser> list, BmobException e) {
//                if (e == null) {
//                    Log.d("HomeFragment", "喜欢的个数 ---- > list.size():" + list.size());
//                }
////                    toast("ok " + list.size());
//            }
//        });


//        comment("97d7b10574");

        //添加喜欢
//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
//        FriendCircle postss = new FriendCircle();
//        postss.setObjectId("97d7b10574");
////将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
//        BmobRelation relation = new BmobRelation();
////将当前用户添加到多对多关联中
//        relation.add(user);
////多对多关联指向`post`的`likes`字段
//        postss.setLikes(relation);
//        postss.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
//
////                    Log.i("bmob","多对多关联添加成功");
//                }else{
//                    Log.i("bmob","失败："+e.getMessage());
//                }
//            }
//
//        });


        //查询评论
//        BmobQuery<FriendCircleComment> query = new BmobQuery<>();
////用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//        FriendCircle post = new FriendCircle();
//        post.setObjectId("97d7b10574");
//        query.addWhereEqualTo("post",new BmobPointer(post));
////希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//        query.include("author,post.author");
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

    private void comment(String postId) {

        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        FriendCircle post = new FriendCircle();
        post.setObjectId(postId);
        final FriendCircleComment comment = new FriendCircleComment();
        comment.setContent("发表评论成功，啦啦啦啦");
        comment.setPost(post);
        comment.setAuthor(user);
        comment.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }

        });

    }
}
