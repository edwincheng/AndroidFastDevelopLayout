package com.github.edwincheng.androidfastdeveloplayout.utils;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by zhengwp on 19-2-22.
 * <p>
 * Activity管理
 */
public class ActivityManageUtil {
    private static Map<String, Activity> activityMap = new HashMap<>();
    private static Activity mainActivity;

    public static Activity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(Activity activity) {
        ActivityManageUtil.mainActivity = activity;
    }

    public static void insertActivity(String aName, Activity activity) {
        activityMap.put(aName, activity);
    }
    public static Activity getActivity(String aName){
         return activityMap.get(aName);
    }

    public static void deleteActivity(String aName) {
        activityMap.remove(aName);
    }

    /** 清空栈内的上下文对象 */
    public static void delAllActivity() {
        for (Map.Entry<String, Activity> entry : activityMap.entrySet()) {
            entry.getValue().finish();
        }
        activityMap.clear();
    }
}
