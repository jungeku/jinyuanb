package com.zyf.fwms.commonlibrary.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
public abstract class FragmentBaseBinding extends ViewDataBinding {
    @Nullable
    public final com.zyf.fwms.commonlibrary.databinding.CommonBackTitleBinding commonTitle;
    @NonNull
    public final android.widget.RelativeLayout container;
    @NonNull
    public final android.widget.ImageView imgErr;
    @NonNull
    public final android.widget.LinearLayout llErrorRefresh;
    @NonNull
    public final android.widget.LinearLayout rlRootRoot;
    // variables
    protected FragmentBaseBinding(@Nullable android.databinding.DataBindingComponent bindingComponent, @Nullable android.view.View root_, int localFieldCount
        , com.zyf.fwms.commonlibrary.databinding.CommonBackTitleBinding commonTitle1
        , android.widget.RelativeLayout container1
        , android.widget.ImageView imgErr1
        , android.widget.LinearLayout llErrorRefresh1
        , android.widget.LinearLayout rlRootRoot1
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.commonTitle = commonTitle1;
        setContainedBinding(this.commonTitle);
        this.container = container1;
        this.imgErr = imgErr1;
        this.llErrorRefresh = llErrorRefresh1;
        this.rlRootRoot = rlRootRoot1;
    }
    //getters and abstract setters
    @NonNull
    public static FragmentBaseBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentBaseBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentBaseBinding bind(@NonNull android.view.View view) {
        return null;
    }
    @NonNull
    public static FragmentBaseBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static FragmentBaseBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static FragmentBaseBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}