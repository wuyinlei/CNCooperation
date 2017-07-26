package com.cainiao.common.utils;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cainiao.common.R;
import com.cainiao.common.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;

public class ImageSelectActivity extends BaseActivity {

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
    //ArrayList<String>  已经选择好的图片
    private ArrayList<String> mResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_image_select;
    }
}
