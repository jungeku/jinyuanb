package com.sk.xjwd.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/11/29.
 */

public class AssetsBankInfo {  //该方法用于打开assets中的binNum文档资源，获得里面的binNum数据
    private static String[] names;
    private static String[] nums;
    private static String openBinNum(Context context) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String str = null;
        try {
            InputStream is = context.getResources().getAssets().open("binNum.txt");
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            is.close();
            outputStream.close();
            str = outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getNameOfBank(Context context, String binNum) {

        parseTxt(context);
        String s = searchIndex(context, binNum);
        return s;
    }

    private static String searchIndex(Context context, String binNum) {
        if(nums!=null && nums.length>0) {
            for (int i = 0; i < nums.length; i++) {
                if(nums[i]!=null && nums[i].length()>0) {
                    if (binNum.startsWith(nums[i]))  return names[i];
                }
            }
        }
        return "";
    }


    private static void parseTxt(Context context) {
        String str = openBinNum(context);
        String[] split = str.split("\n");
        names=new String[split.length];
        nums=new String[split.length];
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split(":");
            nums[i]=split1[0].substring(1).substring(0,split1[0].length()-2);
            names[i]=split1[1].substring(1).substring(0,split1[1].length()-2);
        }
    }

    /**
     * 对银行卡号进行*号处理
     *
     * @param CardNumber
     * @return
     */
    public static String getCardNumber(String CardNumber) {
        String result;
        if (CardNumber.length() >= 16 && CardNumber.length() <= 19) {
            result = CardNumber.substring(CardNumber.length() - 4, CardNumber.length());
            return "**** **** **** *** " + result;
        } else if (CardNumber.length() <= 15 && CardNumber.length() >= 3) {
            result = CardNumber.substring(CardNumber.length() - 4, CardNumber.length());
            return "**** **** *** " + result;
        }
        return "";
    }
}