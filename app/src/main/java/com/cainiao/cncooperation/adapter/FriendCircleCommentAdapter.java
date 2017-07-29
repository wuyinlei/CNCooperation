package com.cainiao.cncooperation.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.cainiao.cncooperation.R;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.factory.model.circle.CircleViewBean;

/**
 * Created by wuyinlei on 2017/7/29.
 *
 * @function 朋友圈的评论
 */

public class FriendCircleCommentAdapter extends SimpleAdapter<CircleViewBean.CircleComment> {

    public FriendCircleCommentAdapter(Context context) {
        super(context, R.layout.mind_circle_item_comment);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, CircleViewBean.CircleComment item, int position) {
        viewHoder.getTextView(R.id.circle_comment_txt).setText(discolorationTips(context, item.getUser().getUsername(), item.getComment()));
    }

    /**
     * 进行变色
     *
     * @param username 人名
     * @param tip      拼接的内容
     */
    private SpannableString discolorationTips(Context context, String username, String tip) {
        String index = username + ":";
        String result = index + tip;
        SpannableString span = new SpannableString(result);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.paleturquoise)), 0, index.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}
