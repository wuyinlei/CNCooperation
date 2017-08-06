package com.cainiao.cncooperation.ui.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.TextWatcherAdapter;
import com.cainiao.cncooperation.ui.pannel.PanelFragment;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.common.widget.recycler.RecyclerAdapter;
import com.cainiao.factory.Account;
import com.cainiao.factory.helper.UserHelper;
import com.cainiao.factory.model.im.UserInfo;
import com.cainiao.factory.presenter.message.ChatMessageContract;
import com.cainiao.factory.presenter.message.ChatMessagePresenter;
import com.cainiao.factory.utils.BmobUtils;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;



public class ChatActivity extends BaseActivity implements
        PanelFragment.PanelCallback ,ChatMessageContract.ChatView{


    private PanelFragment mPanelContent;
    private AirPanel.Boss mPanelBoss;

    @BindView(R.id.edit_content)
    EditText mEtContent;

    @BindView(R.id.btn_submit)
    ImageView mBtSumbmit;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private Adapter mAdapter;

    private List<Message> mMessages = new ArrayList<>();
    private String receiverId;  //接收者id
    private int CHAT_TYPE = Common.Constance.SINGLE_TYPE;

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.chat_view_layout;
    }

    private ChatMessagePresenter mPresenter;

    @Override
    protected void initView() {
        super.initView();

        mPresenter = new ChatMessagePresenter(this,receiverId, Conversation.ConversationType.PRIVATE);

        mPanelBoss = (AirPanel.Boss) findViewById(R.id.lay_container);
        mPanelBoss.setPanelListener(new AirPanel.Listener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(mEtContent);
            }
        });


        mEtContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString().trim();
                boolean sendMsg = !TextUtils.isEmpty(content);
                mBtSumbmit.setActivated(sendMsg);
            }
        });

        PanelFragment fragment = (PanelFragment) getSupportFragmentManager().findFragmentById(R.id.frag_panel);
        fragment.setup(this);
        mPanelContent = fragment;

        mToolbar.setTitle("单聊");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new Adapter();

        mRecyclerView.setAdapter(mAdapter);




    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        receiverId = bundle.getString(Common.Constance.RECEIVER_ID);
        CHAT_TYPE = bundle.getInt(Common.Constance.CHAT_TYPE);
        return true;
    }

    public static void show(Context context, String receiverId, int type) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Common.Constance.RECEIVER_ID, receiverId);
        intent.putExtra(Common.Constance.CHAT_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public EditText getInputEditText() {
        return mEtContent;
    }

    @Override
    public void onSendGalleryClick(ArrayList<String> paths) {

    }

    @Override
    public void onRecordDone(File file, long time) {

    }

    @Override
    public void onBackPressed() {
        if (mPanelBoss.isOpen()) {
            mPanelBoss.closePanel();
        }
    }

    @OnClick(R.id.btn_record)
    void onRecordClick() {
        if (mPanelBoss.isOpen()) {
            Util.showKeyboard(mEtContent);
        } else {
            mPanelContent.showRecord();
            mPanelBoss.openPanel();
        }
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mBtSumbmit.isActivated()) {
            //发送
            String content = mEtContent.getText().toString();
            mPresenter.pushText(content);
            mEtContent.setText("");

        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        if (mPanelBoss.isOpen() && mPanelContent.isOpenMore()) {
            Util.showKeyboard(mEtContent);
        } else {
            mPanelContent.showMore();
            mPanelBoss.openPanel();
        }

    }

    @OnClick(R.id.btn_face)
    void onFaceClick() {
        if (mPanelBoss.isOpen() && mPanelContent.isOpenFace()) {
            Util.showKeyboard(mEtContent);
        } else {
            mPanelContent.showFace();
            mPanelBoss.openPanel();
        }
    }

    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    public void loadData(List<Message> messages) {
        mMessages.addAll(messages);
    }

    @Override
    public void publishSuccess(Message message) {
//        mMessages.add(message);

    }

    @Override
    public RecyclerAdapter<Message> getRecyclerViewAadpter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    public void scrollRecyclerToPosition(int position) {

    }


    class Adapter extends RecyclerAdapter<Message> {


        @Override
        protected AdapterViewHolder<Message> onCreateViewHolder(View root, int viewType) {


            switch (viewType) {
                case R.layout.cell_chat_txt_left:
                case R.layout.cell_chat_txt_right:
                    //左右都是同一个
                    return new TextViewHolder(root);


                default:  //默认的就是
                    return new TextViewHolder(root);
            }
        }

        @Override
        protected int getItemType(int position, Message message) {


            boolean isRight = Objects.equals(message.getTargetId(),
                    Account.getUser().getObjectId());

            if (message.getContent() instanceof TextMessage) {
                //文字内容
                return isRight ? R.layout.cell_chat_txt_right :
                        R.layout.cell_chat_txt_left;
            } else {

                //其他内容
                return isRight ? R.layout.cell_chat_txt_right :
                        R.layout.cell_chat_txt_left;
            }

        }

    }

    class BaseHolder extends RecyclerAdapter.AdapterViewHolder<Message> {


        CircleImageView mPortraitView;

        //允许为空  左边没有 右边有
        ProgressBar mLoading;

        public BaseHolder(View itemView) {
            super(itemView);
            mPortraitView = (CircleImageView) itemView.findViewById(R.id.iv_portrait);
            mLoading = (ProgressBar) itemView.findViewById(R.id.loading);
        }

        @Override
        public void bindData(Message data) {

            ImageLoader.load(data.getExtra(),mPortraitView);


//            UserHelper.searchUser(data.getSenderUserId(), new BmobUtils.OnListener<UserInfo>() {
//                @Override
//                public void onError(int errorCode, String message) {
//
//                }
//
//                @Override
//                public void onSuccess(UserInfo data) {
//                    ImageLoader.load(data.getPortrail(),mPortraitView);
//                }
//            });
        }
    }

    class TextViewHolder extends BaseHolder {

        TextView mContent;

        public TextViewHolder(View itemView) {
            super(itemView);

            mContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @Override
        public void bindData(Message data) {
            super.bindData(data);

            mContent.setText(((TextMessage)data.getContent()).getContent());
        }
    }


}
