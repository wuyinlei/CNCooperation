package com.cainiao.cncooperation.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.cainiao.cncooperation.R;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.factory.model.circle.DetailComment;

/**
 * Created by wuyinlei on 2017/7/30.
 *
 * @function 动态详情评论adapter
 */

public class FriendCricleDetailCommentAdapter extends SimpleAdapter<DetailComment> {

    public FriendCricleDetailCommentAdapter(Context context) {
        super(context, R.layout.mind_circle_friend_detail_comments_layout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, DetailComment item, int position) {
        viewHoder.getTextView(R.id.tv_name).setText(item.getUsername());
        viewHoder.getTextView(R.id.tv_content).setText(item.getContent());
        ImageLoader.load(item.getAvatar(), (ImageView) viewHoder.getView(R.id.img_avatar));
        String createTime = item.getCreateDate();
        createTime = createTime.substring(5, 16);
        viewHoder.getTextView(R.id.tv_time).setText(createTime);
    }
}
