package com.cainiao.common.widget.camera;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cainiao.common.R;
import com.cainiao.common.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ImageSelectActivity extends BaseActivity implements
        View.OnClickListener, GalleryView.SelectedChangeListener {

    //选择图片的模式   --多选
    public static final int MODE_MULTI = 0X0001;
    //选择图片的模式   --单选
    public static final int MODE_SINGLE = 0X0002;

    //是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    //总共可以选择多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    //原始图片的路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    //选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    //返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    public static final int RESULT_CODE = 100;


    //查询的所有数据显示
    private RecyclerView mImageListResults;

    //加载所有的数据
    private static final int LOADER_TYPE = 0X0003;
    //图片显示的adapter

    //拍照临时存放的文件
    private File mTempFile;

    //传递过来的参数
    //单选或者多选 int类型的type
    private int mode = MODE_MULTI;

    //int 类型的选择图片张数
    private int mMaxCount = 9;

    //boolean  类型是否显示拍照按钮
    private boolean mShowCamera = true;


    private ArrayList<String> mResultList = new ArrayList<>();

    private Set<String> mImageDisplayName = new HashSet<>();


    private ContentResolver resolver;

    private GalleryView mGalleryView;

    private ImageView mIvClose;
    private TextView mTvClose, mTvTitle, mTvSelectAlbum, mTvSelectSuccess;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_image_select;
    }

    @Override
    protected void initView() {
        overridePendingTransition(R.anim.login_activity_in, R.anim.login_activity_out);
        super.initView();
        mIvClose = (ImageView) findViewById(R.id.iv_close);
        mTvClose = (TextView) findViewById(R.id.tv_close);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvSelectAlbum = (TextView) findViewById(R.id.tv_select_album);
        mTvSelectSuccess = (TextView) findViewById(R.id.tv_select_success);
        mGalleryView = (GalleryView) findViewById(R.id.gallery_view);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvClose.setOnClickListener(this);
        mTvClose.setOnClickListener(this);
        mTvSelectAlbum.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        getIntentData();

        mGalleryView.setup(getSupportLoaderManager(),
                this,
                mResultList);
    }

    private void getIntentData() {
        mShowCamera = getIntent().getBooleanExtra(ImageSelectActivity.EXTRA_SHOW_CAMERA, true);
        mMaxCount = getIntent().getIntExtra(ImageSelectActivity.EXTRA_SELECT_COUNT, 9);
        mResultList = getIntent().getStringArrayListExtra(ImageSelectActivity.EXTRA_DEFAULT_SELECTED_LIST);
        mode = getIntent().getIntExtra(ImageSelectActivity.EXTRA_SELECT_MODE,ImageSelectActivity.MODE_MULTI);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_close || i == R.id.tv_close) {
            finish();

        } else if (i == R.id.tv_select_album) {// TODO: 2017/7/26   进行相关的相册选择
            showAllFileCamera();
        }


    }

    private void showAllFileCamera() {
        final RecyclerView mAllPhotoPicRecycler;
        View view = LayoutInflater.from(this).inflate(R.layout.activity_album_pick, null);


    }


    @Override
    public void onSelectedCountChanged(int count) {
        if (count > 0) {
            mTvSelectSuccess.setClickable(true);
            mTvSelectSuccess.setText(String.format(getString(R.string.select_image_success), count, mMaxCount));
            mTvSelectSuccess.setTextColor(getResources().getColor(R.color.black));
            mTvSelectSuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(ImageSelectActivity.EXTRA_DEFAULT_SELECTED_LIST,mGalleryView.getAllSelectImages());
                    ImageSelectActivity.this.setResult(RESULT_OK,intent);
                    finish();
                    overridePendingTransition(R.anim.login_activity_in, R.anim.login_activity_out);
                }
            });
        } else {
            mTvSelectSuccess.setTextColor(getResources().getColor(R.color.wheat));
            mTvSelectSuccess.setText(String.format(getString(R.string.select_image_success), count, mMaxCount));
            mTvSelectSuccess.setClickable(false);
        }
    }

}
