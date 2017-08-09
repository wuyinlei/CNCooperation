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
import android.util.Log;
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
import com.cainiao.common.base.PresenterActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.rxbus.RxBus;
import com.cainiao.common.rxbus.RxBusSubscriber;
import com.cainiao.common.rxbus.helper.RxSubscriptions;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.recycler.RecyclerAdapter;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.event.MessageEvent;
import com.cainiao.factory.model.im.UserInfo;
import com.cainiao.factory.presenter.message.ChatMessageContract;
import com.cainiao.factory.presenter.message.ChatMessagePresenter;
import com.cainiao.factory.utils.BmobUtils;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import rx.Subscription;


public class ChatActivity extends PresenterActivity<ChatMessageContract.Presenter> implements
        PanelFragment.PanelCallback, ChatMessageContract.ChatView {


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
    private Conversation.ConversationType CHAT_TYPE = Conversation.ConversationType.PRIVATE;

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.chat_view_layout;
    }

    @Override
    protected void initView() {
        super.initView();


        initPanel();


        subscribeEvent();

        //设置会话已读
        RongIMClient.getInstance().clearMessagesUnreadStatus(CHAT_TYPE, receiverId);


        initToolbar();


        initTitleType();


    }

    private void initToolbar() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (CHAT_TYPE == Conversation.ConversationType.PRIVATE) {
            BmobUtils.queryUserInfo(receiverId, new BmobUtils.OnListener<UserInfo>() {
                @Override
                public void onError(int errorCode, String message) {

                }

                @Override
                public void onSuccess(UserInfo data) {
                    mToolbar.setTitle(data.getName());
                }
            });
        }
//        mToolbar.setTitle("单聊");
        mToolbar.setTitleTextColor(R.color.white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 监听和改变toolbar的显示状态
     */
    private void initTitleType() {
        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType conversationType, String targetId, Collection<TypingStatus> typingStatusSet) {
                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
                if (conversationType == Conversation.ConversationType.PRIVATE && targetId.equals(receiverId)) {
                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示
                    int count = typingStatusSet.size();
                    if (count > 0) {
                        Iterator<TypingStatus> iterator = typingStatusSet.iterator();
                        TypingStatus status = iterator.next();
                        String objectName = status.getTypingContentType();

                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                        //匹配对方正在输入的是文本消息还是语音消息
                        if (objectName.equals(textTag.value())) {
                            //显示“对方正在输入”
//                        setToolbarSubTitle(UIUtils.getString(R.string.SET_TEXT_TYPING_TITLE));
                            mToolbar.setTitle(getString(R.string.SET_TEXT_TYPING_TITLE));
                        } else if (objectName.equals(voiceTag.value())) {
                            //显示“对方正在讲话”
//                        setToolbarSubTitle(UIUtils.getString(R.string.SET_VOICE_TYPING_TITLE));
                            mToolbar.setTitle(getString(R.string.SET_VOICE_TYPING_TITLE));
                        }
                    } else {
                        //当前会话没有用户正在输入，标题栏仍显示原来标题
//                    setToolbarSubTitle("");
                        mToolbar.setTitle("单聊");
//                        setTitle("");
                    }
                }
            }
        });
    }

    private void initPanel() {

        //没用啊   我擦
        mPanelBoss = (AirPanel.Boss) findViewById(R.id.lay_container);
//        mPanelBoss.setPanelListener(new AirPanel.Listener() {
//            @Override
//            public void requestHideSoftKeyboard() {
//                Util.hideKeyboard(mEtContent);
//            }
//        });




        PanelFragment fragment = (PanelFragment) getSupportFragmentManager().findFragmentById(R.id.frag_panel);
        fragment.setup(this);
        mPanelContent = fragment;
    }

    @Override
    protected void initData() {
        super.initData();


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new Adapter();

        mRecyclerView.setAdapter(mAdapter);

        mPresenter.request();

    }

    @Override
    protected void initListener() {
        super.initListener();

        mEtContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString().trim();
                boolean sendMsg = !TextUtils.isEmpty(content);
                mBtSumbmit.setActivated(sendMsg);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                super.onTextChanged(s, start, before, count);
                RongIMClient.getInstance().sendTypingStatus(Conversation.ConversationType.PRIVATE, receiverId, TextMessage.class.getAnnotation(MessageTag.class).value());
            }
        });

    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        receiverId = bundle.getString(Common.Constance.RECEIVER_ID);
        CHAT_TYPE = (Conversation.ConversationType) bundle.get(Common.Constance.CHAT_TYPE);
        return true;
    }

    public static void show(Context context, String receiverId, Conversation.ConversationType type) {
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
        mPresenter.pushImages(paths);
    }

    @Override
    public void onRecordDone(File file, long time) {

    }

    @Override
    public void onBackPressed() {
        if (mPanelBoss.isOpen()) {
            mPanelBoss.closePanel();
            return;
        }
        super.onBackPressed();
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
    protected ChatMessageContract.Presenter initPresenter() {
        return new ChatMessagePresenter(this, receiverId, CHAT_TYPE);
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

//    @Override
//    public void onAdapterDataChanged() {
//
//    }

    @Override
    public void scrollRecyclerToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

//    @Override
//    public void onDataLoaded(final Message data) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter.add(data);
//                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
//            }
//        });
//
//    }


    class Adapter extends RecyclerAdapter<Message> {


        @Override
        protected AdapterViewHolder<Message> onCreateViewHolder(View root, int viewType) {


            switch (viewType) {
                case R.layout.cell_chat_txt_left:
                case R.layout.cell_chat_txt_right:
                    //左右都是同一个
                    return new TextViewHolder(root);

                case R.layout.cell_chat_pic_right:
                case R.layout.cell_chat_pic_left:

                    return new ImageViewHolder(root);


                default:  //默认的就是
                    return new TextViewHolder(root);
            }
        }

        @Override
        protected int getItemType(int position, Message message) {


            //判断是否应该显示的是右侧布局还是左侧布局
            boolean isRight = Objects.equals(message.getSenderUserId(),
                    Account.getUser().getObjectId());

            if (message.getContent() instanceof TextMessage) {
                //文字内容
                return isRight ? R.layout.cell_chat_txt_right :
                        R.layout.cell_chat_txt_left;
            } else if (message.getContent() instanceof ImageMessage) {
                //图片内容
                return isRight ? R.layout.cell_chat_pic_right :
                        R.layout.cell_chat_pic_left;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.hideKeyboard(mEtContent);
                }
            });
        }

        @Override
        public void bindData(Message data) {

            Glide.with(ChatActivity.this)
                    .load(data.getContent().getUserInfo().getPortraitUri())
                    .asBitmap()
                    .into(mPortraitView);

//            ImageLoader.load(data.getContent().getUserInfo().getPortraitUri(),mPortraitView);
//            mLoading.setVisibility(View.GONE);


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

            mContent.setText(((TextMessage) data.getContent()).getContent());
        }
    }

    class ImageViewHolder extends BaseHolder {

        ImageView mIvPic;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mIvPic = (ImageView) itemView.findViewById(R.id.iv_pic);
        }

        @Override
        public void bindData(Message data) {
            super.bindData(data);
            MessageContent content = data.getContent();
            if (content instanceof ImageMessage){
              ImageMessage message = ((ImageMessage) content);

//                Glide.with(ChatActivity.this)
//                        .load(thumUri)
//                        .fitCenter()
//                        .into(mIvPic);

                Glide.with(ChatActivity.this).load(message.getLocalUri() == null ? message.getRemoteUri() : message.getLocalUri()).fitCenter().into(mIvPic);

            }


            //当是图片类型的时候  content中就是具体的地址
//
//            Glide.with(RecentSessionFragment.this)
//                    .load(content)
//                    .fitCenter()
//                    .into(mIvPic);
        }
    }


    private Subscription mRxSub;

    /**
     * 接受到事件并做相关处理
     */
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(MessageEvent.class)
                .subscribe(new RxBusSubscriber<MessageEvent>() {
                    @Override
                    public void onEvent(final MessageEvent userEvent) {
                        if (userEvent != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.add(userEvent.mMessage);
                                    Log.d("ChatActivity", "mAdapter.getItemCount():" + mAdapter.getItemCount());
                                    Log.d("ChatActivity", "mRecyclerView:" + mRecyclerView);
                                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxSub.unsubscribe();
    }


}
