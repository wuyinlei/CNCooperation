package com.cainiao.cncooperation.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cainiao.cncooperation.R;
import com.cainiao.common.widget.camera.ImageSelectActivity;

import java.util.ArrayList;

public class TestSelectImageActivity extends AppCompatActivity {

    private ArrayList<String> mImageResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_select_image);
    }

    public void bt_select(View view){
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.EXTRA_SHOW_CAMERA,true);
        intent.putExtra(ImageSelectActivity.EXTRA_SELECT_COUNT,9);
        intent.putExtra(ImageSelectActivity.EXTRA_SELECT_MODE,ImageSelectActivity.MODE_MULTI);
        intent.putStringArrayListExtra(ImageSelectActivity.EXTRA_DEFAULT_SELECTED_LIST,mImageResults);
        startActivityForResult(intent,ImageSelectActivity.RESULT_CODE);
//        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelectActivity.RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                mImageResults = data.getStringArrayListExtra(ImageSelectActivity.EXTRA_DEFAULT_SELECTED_LIST);
                Toast.makeText(this, "mImageResults.size():" + mImageResults.size(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
