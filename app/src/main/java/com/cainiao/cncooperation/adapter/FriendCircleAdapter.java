package com.cainiao.cncooperation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cainiao.cncooperation.R;
import com.cainiao.common.widget.ExpandTextView;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.common.widget.nineimage.NineGridView;
import com.cainiao.factory.model.Index;
import com.cainiao.factory.model.circle.CircleViewBean;

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
    protected void convert(BaseViewHolder viewHoder, CircleViewBean item,int position) {
//        viewHoder.getImageView(R.id.iv_avatar)
        viewHoder.getTextView(R.id.tv_name).setText(item.getUsername());
        ExpandTextView mExpandView = (ExpandTextView) viewHoder.getView(R.id.tv_expand_content);
        NineGridView mCircleImageView = (NineGridView) viewHoder.getView(R.id.nine_grid_view);
//        viewHoder.getTextView(R.id.mind_circle_view)
        viewHoder.getTextView(R.id.mind_circle_like).setText(item.getLikescount());
        viewHoder.getTextView(R.id.mind_circle_comments).setText(item.getCommentcount());
        LinearLayout mCircleComment = (LinearLayout) viewHoder.getView(R.id.circle_comment);
        RecyclerView mCircleRecyclerComment = (RecyclerView) viewHoder.getView(R.id.circle_comment_list);

        Glide.with(context)
                .load(item.getAvator())
                .asBitmap()
                .into((ImageView) viewHoder.getView(R.id.iv_avatar));



    }
}
