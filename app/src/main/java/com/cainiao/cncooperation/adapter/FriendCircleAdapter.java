package com.cainiao.cncooperation.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.ui.account.AccountActivity;
import com.cainiao.cncooperation.ui.account.PersonalActivity;
import com.cainiao.cncooperation.ui.dynamic.FriendCircleDetailActivity;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.expandtextview.ExpandTextView;
import com.cainiao.common.widget.nineimage.ImageInfo;
import com.cainiao.common.widget.nineimage.NineGridClickViewAdapter;
import com.cainiao.common.widget.nineimage.NineGridView;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.model.circle.CircleViewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyinlei on 2017/7/27.
 *
 * @function 朋友圈的动态适配器
 */

public class FriendCircleAdapter extends SimpleAdapter<CircleViewBean> {

    public FriendCircleAdapter(Context context) {
        super(context, R.layout.mind_circle_individual_list_item);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final CircleViewBean item, int position) {
//        viewHoder.getImageView(R.id.iv_avatar)
        viewHoder.getTextView(R.id.tv_name).setText(item.getUsername());
        ExpandTextView mExpandView = (ExpandTextView) viewHoder.getView(R.id.tv_expand_content);
        mExpandView.setText(item.getContent());
        NineGridView mCircleImageView = (NineGridView) viewHoder.getView(R.id.nine_grid_view);

        if (item.getImages() != null && item.getImages().size() > 0) {
            List<ImageInfo> infoimages = new ArrayList<>();
            for (int i = 0; i < item.getImages().size(); i++) {
                ImageInfo image = new ImageInfo();
                image.setThumbnailUrl(item.getImages().get(i));
                infoimages.add(image);
            }
            mCircleImageView.setVisibility(View.VISIBLE);
            mCircleImageView.setAdapter(new NineGridClickViewAdapter(context, infoimages));
        } else {
            mCircleImageView.setVisibility(View.GONE);
        }

        String createTime = item.getCreateDate();
        createTime = createTime.substring(5, 16);

        viewHoder.getTextView(R.id.tv_circle_time).setText(createTime);
        viewHoder.getTextView(R.id.mind_circle_like).setText(item.getLikescount());
        viewHoder.getTextView(R.id.mind_circle_comments).setText(item.getCommentcount());
        LinearLayout mCircleComment = (LinearLayout) viewHoder.getView(R.id.circle_comment);
        RecyclerView mCircleRecyclerComment = (RecyclerView) viewHoder.getView(R.id.circle_comment_list);

        CircleImageView circleImageView = (CircleImageView) viewHoder.getView(R.id.iv_avatar);

        viewHoder.getTextView(R.id.mind_circle_view).setText(String.format(context.getString(R.string.look_view_count), item.getViewcount()));

        Glide.with(context)
                .load(item.getAvator())
                .asBitmap()
                .into(circleImageView);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Account.isLogin()) {
                    PersonalActivity.show(context, item.getUsername());
                } else {
                    AccountActivity.show(context);
                }
            }
        });

        if (item.getComment() != null && item.getComment().size() > 0) {
            RecyclerView commentView = (RecyclerView) viewHoder.getView(R.id.circle_comment_list);
            commentView.setLayoutManager(new LinearLayoutManager(context));
            FriendCircleCommentAdapter adapter = new FriendCircleCommentAdapter(context);
            commentView.setAdapter(adapter);
            adapter.addData(item.getComment());
        }


        viewHoder.getView(R.id.ll_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendCircleDetailActivity.show(context, item.getObjectId());
            }
        });

    }
}

