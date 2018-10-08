package com.sk.xjwd.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.xrecyclerview.XRecyclerView;
import com.jph.takephoto.model.TImage;
import com.sk.xjwd.R;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.sk.xjwd.minehome.model.ChoosePhotoModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;
import com.zyf.fwms.commonlibrary.photo.PhotoModel;
import com.zyf.fwms.commonlibrary.utils.AutoUtils;
import com.zyf.fwms.commonlibrary.utils.CommonUtils;
import com.zyf.fwms.commonlibrary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 杭州融科网络
 * 刘宇飞创建 on 2017/6/19.
 * 描述：
 */

public class ChoosePhotoRV extends XRecyclerView {
    private Context context;
    private MineHomeAdapter adapter;
    private List<String> urlList=new ArrayList<>();
    private ArrayList<String> urlLists=new ArrayList<>();

    public ChoosePhotoRV(Context context) {
        super(context);
    }

    public ChoosePhotoRV(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public ChoosePhotoRV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context,attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        this.context=context;

        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        setNestedScrollingEnabled(false);
        setHasFixedSize(false);
        clearHeader();
        setLayoutManager(new GridLayoutManager(context,3));
        adapter = new MineHomeAdapter();
        setAdapter(adapter);
        adapter.add(new ChoosePhotoModel("").setViewType(MineHomeAdapter.CHOOOSE_PHOTO_ITME));
        adapter.setOnClickListener(new OnItemClickListener<BaseRecyclerModel>() {
            @Override
            public void onClick(View view, BaseRecyclerModel baseRecyclerModel, int position) {
                switch (view.getId()){
                    case R.id.tv_img://加号
                        if(position==adapter.getItemCount()-1){
                            showPhotoDialog();
                        }/*else {
                            int[] location = new int[2];
                            view.getLocationOnScreen(location);
                            int height = (AutoUtils.displayWidth - 20 * 4) / 3;
                            UIUtil.showMaxPhoto( context,location, getArrayData(),position,height,height);
                        }*/
                        break;
                    case R.id.iv_close://关闭图片
                        adapter.remove(position);
                        adapter.notifyDataSetChanged();
                        setHeight();
                        break;
                }
            }
        });

    }

    /**
     * 显示图片选择dialog
     */
    private void showPhotoDialog() {
        PhotoModel photoModel = new PhotoModel(context);
        photoModel.maxSize=10-adapter.getItemCount();
        if(photoModel.maxSize<=0)return;
        photoModel.setCallback(new PhotoModel.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(List<TImage> resultList) {
                for(TImage model :resultList){
                    LogUtil.getInstance().e(model.getCompressPath());
                    adapter.add(0,new ChoosePhotoModel(model.getCompressPath()).setViewType(MineHomeAdapter.CHOOOSE_PHOTO_ITME));
                }
                setHeight();
            }
        });
        photoModel.initTakePhoto();
    }

    public void addUrlDate(List<String> urlList){
        if(urlList!=null&&!urlList.isEmpty()){
            for(String url:urlList){
                adapter.add(0,new ChoosePhotoModel(url).setViewType(MineHomeAdapter.CHOOOSE_PHOTO_ITME));
            }
            setHeight();
        }

    }
    /**
     * 获取相片  注意减去加号图片
     * @return
     */
    public List<String> getData(){
        urlList.clear();
       for(BaseRecyclerModel model:adapter.getData()){
          if(model instanceof ChoosePhotoModel){
              if(CommonUtils.isNotEmpty(((ChoosePhotoModel) model).url)){
                 urlList.add(((ChoosePhotoModel) model).url);
              }
          }
       }
       return urlList;
    }
    public ArrayList<String> getArrayData(){
        urlLists.clear();
        for(BaseRecyclerModel model:adapter.getData()){
            if(model instanceof ChoosePhotoModel){
                if(CommonUtils.isNotEmpty(((ChoosePhotoModel) model).url)){
                    urlLists.add(((ChoosePhotoModel) model).url);
                }
            }
        }
        return urlLists;
    }
    private void setHeight(){
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int line;
        if(adapter.getItemCount()%3==0){
            line=adapter.getItemCount()/3;
        }else {
            line=adapter.getItemCount()/3+1;
        }
        layoutParams.height=(AutoUtils.designWidth-20*4)/3*line+60+15*(line);
        setLayoutParams(layoutParams);
        AutoUtils.autoSize(this);
        adapter.notifyDataSetChanged();
    }
}
