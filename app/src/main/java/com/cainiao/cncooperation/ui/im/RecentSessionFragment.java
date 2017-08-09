package com.cainiao.cncooperation.ui.im;


import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cainiao.cncooperation.R;
import com.cainiao.common.base.PresenterFragment;
import com.cainiao.common.utils.TimeUtils;
import com.cainiao.common.widget.recycler.RecyclerAdapter;
import com.cainiao.factory.presenter.message.RecentConversationContract;
import com.cainiao.factory.presenter.message.RecentConversationPresenter;

import java.util.List;

import butterknife.BindView;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;


/**
 * @author wuyinlei
 * @function 最近会话列表页面
 */
public class RecentSessionFragment extends PresenterFragment<RecentConversationContract.Presenter> implements RecentConversationContract.View {

    private RecentSessionFragment.Adapter mAdapter;

    @BindView(R.id.recent_recycler_view)
    RecyclerView mRecentView;

    @Override
    protected RecentConversationContract.Presenter initPresenter() {
        return new RecentConversationPresenter(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRecentView = (RecyclerView) view.findViewById(R.id.recent_recycler_view);
        mRecentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecentView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.requestData();

    }


    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void loadData(List<Conversation> messages) {
        mAdapter.replace(messages);
    }

    @Override
    public void loadMoreData(List<Conversation> messages) {

    }

    @Override
    public RecyclerAdapter<Conversation> getRecyclerViewAadpter() {
        return mAdapter;
    }

//    @Override
//    public void onAdapterDataChanged() {
//
//    }

    @Override
    public void scrollRecyclerToPosition(int position) {

    }

    class Adapter extends RecyclerAdapter<Conversation> {

        @Override
        protected AdapterViewHolder<Conversation> onCreateViewHolder(View root, int viewType) {
            return new ConversationHolder(root);
        }

        @Override
        protected int getItemType(int position, Conversation conversation) {
            return R.layout.cell_conversation_list_item;
        }


    }

    class ConversationHolder extends RecyclerAdapter.AdapterViewHolder<Conversation> {

        @BindView(R.id.iv_portrait)
        ImageView mIvPortrait;

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_desc)
        TextView mTvDesc;

        @BindView(R.id.tv_time)
        TextView mTvTime;

        @BindView(R.id.iv_message)
        ImageView mIvMessage;

        @BindView(R.id.tv_unread_count)
        TextView mTvUnreadCount;

        public ConversationHolder(View itemView) {
            super(itemView);



        }

        @Override
        public void bindData(final Conversation item) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatActivity.show(getContext(),item.getTargetId(),item.getConversationType());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(), "长按点击事件", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            if (item.getConversationType() == Conversation.ConversationType.PRIVATE) {
                UserInfo userInfo = item.getLatestMessage().getUserInfo();
                if (userInfo != null) {

                    mTvName.setText(userInfo.getName());

                    Glide.with(getActivity()).load(userInfo.getPortraitUri()).centerCrop().into(mIvPortrait);

                    mTvTime.setText(TimeUtils.getMsgFormatTime(item.getReceivedTime()));

                }
            } else {
                //群消息

            }

            if (item.getLatestMessage() instanceof TextMessage) {
                mTvDesc.setText(((TextMessage) item.getLatestMessage()).getContent());
            } else if (item.getLatestMessage() instanceof ImageMessage) {
                mTvDesc.setText("[" + "图片" + "]");
            } else if (item.getLatestMessage() instanceof VoiceMessage) {
                mTvDesc.setText("[" + "语言" + "]");
            }

            if (item.getUnreadMessageCount() > 0){
                mTvUnreadCount.setVisibility(View.VISIBLE);
                mTvUnreadCount.setText(item.getUnreadMessageCount() + "");
            } else {
                mTvUnreadCount.setVisibility(View.GONE);
            }

        }
    }

}
