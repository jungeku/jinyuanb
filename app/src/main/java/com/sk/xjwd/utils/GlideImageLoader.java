package com.sk.xjwd.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sk.xjwd.MyApplication;
import com.sk.xjwd.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/7/12.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(MyApplication.getContext()).load(path).crossFade().placeholder(R.mipmap.banner).into(imageView);
    }

}
