package com.sk.xjwd.minehome.model;

import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;

/**
 * 杭州融科网络
 * 刘宇飞创建 on 2017/6/19.
 * 描述：
 */

public class ChoosePhotoModel extends BaseRecyclerModel {
    public String url;

    public ChoosePhotoModel(String url) {
        this.url = url;
    }
    public ChoosePhotoModel setViewType(int viewType){
        this.viewType=viewType;
        return this;
    }
}
