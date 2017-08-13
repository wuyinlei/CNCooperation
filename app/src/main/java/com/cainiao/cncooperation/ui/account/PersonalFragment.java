package com.cainiao.cncooperation.ui.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.base.PresenterFragment;
import com.cainiao.common.constant.Common;
import com.cainiao.common.widget.circleimage.CircleImageView;
import com.cainiao.common.widget.imageloader.ImageLoader;
import com.cainiao.factory.app.Account;
import com.cainiao.factory.model.MyUser;
import com.cainiao.factory.presenter.account.PersonalContract;
import com.cainiao.factory.presenter.account.PersonalPresenter;
import com.cainiao.factory.utils.UploadHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @author wuyinlei
 * @function 这个是用户自己的详情界面
 */
public class PersonalFragment extends PresenterFragment<PersonalContract.Presenter>
        implements PersonalContract.View {


    @BindView(R.id.profile_avatar)
    CircleImageView mIvAvatar;

    @BindView(R.id.profile_nickname_txt)
    TextView mUserAlias;

    @BindView(R.id.profile_address_txt)
    TextView mUserAdress;

    @BindView(R.id.profile_gender_txt)
    TextView mUserGender;

    @BindView(R.id.profile_dob_txt)
    TextView mUserBirth;

    @BindView(R.id.profile_slogan_txt)
    TextView mUserSolgon;

    @BindView(R.id.profile_mobile_txt)
    TextView mUserPhone;

    @BindView(R.id.ll_container)
    LinearLayout mActivityUser;

    private RelativeLayout layout_choose;
    private RelativeLayout layout_photo;
    private RelativeLayout layout_close;


    PopupWindow avatorPop;
    private Bitmap mBitmap;
    private String mPortrait;


    @Override
    protected void initData() {
        super.initData();

        updateUI(Account.getUser());

    }

    private void updateUI(MyUser user) {

        ImageLoader.load(user.getAvatar(), mIvAvatar);

        mPortrait = user.getAvatar();

        mUserAlias.setText(TextUtils.isEmpty(user.getAlias()) ?
                getActivity().getResources().getString(R.string.edit_personal_alias) :
                user.getAlias());

        mUserAdress.setText(TextUtils.isEmpty(user.getAddress()) ?
                getActivity().getResources().getString(R.string.edit_personal_address) :
                user.getAddress());

        mUserGender.setText(user.getSex() ? "女" : "男");

        mUserBirth.setText(TextUtils.isEmpty(user.getBirthday()) ?
                getActivity().getResources().getString(R.string.edit_personal_birth)
                : user.getBirthday());

        mUserSolgon.setText(TextUtils.isEmpty(user.getDescription()) ?
                getActivity().getResources().getString(R.string.personal_lazy_desc)
                : user.getDescription());

        if (user.getMobilePhoneNumberVerified() != null && user.getMobilePhoneNumberVerified()) {
            mUserPhone.setText(user.getMobilePhoneNumber());
        }

    }

    @OnClick(R.id.ic_back)
    public void close() {
        getActivity().finish();
    }

    @OnClick(R.id.tv_save)
    public void save() {

        mPresenter.updateUserInfo(getActivity(),
                mPortrait,
                mUserSolgon.getText().toString().trim(),
                mUserAdress.getText().toString().trim(),
                !mUserGender.getText().equals("男"),
                mUserAlias.getText().toString().trim(),
                mUserBirth.getText().toString().trim(), null);
    }

    //点击了用户
    @OnClick(R.id.profile_avatar_layout)
    public void clickUpdatePortrait() {
        showSelectPop();
    }

    protected int mScreenWidth;


    private void showSelectPop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_personal_pop_camera,
                null);
        layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
        layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
        layout_close = (RelativeLayout) view.findViewById(R.id.layout_close);

        layout_choose.setBackgroundColor(getResources().getColor(
                R.color.white));
        layout_photo.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.pop_bg_press));
        layout_close.setBackgroundColor(getResources().getColor(
                R.color.white));

        layout_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_choose.setBackgroundColor(getResources().getColor(
                        R.color.white));
                layout_photo.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                layout_close.setBackgroundColor(getResources().getColor(
                        R.color.white));


                openCamera();

            }
        });

        layout_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_photo.setBackgroundColor(getResources().getColor(
                        R.color.white));
                layout_choose.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                layout_close.setBackgroundColor(getResources().getColor(
                        R.color.white));
                openPic();
            }
        });

        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_photo.setBackgroundColor(getResources().getColor(
                        R.color.white));
                layout_close.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                layout_choose.setBackgroundColor(getResources().getColor(
                        R.color.white));
                avatorPop.dismiss();
            }
        });


        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        avatorPop = new PopupWindow(view, mScreenWidth, 200);

        avatorPop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    avatorPop.dismiss();
                    return true;
                }
                return false;
            }
        });

        avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        avatorPop.setTouchable(true);
        avatorPop.setFocusable(true);
        avatorPop.setOutsideTouchable(true);
        avatorPop.setBackgroundDrawable(new BitmapDrawable());
        // 动画效果 从底部弹起
        avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
        avatorPop.showAtLocation(mActivityUser, Gravity.BOTTOM, 0, 0);


    }

    private void openPic() {


    }

    private File mFile;

    private void openCamera() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdirs();
            }
            mFile = new File(file, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, Common.Constance.REQUESTCODE_CAM);
        } else {
            Toast.makeText(this, getResources()
                            .getString(R.string.please_insert_sd_card),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.profile_nickname_layout)
    public void clickUpdateAlias() {
        EditorInfoActivity.show(getActivity(),
                Common.Constance.USER_NICK_NAME_TYPE,
                TextUtils.isEmpty(mUserAlias.getText().toString().trim()) ?
                        mUserAlias.getText().toString().trim() :
                        mUserAlias.getText().toString().trim());
    }

    //点击更改地址
    @OnClick(R.id.profile_address_layout)
    public void clickUpdateAddress() {
        EditorInfoActivity.show(getActivity(), Common.Constance.USER_ADDRESS_TYPE, TextUtils.isEmpty(mUserAdress.getText().toString().trim()) ?
                getActivity().getResources().getString(R.string.personal_address) : mUserAdress.getText().toString().trim());

    }

    //更改用户的性别
    @OnClick(R.id.profile_gender_layout)
    public void clickUpdateGender() {

    }

    //点击更改生日
    @OnClick(R.id.profile_dob_layout)
    public void clickUpdateBirth() {

    }

    //点击更改个性签名
    @OnClick(R.id.profile_slogan_layout)
    public void clickUserDesc() {
        EditorInfoActivity.show(getActivity(), Common.Constance.USER_DESC_TYPE,
                TextUtils.isEmpty(mUserSolgon.getText().toString().trim()) ?
                        getActivity().getResources().getString(R.string.personal_desc) :
                        mUserSolgon.getText().toString().trim());

    }

    //点击绑定手机
    @OnClick(R.id.profile_mobile_layout)
    public void clickBindPhone() {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void showError(@StringRes int str) {

    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Common.Constance.USER_INFO_RESULT_CODE) {
            switch (requestCode) {
                case Common.Constance.USER_NICK_NAME_TYPE:
                    mUserAlias.setText(data.getStringExtra(Common.Constance.USER_UPDATE_INFO_RESULT));
                    break;
                case Common.Constance.USER_ADDRESS_TYPE:
                    mUserAdress.setText(data.getStringExtra(Common.Constance.USER_UPDATE_INFO_RESULT));
                    break;
                case Common.Constance.USER_DESC_TYPE:
                    mUserSolgon.setText(data.getStringExtra(Common.Constance.USER_UPDATE_INFO_RESULT));
                    break;
            }
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Common.Constance.REQUESTCODE_CAM:
                    startPhotoZoom(Uri.fromFile(mFile));
                    break;

                case Common.Constance.REQUESTCODE_CUT:

                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
            }
        }


    }

    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {

//
//            Uri selectedImage = data.getData();
//
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//

            //这里也可以做文件上传
            mBitmap = bundle.getParcelable("data");
            // ivHead.setImageBitmap(mBitmap);
            mIvAvatar.setImageBitmap(mBitmap);
            String path = "";
//
//            if (picturePath!=null){
//                path = picturePath;
//            }

            if (mFile != null)
                path = mFile.getPath();

            mPortrait = UploadHelper.uploadPortrait(path);

//         }

        }


    }

    /**
     * 打开系统图片裁剪功能
     *
     * @param uri uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true); //黑边
        intent.putExtra("scaleUpIfNeeded", true); //黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, Common.Constance.REQUESTCODE_CUT);

    }

    @Override
    public void onSaveFailure(int errorCode, String errMsg) {
        Log.d("PersonalFragment", "errorCode:" + errorCode);
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveSuccess(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
