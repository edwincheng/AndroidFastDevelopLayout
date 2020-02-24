package com.github.edwincheng.androidfastdeveloplayout.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.github.edwincheng.androidfastdeveloplayout.application.ProjectApplication;

public class AppDataCache {
    private static Context mContext;
    private static SharedPreferences mSharePreferences;
    public static AppDataCache sharedPreferencesUtil;


    public static AppDataCache getInstance(){
        if (sharedPreferencesUtil == null){
            synchronized (AppDataCache.class){
                if (sharedPreferencesUtil == null){
                    sharedPreferencesUtil = new AppDataCache();
                    mSharePreferences = PreferenceManager.getDefaultSharedPreferences(ProjectApplication.getmContext());
                }
            }
        }
        return sharedPreferencesUtil;
    }

    public AppDataCache() {
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key){
        return mSharePreferences.getInt(key, 0);
    }

    public int getInt(String key, int value){
        return mSharePreferences.getInt(key, value);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key){
        return mSharePreferences.getString(key, "");
    }

    public long getLong(String key){
        return mSharePreferences.getLong(key, 0L);
    }

    public void putLong(String key, long value){
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * remove some data.
     * @param key
     */
    public void removekey(String key){
        if (getString(key) != null && !getString(key).equals("")) {
            SharedPreferences.Editor editor = mSharePreferences.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public void clear() {
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.clear();
        editor.apply();
    }

}
