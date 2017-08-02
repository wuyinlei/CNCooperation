package com.cainiao.cncooperation.ui.dynamic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.cncooperation.adapter.PublishImageAdapter;
import com.cainiao.common.base.BaseActivity;
import com.cainiao.common.constant.Common;
import com.cainiao.common.utils.luban.Luban;
import com.cainiao.common.widget.camera.ImageSelectActivity;
import com.cainiao.factory.utils.UploadHelper;
import com.cainiao.factory.model.UploadImage;
import com.cainiao.factory.presenter.dynamic.DynamicPublishContract;
import com.cainiao.factory.presenter.dynamic.DynamicPublishPresenter;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wuyinlei on 2017/7/27.
 *
 * @function 动态发布界面
 */

public class DynamicPublishActivity extends BaseActivity implements DynamicPublishContract.View {

    @BindView(R.id.action_mindcirrcle_message)
    RelativeLayout mActionBack;

    @BindView(R.id.action_mindcircle_view)
    ImageView mActionImageBack;

    @BindView(R.id.action_bar_title)
    TextView mActionBarTitle;

    @BindView(R.id.action_mindcirrcle_publish)
    RelativeLayout mActionPublish;

    @BindView(R.id.action_create_btn)
    ImageView mActionImagePublish;

    @BindView(R.id.circle_content_edit)
    EditText mCircleContentEdit;

    @BindView(R.id.photo_viewer)
    RecyclerView mPhotoViewer;

    @BindView(R.id.circle_location_layout)
    RelativeLayout mCircleLocationLayout;

    @BindView(R.id.circle_anon_switch)
    SwitchButton mCircleAnonSwitch;

    private DynamicPublishPresenter mPublishPresenter;

    //选择的图片
    private ArrayList<String> mImageResults = new ArrayList<>();
    //需要上传的图片
    private ArrayList<UploadImage> mUploadImages = new ArrayList<>();

    private Set<UploadImage> mHasUploadedImages = new HashSet<>();


    //上传的服务器之后返回来的图片地址
    private ArrayList<String> mServerUrls = new ArrayList<>();
    private ArrayList<String> mLocalPathUrls = new ArrayList<>();


    public static void show(Context context) {
        Intent intent = new Intent(context, DynamicPublishActivity.class);

        ((Activity) context).startActivityForResult(intent, Common.Constance.DYNAMIC_REQUEST_CODE);
    }

    PublishImageAdapter mImageAdapter;

    //关闭
    @OnClick(R.id.action_mindcirrcle_message)
    public void close() {

        new AlertDialog.Builder(this).setTitle(getString(R.string.upload_dialog_title)).setMessage("")
                .setPositiveButton(getString(R.string.upload_dialog_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DynamicPublishActivity.this.finish();
                    }
                }).setNegativeButton(getString(R.string.upload_dialog_quit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();


    }

    //发布动态
    @OnClick(R.id.action_mindcirrcle_publish)
    public void publishDynamic() {
        if (mServerUrls.size() >= mImageResults.size()) {

            String content = mCircleContentEdit.getText().toString().trim();

            mPublishPresenter.dynamicPublish(content, mServerUrls);

            Log.d("DynamicPublishActivity", "mServerUrls.size():" + mServerUrls.size());
            Log.d("DynamicPublishActivity", "mImageResults.size():" + mImageResults.size());
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.mind_circle_publish_edit_view;
    }

    @Override
    protected void initView() {
        super.initView();
        mActionImageBack.setBackgroundResource(R.drawable.ic_close_drawable);
        mActionBarTitle.setText(getString(R.string.circle_content_publish));
        mActionImagePublish.setBackgroundResource(R.drawable.mind_circle_post_icon);

        mPublishPresenter = new DynamicPublishPresenter(this);

    }

    @Override
    protected BaseActivity injectTarget() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelectActivity.RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                mImageResults.clear();
                mUploadImages.clear();
                mImageResults = data.getStringArrayListExtra(ImageSelectActivity.EXTRA_DEFAULT_SELECTED_LIST);
//                Toast.makeText(this, "mImageResults.size():" + mImageResults.size(), Toast.LENGTH_SHORT).show();
                //接收到的数据
                for (String result : mImageResults) {
                    UploadImage uploadImage = new UploadImage();
                    uploadImage.setPicadress(result);
                    mUploadImages.add(uploadImage);
                }

                // TODO: 2017/7/28   这个地方貌似有问题   再次选择的时候
                mImageAdapter.refreshData(mUploadImages);

                // TODO: 2017/7/28 在这个地方可以执行上传的逻辑


//                for (UploadImage image : mUploadImages) {
//                    for (UploadImage uploadImage : mHasUploadedImages) {
//                        if (uploadImage.getPicadress().equals(image.getPicadress()))
//                            mUploadImages.remove(image);
//                    }
//                }

                uploadImageService(mUploadImages);

            }
        }


    }

    /**
     * 上传图片的逻辑
     *
     * @param uploadImages 需要上传的图片  其实这个时候写 有个问题 什么问题那就自己想吧
     */
    private void uploadImageService(ArrayList<UploadImage> uploadImages) {
        for (final UploadImage uploadImage : uploadImages) {
            if (!uploadImage.isHasUpload()) {
//                //没有上传
//                Luban.with(this)
//                        .load(new File(uploadImage.getPicadress()))
//                        .setCompressListener(new OnCompressListener() {
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onSuccess(File file) {
//                                String serverUrl = UploadHelper.uploadImage(uploadImage.getPicadress(), null);
//                                if (serverUrl != null) {
//                                    mServerUrls.add(serverUrl);
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//                        });
                if (mLocalPathUrls.contains(uploadImage.getPicadress()))
                    continue;

                Observable.just(new File(uploadImage.getPicadress()))
                        .observeOn(Schedulers.io())
                        .map(new Func1<File, File>() {
                            @Override
                            public File call(File file) {
                                try {
                                    return Luban.with(DynamicPublishActivity.this).load(file).get();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<File>() {
                            @Override
                            public void call(File file) {
                                String serverUrl = UploadHelper.uploadImage(uploadImage.getPicadress(), null);
                                if (serverUrl != null) {
                                    uploadImage.setHasUpload(true);
//                                    mHasUploadedImages.add(uploadImage);
                                    mServerUrls.add(serverUrl);
                                    mLocalPathUrls.add(uploadImage.getPicadress());
                                }
                            }
                        });
//                        .subscribe(new Subscriber<File>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(File file) {
//                                String serverUrl = UploadHelper.uploadImage(uploadImage.getPicadress(), null);
//                                if (serverUrl != null) {
//                                    mServerUrls.add(serverUrl);
//                                }
//                            }
//                        });

//                        .subscribe(new Consumer<File>() {
//                            @Override public void accept(@NonNull File file) throws Exception {
//                                Log.d(TAG, file.getAbsolutePath());
//
//                                Glide.with(MainActivity.this).load(file).into(image);
//
//                                thumbFileSize.setText(file.length() / 1024 + "k");
//                                thumbImageSize.setText(computeSize(file)[0] + "*" + computeSize(file)[1]);
//                            }
//                        });

            }

        }

    }

    @Override
    protected void initData() {
        super.initData();
        mImageAdapter = new PublishImageAdapter(this);
        mPhotoViewer.setLayoutManager(new GridLayoutManager(this, 5));
        mPhotoViewer.setAdapter(mImageAdapter);
    }


    @Override
    public void showError(@StringRes int str) {
        Toast.makeText(this, getString(str), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void publishDynamicSuccess(String postId,  String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        //这样的话可以模拟本地发表
        intent.putStringArrayListExtra(Common.Constance.DYNAMIC_IMAGE, mImageResults);
        intent.putExtra(Common.Constance.DYNAMIC_CONTENT, mCircleContentEdit.getText().toString().trim());
        intent.putExtra(Common.Constance.OBECJT_ID, postId);
        setResult(Common.Constance.DYNAMIC_RESULT_CODE, intent);
        finish();
    }
}
