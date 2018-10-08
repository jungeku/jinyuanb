package com.zyf.fwms.commonlibrary.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
public abstract class CommonBackTitleBinding extends ViewDataBinding {
    @NonNull
    public final android.widget.EditText etSearch;
    @NonNull
    public final android.widget.ImageView ivIconBack;
    @NonNull
    public final android.widget.ImageView ivRightImg;
    @NonNull
    public final android.widget.LinearLayout llEtLabel;
    @NonNull
    public final android.widget.LinearLayout llLiftBack;
    @NonNull
    public final android.widget.RelativeLayout llRightImg;
    @NonNull
    public final android.widget.LinearLayout llRightText;
    @NonNull
    public final android.widget.RelativeLayout rlSearch;
    @NonNull
    public final android.widget.RelativeLayout rlTitleBar;
    @NonNull
    public final android.widget.TextView tvRightText;
    @NonNull
    public final android.widget.TextView tvTitle;
    @NonNull
    public final android.widget.TextView tvUnRead;
    // variables
    protected CommonBackTitleBinding(@Nullable android.databinding.DataBindingComponent bindingComponent, @Nullable android.view.View root_, int localFieldCount
        , android.widget.EditText etSearch1
        , android.widget.ImageView ivIconBack1
        , android.widget.ImageView ivRightImg1
        , android.widget.LinearLayout llEtLabel1
        , android.widget.LinearLayout llLiftBack1
        , android.widget.RelativeLayout llRightImg1
        , android.widget.LinearLayout llRightText1
        , android.widget.RelativeLayout rlSearch1
        , android.widget.RelativeLayout rlTitleBar1
        , android.widget.TextView tvRightText1
        , android.widget.TextView tvTitle1
        , android.widget.TextView tvUnRead1
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.etSearch = etSearch1;
        this.ivIconBack = ivIconBack1;
        this.ivRightImg = ivRightImg1;
        this.llEtLabel = llEtLabel1;
        this.llLiftBack = llLiftBack1;
        this.llRightImg = llRightImg1;
        this.llRightText = llRightText1;
        this.rlSearch = rlSearch1;
        this.rlTitleBar = rlTitleBar1;
        this.tvRightText = tvRightText1;
        this.tvTitle = tvTitle1;
        this.tvUnRead = tvUnRead1;
    }
    //getters and abstract setters
    @NonNull
    public static CommonBackTitleBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static CommonBackTitleBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static CommonBackTitleBinding bind(@NonNull android.view.View view) {
        return null;
    }
    @NonNull
    public static CommonBackTitleBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static CommonBackTitleBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static CommonBackTitleBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}