package com.cainiao.cncooperation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.cainiao.cncooperation.R;
import com.cainiao.common.widget.ExpandTextView;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.common.widget.nineimage.NineGridView;
import com.cainiao.factory.model.Index;

/**
 * Created by wuyinlei on 2017/7/27.
 *
 * @function 朋友圈的动态适配器
 */

public class FriendCircleAdapter extends SimpleAdapter<Index> {

    public FriendCircleAdapter(Context context, int layoutResId) {
        super(context, R.layout.mind_circle_individual_list_item);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Index item,int position) {
//        viewHoder.getImageView(R.id.iv_avatar)
//        viewHoder.getTextView(R.id.tv_name)
        ExpandTextView mExpandView = (ExpandTextView) viewHoder.getView(R.id.tv_expand_content);
        NineGridView mCircleImageView = (NineGridView) viewHoder.getView(R.id.nine_grid_view);
//        viewHoder.getTextView(R.id.mind_circle_view)
//        viewHoder.getTextView(R.id.mind_circle_like)
//        viewHoder.getTextView(R.id.mind_circle_comments)
        LinearLayout mCircleComment = (LinearLayout) viewHoder.getView(R.id.circle_comment);
        RecyclerView mCircleRecyclerComment = (RecyclerView) viewHoder.getView(R.id.circle_comment_list);
    }
}
