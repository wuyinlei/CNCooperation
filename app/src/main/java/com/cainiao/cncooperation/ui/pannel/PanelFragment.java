package com.cainiao.cncooperation.ui.pannel;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.BaseFragment;
import com.cainiao.common.utils.DisplayUtils;
import com.cainiao.common.widget.camera.GalleryView;

import java.io.File;
import java.util.ArrayList;

/**
 * @function 表情面板的无缝切换
 */
public class PanelFragment extends BaseFragment  {

    private View mFacePanel;
    private View mGalleryPanel;
    private View mRecordPanel;
    private PanelCallback mCallback;

    public PanelFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        initRecord(view);
        initFace(view);
        initGallery(view);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_panel;
    }


    private void initFace(View root) {
        View facePanel = mFacePanel = root.findViewById(R.id.lay_face_panel);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tab);
        ViewPager pager = (ViewPager) facePanel.findViewById(R.id.pager);
        facePanel.findViewById(R.id.im_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackspaceClick();
            }
        });
        tabLayout.setupWithViewPager(pager);

        // Min 48dp
        final int minFaceSize = (int) DisplayUtils.dipToPx(getResources(), 56);
        final int totalWidth = DisplayUtils.getScreenWidth(getActivity());
        final int spanCount = totalWidth / minFaceSize;


    }

    /**
     * 初始化录音布局
     *
     * @param root 根布局
     */
    private void initRecord(View root) {
        View recordPanel = mRecordPanel = root.findViewById(R.id.lay_record_panel);
    }


    /**
     * 初始化图片画廊
     *
     * @param root 根布局
     */
    private void initGallery(View root) {
        View galleryPanel = mGalleryPanel = root.findViewById(R.id.lay_gallery_panel);
        final GalleryView galleryView = (GalleryView) galleryPanel.findViewById(R.id.view_gallery);
        final TextView selectedSize = (TextView) galleryPanel.findViewById(R.id.txt_gallery_select_count);
        galleryView.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {
                selectedSize.setText(String.format(getText(R.string.gallery_selected_size).toString(),
                        count));
            }
        });
        galleryPanel.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendGalleryClick(galleryView, galleryView.getAllSelectImages());
                selectedSize.setText(String.format(getText(R.string.gallery_selected_size).toString(),
                        0));
            }
        });
    }



    /**
     * 删除已经选择的表情
     */
    private void onBackspaceClick() {
        PanelCallback callback = mCallback;
        if (callback == null)
            return;
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0,
                0, KeyEvent.KEYCODE_ENDCALL);
        callback.getInputEditText().dispatchKeyEvent(event);
    }

    /**
     * 发送
     *
     * @param galleryView
     * @param paths
     */
    private void onSendGalleryClick(GalleryView galleryView, ArrayList<String> paths) {
        galleryView.clear();
        PanelCallback callback = mCallback;
        if (callback == null)
            return;
        callback.onSendGalleryClick(paths);
    }


    public void setup(PanelCallback callback) {
        this.mCallback = callback;
    }

    public boolean isOpenFace() {
        return mFacePanel.getVisibility() == View.VISIBLE;
    }


    public boolean isOpenMore() {
        return mGalleryPanel.getVisibility() == View.VISIBLE;
    }

    /**
     * 显示表情界面
     */
    public void showFace() {
        mFacePanel.setVisibility(View.VISIBLE);
        mGalleryPanel.setVisibility(View.GONE);
        mRecordPanel.setVisibility(View.GONE);
    }

    /**
     * 显示录音界面
     */
    public void showRecord() {
        mFacePanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.GONE);
        mRecordPanel.setVisibility(View.VISIBLE);
    }

    /**
     * 显示图片界面
     */
    public void showGallery() {
        mFacePanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.VISIBLE);
        mRecordPanel.setVisibility(View.GONE);
        GalleryView view = (GalleryView) mGalleryPanel.findViewById(R.id.view_gallery);
        view.clear();
    }

    public void showMore() {
        showGallery();
    }

    public interface PanelCallback {
        EditText getInputEditText();

        void onSendGalleryClick(ArrayList<String> paths);

        void onRecordDone(File file, long time);
    }
}
