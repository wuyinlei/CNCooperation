package com.cainiao.cncooperation.ui.im;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.TextWatcherAdapter;
import com.cainiao.cncooperation.ui.pannel.PanelFragment;
import com.cainiao.common.base.BaseActivity;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements PanelFragment.PanelCallback {


    private PanelFragment mPanelContent;
    private AirPanel.Boss mPanelBoss;


    @BindView(R.id.edit_content)
    EditText mEtContent;

    @BindView(R.id.btn_submit)
    ImageView mBtSumbmit;

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


    }

    public static void show(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
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


}
