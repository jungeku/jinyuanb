package com.sk.xjwd.update;


/**
 * Created by Jepson on 2016/11/10.
 */
/*
public class HomeFragment extends Fragment{
    private NormalTopBar mTopBar;
    private ListView mlist;
    private HomeAdapter mAdapter;
    private Intent intent;
    private TextView title;
    private ImageView headerOne;
    private ImageView headerTwo;
    private ImageView imageAnger;
    private RecyclerViewBanner recyclerViewBanner;
    private ACache mCache;

    private String versionName;

    private SharedPreferences sp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        sp = getContext().getSharedPreferences("info", getContext().MODE_PRIVATE);
        mCache = ACache.get(getContext());
        initView(view);

        //判断网络
        if(NetworkUtils.isAvailable(getContext()) == false){
            HomeHeaderBean headerBean = (HomeHeaderBean) mCache.getAsObject("homeHeaderBean");
            //判断是否有缓存、有缓存就加载缓存
            if (mCache.getAsObject("homeHeaderBean") != null){
                Glide.with(getContext()).load(GlobalConstants.SERVIER_URL+headerBean.getData().getIcon()).into(imageAnger);
                title.setText(headerBean.getData().getTitle());
                final List<Banner> banners = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    banners.add(new Banner(GlobalConstants.SERVIER_URL+headerBean.getData().getPone()));
                    banners.add(new Banner(GlobalConstants.SERVIER_URL+headerBean.getData().getPthree()));
                    banners.add(new Banner(GlobalConstants.SERVIER_URL+headerBean.getData().getPtwo()));
                }
                recyclerViewBanner.isShowIndicatorPoint(true);
                recyclerViewBanner.setRvBannerDatas(banners);
                recyclerViewBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, ImageView bannerView) {
                        Glide.with(bannerView.getContext()).load(banners.get(position % banners.size()).getUrl()).placeholder(R.mipmap.loading).into(bannerView);
                    }
                });
                Glide.with(getContext()).load(GlobalConstants.SERVIER_URL+headerBean.getData().getPfour()).into(headerOne);
                Glide.with(getContext()).load(GlobalConstants.SERVIER_URL+headerBean.getData().getPfive()).into(headerTwo);
            }
			
            LanguageBean bean = (LanguageBean) mCache.getAsObject("homeBean");
            //判断是否有缓存、有缓存就加载缓存
            if (mCache.getAsObject("homeBean") != null){
                loadData(bean.getData());
                mAdapter.notifyDataSetChanged();
            }
        }else {
            doGetHome();//网络请求
            doList();//网络请求
            if (!TextUtils.isEmpty(sp.getString("token",null))){
                doSelectMsg(String.valueOf(sp.getInt("id",0)),sp.getString("token",null));
                mTopBar.setOnMessageListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doMessage(String.valueOf(sp.getInt("id",0)),sp.getString("token",null));
                        intent = new Intent(getContext(), MessageActivity.class);
                        startActivity(intent);
                    }
                });
            }else {
                mTopBar.setOnMessageListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }

        getApkVersion();
        chechVersionRequest();
        return  view;
    }


    //获取apk版本
    private void getApkVersion(){
        int versionCode = com.siyu.energy.BuildConfig.VERSION_CODE;
        versionName = com.siyu.energy.BuildConfig.VERSION_NAME;
    }

    //更新弹框
    private void checkVersion(){
        //这里不发送检测新版本网络请求，直接进入下载新版本安装
        CommonDialog.Builder builder = new CommonDialog.Builder(getContext());
        builder.setTitle("升级提示");
        builder.setMessage("发现新版本，请及时更新");
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getContext(), UpdateService.class);
                intent.putExtra("apkUrl", GlobalConstants.UpdataApk);
                getActivity().startService(intent);

            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //更新应用的网络请求
    private void chechVersionRequest(){
        OkHttpUtils
                .get()
                .url(GlobalConstants.FINDALL)
                .build()
                .execute(new UpdateCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                    @Override
                    public void onResponse(UpdateBean response, int id) {
                        super.onResponse(response, id);
                        if (!versionName.equals(response.getVersions())){
                            checkVersion();
                        }
                    }
                });
    }

    //查询消息状态网络请求
    private void doSelectMsg(String id,String token){
        OkHttpUtils
                .post()
                .url(GlobalConstants.SELECTMSG)
                .addParams("id",id)
                .addParams("token",token)
                .build()
                .execute(new MsgCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                    @Override
                    public void onResponse(MsgBean response, int id) {
                        super.onResponse(response, id);
                        if ("000000".equals(response.getCode())){
                            if (response.getData() == 0){//未读状态
                                mTopBar.setTabSelected(false);
                            }else if (response.getData() == 1){//已读状态
                                mTopBar.setTabSelected(true);
                            }
                        }else if ("666666".equals(response.getCode())){
                            Toast toast = Toast.makeText(getContext(),"您已被强制下线",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            ExitUtil.getInstance(getContext()).setExit();
                        }
                    }

                    @Override
                    public MsgBean parseNetworkResponse(Response response, int id) throws Exception {
                        return super.parseNetworkResponse(response, id);
                    }
                });
    }

    //消息状态的网络请求
    private void doMessage(String id,String token){
        OkHttpUtils
                .post()
                .url(GlobalConstants.MSG_URL)
                .addParams("id",id)
                .addParams("token",token)
                .build()
                .execute(new MsgCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                    @Override
                    public void onResponse(MsgBean response, int id) {
                        super.onResponse(response, id);
                        if ("000000".equals(response.getCode())){
                            //可阅读、状态
                            if (response.getData() == 0){//未读状态
                                mTopBar.setTabSelected(false);
                            }else if (response.getData() == 1){//已读状态
                                mTopBar.setTabSelected(true);
                            }
                        }else if ("666666".equals(response.getCode())){
                            Toast toast = Toast.makeText(getContext(),"您已被强制下线",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            ExitUtil.getInstance(getContext()).setExit();
                        }
                    }

                    @Override
                    public MsgBean parseNetworkResponse(Response response, int id) throws Exception {
                        return super.parseNetworkResponse(response, id);
                    }
                });
    }

    //列表目录网络请求
    public void doList(){
        OkHttpUtils
                .get()
                .url(GlobalConstants.TYPEA_URL)
                .build()
                .execute(new LanguageCallback() {
                    @Override
                    public LanguageBean parseNetworkResponse(Response response, int id) throws Exception {
                        String s  = response.body().string();
                        LanguageBean bean = new Gson().fromJson(s, LanguageBean.class);
                        mCache.put("homeBean",bean);
                        return bean;
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                    @Override
                    public void onResponse(LanguageBean response, int id) {
                        super.onResponse(response, id);
                        if (response.getCode().equals("000000")){
                            List<LanguageBean.LanguageData> languageBeanList = response.getData();
                            loadData(languageBeanList);
                        }
                    }
                });
    }

    //首页页面展示请求网络
    public void doGetHome(){
        OkHttpUtils
                .get()
                .url(GlobalConstants.GET_URL)
                .build()
                .execute(new HomeHeaderCallback() {
                    @Override
                    public HomeHeaderBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        HomeHeaderBean headerBean = new Gson().fromJson(string,HomeHeaderBean.class);
                        mCache.put("homeHeaderBean",headerBean);
                        return headerBean;
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e,id);
                    }
                    @Override
                    public void onResponse(HomeHeaderBean response, int id) {
                        super.onResponse(response, id);
                        if ("000000".equals(response.getCode())){
                            Glide.with(getContext()).load(GlobalConstants.SERVIER_URL+response.getData().getIcon()).placeholder(R.mipmap.anger_image).into(imageAnger);
                            title.setText(response.getData().getTitle());
                            final List<Banner> banners = new ArrayList<>();
                            for (int i = 0; i < 1; i++) {
                                banners.add(new Banner(GlobalConstants.SERVIER_URL+response.getData().getPone()));
                                banners.add(new Banner(GlobalConstants.SERVIER_URL+response.getData().getPthree()));
                                banners.add(new Banner(GlobalConstants.SERVIER_URL+response.getData().getPtwo()));
                            }
                            recyclerViewBanner.isShowIndicatorPoint(true);
                            recyclerViewBanner.setRvBannerDatas(banners);
                            recyclerViewBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                                @Override
                                public void switchBanner(int position, ImageView bannerView) {
                                    Glide.with(bannerView.getContext()).load(banners.get(position % banners.size()).getUrl()).placeholder(R.mipmap.loading).into(bannerView);
                                }
                            });
                            Glide.with(getContext()).load(GlobalConstants.SERVIER_URL+response.getData().getPfour()).into(headerOne);
                            Glide.with(getContext()).load(GlobalConstants.SERVIER_URL+response.getData().getPfive()).into(headerTwo);
                        }
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onResume() {
        super.onResume();
        showInfo();
        doList();//网络请求
        doGetHome();
        if (!TextUtils.isEmpty(sp.getString("token",null))){
            doSelectMsg(String.valueOf(sp.getInt("id",0)),sp.getString("token",null));
            mTopBar.setOnMessageListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doMessage(String.valueOf(sp.getInt("id",0)),sp.getString("token",null));
                    intent = new Intent(getContext(), MessageActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            mTopBar.setOnMessageListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initView(View view) {
        View headView = View.inflate(getContext(),R.layout.list_home_head,null);
        title = (TextView) headView.findViewById(R.id.textTitle);
        imageAnger = (ImageView) headView.findViewById(R.id.iv_anger);
        headerOne = (ImageView) headView.findViewById(R.id.headerOne);
        headerTwo = (ImageView) headView.findViewById(R.id.headerTwo);
        headerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"此链接暂无内容",Toast.LENGTH_SHORT).show();
            }
        });
        headerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"此链接暂无内容",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewBanner = (RecyclerViewBanner) view.findViewById(R.id.rv_banner);
        mTopBar = (NormalTopBar) view.findViewById(R.id.home_top_bar);
        mlist = (ListView) view.findViewById(R.id.lv_list);
        mTopBar.setTitle("首页");

        mTopBar.setOnShareListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idd = sp.getInt("id",0);
                if (idd != 0){
                    String ids = String.valueOf(idd);
                    ShowShareUtil.getInstance(getContext()).showShareID(ids);
                }else {
                    ShowShareUtil.getInstance(getContext()).showShare();
                }

            }
        });
        mlist.addHeaderView(headView, null, false);//header不能点击

        //有保存用户信息
        showInfo();
    }

    //显示保存用户的信息
    private void showInfo() {
        String name = sp.getString("userName", null);
        if (!TextUtils.isEmpty(name)) {
            mTopBar.setOnSettingListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            mTopBar.setOnSettingListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void loadData(final List<LanguageBean.LanguageData> languageBeanList) {
        mAdapter = new HomeAdapter(getContext(), languageBeanList, new HomeAdapter.HomeOnClickListener() {
            @Override
            public void setOnClickListener(String id) {
                intent = new Intent(getContext(),LanguageListActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        mlist.setAdapter(mAdapter);

    }
    private class Banner {

        String url;

        public Banner(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}

*/
