package com.cainiao.cncooperation.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cainiao.cncooperation.R;
import com.cainiao.common.widget.adapter.BaseViewHolder;
import com.cainiao.common.widget.adapter.SimpleAdapter;
import com.cainiao.common.widget.camera.ImageSelectActivity;
import com.cainiao.factory.model.UploadImage;

import java.util.ArrayList;

/**
 * Created by wuyinlei on 2017/7/28.
 *
 * @function
 */

public class PublishImageAdapter extends SimpleAdapter<UploadImage> {

    public PublishImageAdapter(Context context) {
        super(context, R.layout.mind_circle_publish_image_adapter_item);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, UploadImage item, int position) {
        if (position + 1 > getDatas().size()) {
            //这个是 显示加号的
            viewHoder.getImageView(R.id.circle_public_item).setBackgroundResource(R.drawable.icon_addpic_focused);
            viewHoder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> strings = new ArrayList<>();

                    if (datas!= null && datas.size()>0){
                        for (UploadImage data : datas) {
                            strings.add(data.getPicadress());
                        }
                    }
                    ImageSelectActivity.start(context,strings);

                }
            });
        } else {
            Glide.with(context)
                    .load(item.getPicadress())
                    .asBitmap().centerCrop()
                    .into(viewHoder.getImageView(R.id.circle_public_item));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }
}
