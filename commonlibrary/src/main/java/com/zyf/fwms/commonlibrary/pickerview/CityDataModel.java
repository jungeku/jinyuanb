package com.zyf.fwms.commonlibrary.pickerview;

import java.util.List;

/**
 * 杭州融科网络
 * 刘宇飞创建 on 2017/4/27.
 * 描述：
 */

public class CityDataModel {


    public String areaId;
    public String areaName;
    public List<CitiesBean> cities;

    public static class CitiesBean {

        public String areaId;
        public String areaName;
        public List<CountiesBean> counties;
        public static class CountiesBean {

            public String areaId;
            public String areaName;

        }
    }
}
