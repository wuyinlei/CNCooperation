package com.cainiao.common.widget.camera;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cainiao.common.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wuyinlei on 2017/7/27.
 *
 * @function 封装的图片选择框架
 */

public class GalleryView extends RecyclerView {


    public static final int LOADER_ID = 0x0000;

    public static final long MIN_IMAGE_LEN = 10 * 1024;  //最大的照片的大小  10MB
    private int mMaxCount = 9;

    private boolean isShowCamera;


    //传递过来的参数
    //单选或者多选 int类型的type
    private int mode = ImageSelectActivity.MODE_MULTI;

    //加载的CallBack
    private LoaderCallback callback = new LoaderCallback();
    private ImageAdapter mImageAdapter = new ImageAdapter();

    //ArrayList<String>  已经选择好的图片
    private ArrayList<Image> mSelectImages = new ArrayList<>();

    //选择的监听
    private SelectedChangeListener mListener;

    private ArrayList<String> mHasChooseImages = new ArrayList<>();


    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //四列图片
        setLayoutManager(new GridLayoutManager(getContext(), 3));
        setAdapter(mImageAdapter);
    }

    /**
     * 初始化方法
     *
     * @param manager    LoaderManager Loader管理器
     * @param listener   选择改变监听
     * @param showCamera 是否显示拍照按钮  默认显示
     * @param mode       多选或者单选
     * @return 任何一个LOADER_ID  可以用于销毁Loader
     */
    public int setup(LoaderManager manager, SelectedChangeListener listener, int maxCount, ArrayList<String> images, boolean showCamera, int mode) {
        mListener = listener;
        // 一个标识加载器的唯一ID    一个可选的参数以支持加载器的构建   一个LoaderManager.LoaderCallbacks的实现
        manager.initLoader(LOADER_ID, null, callback);
        this.mMaxCount = maxCount;
        this.mHasChooseImages = images;
        this.isShowCamera = showCamera;
        this.mode = mode;
        return LOADER_ID;
    }

    /**
     * 初始化方法  用于头像选择
     *
     * @param manager  LoaderManager Loader管理器
     * @param listener 选择改变监听
     * @return 任何一个LOADER_ID  可以用于销毁Loader
     */
    public int setup(LoaderManager manager, SelectedChangeListener listener) {
        mListener = listener;
        // 一个标识加载器的唯一ID    一个可选的参数以支持加载器的构建   一个LoaderManager.LoaderCallbacks的实现
        manager.initLoader(LOADER_ID, null, callback);
        this.mMaxCount = 1;
        this.isShowCamera = true;
        this.mode = ImageSelectActivity.MODE_SINGLE;
        return LOADER_ID;
    }

    /**
     * 初始化方法  多选 默认选择九张图片
     *
     * @param manager  LoaderManager Loader管理器
     * @param listener 选择改变监听
     * @return 任何一个LOADER_ID  可以用于销毁Loader
     */
    public int setup(LoaderManager manager, SelectedChangeListener listener, ArrayList<String> images) {
        mListener = listener;
        // 一个标识加载器的唯一ID    一个可选的参数以支持加载器的构建   一个LoaderManager.LoaderCallbacks的实现
        manager.initLoader(LOADER_ID, null, callback);
        this.mMaxCount = 9;
        this.mHasChooseImages = images;
        this.isShowCamera = true;
        this.mode = ImageSelectActivity.MODE_MULTI;
        return LOADER_ID;
    }


    /**
     * 通知选择改变的时候刷新
     */
    private void notifySelectChanged() {
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedCountChanged(mSelectImages.size());
        }
    }

    /**
     * 获取到选择过的图片的路径
     *
     * @return 图片路径集合
     */
    public ArrayList<String> getAllSelectImages() {
        ArrayList<String> images = new ArrayList<>();

        for (Image image : mSelectImages) {
            images.add(image.path);
        }
        return images;
    }

    /**
     * 用于实际数据加载的Loader
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {


        //读取图片文件的参数
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ID) {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        null, null, IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
            //当Loader加载完成的时候回调方法
            final ArrayList<Image> images = new ArrayList<>();
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {

                        //getColumnIndexOrThrow(String columnName)
                        //从零开始返回指定列名称，如果不存在将抛出IllegalArgumentException 异常
                        int id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        //获取到图片本地地址
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        //获取到照片的时间
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                        String fileName = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));

                        File file = new File(path);
                        if (!file.exists() || file.length() < MIN_IMAGE_LEN)
                            continue;
                        //构建javabean
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = dateTime;
                        image.imageDisplay = fileName;

                        //添加到集合中
                        images.add(image);
                    } while (data.moveToNext());
                }
            }
            //加载完本地找之后进行更新资源
            updateSource(images);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            //当Loader销毁或者重置
            updateSource(null);
        }
    }


    private void updateSource(ArrayList<Image> images) {
        mImageAdapter.replace(images);
    }

    private static final int ITEM_CAMERA = 0X01;
    private static final int ITEM_NORMAL = 0X02;


    class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Image> mImgLists = new ArrayList<>();

        public void replace(ArrayList<Image> images) {
            if (images != null) {
                mImgLists.addAll(images);
                notifyDataSetChanged();
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == ITEM_CAMERA) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gallery_camera_item, parent, false);
                return new CameraHolder(view);
            } else if (viewType == ITEM_NORMAL) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.image_cell_gallery, parent, false);
                return new ItemHolder(view);
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return ITEM_CAMERA;
            return ITEM_NORMAL;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (position == 0)
                return;

            if (holder instanceof ItemHolder) {
                ItemHolder itemHolder = (ItemHolder) holder;
                final Image image = mImgLists.get(position);

                //加载图片
                Glide.with(holder.itemView.getContext())
                        .load(image.path)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
//                        .placeholder(R.color.darkgray)
//                        .error(R.color.darkgray)
                        .into(itemHolder.mPic);
                //设置选择阴影
                itemHolder.mShade.setVisibility(image.isSelect ? View.VISIBLE : View.INVISIBLE);
                //是否选择
                itemHolder.mSelected.setChecked(image.isSelect);
                //是否显示  未选择的图片checkbox不显示
                itemHolder.mSelected.setVisibility(image.isSelect ? View.VISIBLE : View.INVISIBLE);

                itemHolder.mPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemSelectClick(image)) {
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return mImgLists.size();
        }
    }

    private boolean onItemSelectClick(Image image) {

        boolean isNeedUpdateNotify = false;
        if (mSelectImages.contains(image)) {
            //如果包含了这个  在此点击需要移除列表 并通知状态更新
            mSelectImages.remove(image);
            image.isSelect = false;
            isNeedUpdateNotify = true;
        } else {
            if (mSelectImages.size() >= mMaxCount) {
                //然后去更新界面  如果不允许点击(已经达到我们最大的选择数量)  那么就不需要刷新数据
                Toast.makeText(getContext(), String.format(
                        getResources().getText(R.string.label_gallery_select_max_size).toString(),
                        mMaxCount), Toast.LENGTH_LONG).show();

                //不需要刷新
                isNeedUpdateNotify = false;
            } else {

                //如果不在已选择集合中  那么就添加到集合中
                mSelectImages.add(image);
                image.isSelect = true; //选择标志置为true
                isNeedUpdateNotify = true; //需要通知刷新

            }

        }

        if (isNeedUpdateNotify) {
            notifySelectSuccess(mSelectImages);
            notifySelectChanged();
        }

        //如果是需要刷新的  添加 或者删除都需要进行刷新

        return isNeedUpdateNotify;

    }

    private void notifySelectSuccess(ArrayList<Image> selectImages) {

    }


    class ItemHolder extends RecyclerView.ViewHolder {


        //图片
        public ImageView mPic;
        //引用
        public View mShade;
        //checkbox
        public CheckBox mSelected;

        public ItemHolder(View itemView) {
            super(itemView);

            mPic = (ImageView) itemView.findViewById(R.id.iv_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mSelected = (CheckBox) itemView.findViewById(R.id.cb_select);
        }
    }

    class CameraHolder extends RecyclerView.ViewHolder {

        RelativeLayout re_camera;

        public CameraHolder(final View itemView) {
            super(itemView);
            re_camera = (RelativeLayout) itemView.findViewById(R.id.re_camera);

            re_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "点击了我进行拍照", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * 图片Image   jvabean
     */
    class Image {
        int id;  //数据的id
        String path;  //图片的路径
        boolean isSelect; //图片是否选择
        long date; //图片创建的日期
        String imageDisplay;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;

        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }

    /**
     * 图片选择监听器
     */
    public interface SelectedChangeListener {
        /**
         * 选择的个数监听器
         *
         * @param count 图片个数
         */
        void onSelectedCountChanged(int count);
    }

}