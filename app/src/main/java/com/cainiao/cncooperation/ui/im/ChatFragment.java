package com.cainiao.cncooperation.ui.im;


import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.PresenterFragment;
import com.cainiao.common.widget.recycler.RecyclerAdapter;
import com.cainiao.factory.presenter.message.RecentConversationContract;
import com.cainiao.factory.presenter.message.RecentConversationPresenter;

import java.util.List;

import butterknife.BindView;
import io.rong.imlib.model.Conversation;


/**
 * @author wuyinlei
 * @function 最近会话列表页面
 */
public class ChatFragment extends PresenterFragment<RecentConversationContract.Presenter> implements RecentConversationContract.View {

    private ChatFragment.Adapter mAdapter;

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

        mAdapter = new Adapter();
    }

    @Override
    protected void initData() {
        super.initData();


    }



    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void loadData(List<Conversation> messages) {

    }

    @Override
    public void loadMoreData(List<Conversation> messages) {

    }

    @Override
    public RecyclerAdapter<Conversation> getRecyclerViewAadpter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    public void scrollRecyclerToPosition(int position) {

    }

    class Adapter extends RecyclerAdapter<Conversation>{

        @Override
        protected AdapterViewHolder<Conversation> onCreateViewHolder(View root, int viewType) {
            return new ConversationHolder(root);
        }

        @Override
        protected int getItemType(int position, Conversation conversation) {
            return R.layout.cell_conversation_list_item;
        }


    }

    class ConversationHolder extends RecyclerAdapter.AdapterViewHolder<Conversation>{

        public ConversationHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Conversation data) {

        }
    }

}
