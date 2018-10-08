package com.sk.xjwd.checking.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.sk.xjwd.R;
import com.sk.xjwd.base.BaseActivity;
import com.sk.xjwd.checking.holder.AccountGridModel;
import com.sk.xjwd.checking.holder.CheckingAccountGriditemHolder;
import com.sk.xjwd.checking.presenter.AddActivityPresenter;
import com.sk.xjwd.databinding.CheckingActivityAddBinding;
import com.sk.xjwd.minehome.adapter.MineHomeAdapter;
import com.zyf.fwms.commonlibrary.base.baseadapter.AutoLayoutManager;
import com.zyf.fwms.commonlibrary.base.baseadapter.BaseRecyclerModel;
import com.zyf.fwms.commonlibrary.base.baseadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by mayn on 2018/9/11.
 */

public class AddActivity extends BaseActivity<AddActivityPresenter,CheckingActivityAddBinding>{

    private MineHomeAdapter adapter;
    private List<BaseRecyclerModel> modelList=new ArrayList<>();
    private int[] expandUnclickIcon={R.mipmap.unclick_eatdrink,R.mipmap.unclick_cloths,R.mipmap.unclick_commodities,R.mipmap.unclick_education,R.mipmap.unclick_enterment,
            R.mipmap.unclick_fruit,R.mipmap.unclick_yanjiu,R.mipmap.unclick_livefee,R.mipmap.unclick_medical,R.mipmap.unclick_phonefee};
    private int[] expandClickedIcon={R.mipmap.clicked_eatdrink,R.mipmap.clicked_cloth,R.mipmap.clicked_commodities,R.mipmap.clicked_edcation,R.mipmap.clicked_enterment,
            R.mipmap.clicked_fruit,R.mipmap.clicked_yanjiu,R.mipmap.clicked_livefee,R.mipmap.clicked_medical,R.mipmap.clicked_phonefee};
    private String[] expandName={"吃喝","衣服","日用品","教育","娱乐","水果","烟酒","住宿","医疗","电话费"};
    private int[] incomeUnclickIcon={R.mipmap.unclick_salary,R.mipmap.unclick_inestment,R.mipmap.unclick_parttime,R.mipmap.unclick_redpacket,R.mipmap.unclick_reward};
    private int[] incomeClickedIcon={R.mipmap.clicked_salary,R.mipmap.click_investment,R.mipmap.click_parttime,R.mipmap.click_redpacket,R.mipmap.click_reward};
    private String[] incomeName={"工资","投资","兼职","红包","奖金"};
    private boolean isitemPressed=false;
    List<AccountGridModel> incomelist=new ArrayList<>();
    List<AccountGridModel> expandlist=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checking_activity_add);

    }

    @OnClick({R.id.img_add_cancel})
    void click(View view){
        StringBuilder sb=new StringBuilder();
        switch (view.getId()){
            case R.id.img_add_cancel:
                if(CheckingAccountGriditemHolder.isIncome) {
                    sb.delete(0,sb.length());
                    incomelist.clear();
                    incomelist.add(CheckingAccountGriditemHolder.incomemodel);
                    for (int i = 0; i <incomelist.size(); i++) {
                          if(incomelist.get(i).isChecked) sb = sb.append(incomelist.get(i).name);
                    }
                }else {
                    sb.delete(0,sb.length());
                    expandlist.clear();
                    expandlist.add(CheckingAccountGriditemHolder.expandmodel);
                    for (int i = 0; i <expandlist.size(); i++) {
                        if(expandlist.get(i).isChecked) sb = sb.append(expandlist.get(i).name);
                    }
                }
                Toast.makeText(AddActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }



    @Override
    protected void initView() {
        adapter = new MineHomeAdapter();
        mBindingView.gvAdd.setLoadingMoreEnabled(false);
        mBindingView.gvAdd.setPullRefreshEnabled(false);
        mBindingView.gvAdd.setLayoutManager(new AutoLayoutManager(mContext,0));
        mBindingView.gvAdd.setNestedScrollingEnabled(false);
        mBindingView.gvAdd.setHasFixedSize(false);
        mBindingView.gvAdd.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        mBindingView.gvAdd.setLayoutManager(layoutManager);
        initData(false);//false是花费支出，true是收入
        mBindingView.rbAddExpand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckingAccountGriditemHolder.isIncome=false;
                initData(false);
            }
        });
        mBindingView.rbAddIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckingAccountGriditemHolder.isIncome=true;
                initData(true);
            }
        });
    }

    private void initData(boolean b) {
        modelList.clear();
        if (b){//打开收入图标列表
            CheckingAccountGriditemHolder.incomemodel=new AccountGridModel();
            for(int i=0;i<incomeClickedIcon.length;i++){
                AccountGridModel model=CheckingAccountGriditemHolder.incomemodel;
                model.unClickicon=incomeUnclickIcon[i];
                model.Clickedicon=incomeClickedIcon[i];
                model.name=incomeName[i];
                model.id=i;
                model.viewType=MineHomeAdapter.CHECKING_ACCOUNT_GRID;
                modelList.add(model);
            }
            adapter.addAll(modelList);
            adapter.notifyDataSetChanged();
        }else {//打开支出图标列表
            CheckingAccountGriditemHolder.expandmodel=new AccountGridModel();
            for(int i=0;i<expandClickedIcon.length;i++){
                AccountGridModel model=CheckingAccountGriditemHolder.expandmodel;
                model.Clickedicon=expandClickedIcon[i];
                model.unClickicon=expandUnclickIcon[i];
                model.name=expandName[i];
                model.id=i;
                model.viewType=MineHomeAdapter.CHECKING_ACCOUNT_GRID;
                modelList.add(model);
            }
            adapter.addAll(modelList);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void initPresenter() {
         mPresenter.setView(this);
    }

}
