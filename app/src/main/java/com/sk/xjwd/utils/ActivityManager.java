package com.sk.xjwd.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by LiJiangLong on 2017/8/7.
 */

public class ActivityManager{

    private static final String TAG = "ActivityManager";
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;
    private Activity currActivity;
    private ActivityManager(){
    }

    public static ActivityManager getActivityManager(){
        if(instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }

    //退出栈顶Activity
    public void popActivity(Activity activity){
        if(activity == null || activityStack == null){
            return;
        }
        if(activityStack.contains(activity)){
            activityStack.remove(activity);
        }
        currActivity = activity;
        //activity.finish;

    }

    public void destoryActivity(Activity activity){
        if(activity == null){
            return;
        }
        activity.finish();
        if(activityStack.contains(activity)){
            activityStack.remove(activity);
        }
        activity=null;
    }

    //获得当前栈顶Activity
    public Activity currentActivity(){
        if(activityStack == null||activityStack.empty()){
            return null;
        }
        return activityStack.lastElement();
    }

    //将当前Activity推入栈中
    public void pushActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    //退出栈中除指定的Activity外所有
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null)
                break;
            if (activity.getClass().equals(cls))
                break;
            destoryActivity(activity);
        }
    }

    //是否存在某一个activity
    public boolean haveAativity(Class cls){
        Activity activity = currentActivity();
        if (activity.getClass().equals(cls))
            return true;
        return false;
    }

        //退出栈中所有Activity
    public void popAllActivity(){
        popAllActivityExceptOne(null);
    }

    public void popAllActivity(Class mclass){
        popAllActivityExceptOne(mclass);
    }

    public Activity getCurrentActivity(){
        return currActivity;
    }

    public int getActivityStackSize(){
        int size = 0;
        if(activityStack != null){
            size = activityStack.size();
        }
        return size;
    }

}
