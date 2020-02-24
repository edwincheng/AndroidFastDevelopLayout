package com.github.edwincheng.androidfastdeveloplayout.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.github.edwincheng.androidfastdeveloplayout.service.NetworkConnectChangedReceiver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-7 下午5:21
 * @ desc   : 应用工具类
 */
public class AppUtils {
    private static NetworkConnectChangedReceiver networkConnectChangedReceiver;
    private static boolean isRegister = false;

    //注册网络监听
    public static void registerReceiverNetwork(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        context.getApplicationContext().registerReceiver(networkConnectChangedReceiver, filter);
        isRegister = true;
    }

    public static void unregisterReceiverNetwork(Context context) {
        if (isRegister) {
            try {
                if (networkConnectChangedReceiver != null) {
                    context.getApplicationContext().unregisterReceiver(networkConnectChangedReceiver);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            isRegister = false;
        }
    }

    /**
     * 描述：判断网络有效.
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 检测是否有emoji表情
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 或者App版本号码
     * @param context
     * @return
     */
    public static String getAppInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
//            int versionCode = context.getPackageManager()
//                    .getPackageInfo(pkName, 0)versionCode;
            return "V " + versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 判断是否是Emoji
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }


    /**
     * 正确的输入：字母数字下划线组合,且第一个为英文
     * 检查账号是否含有非法字符
     *
     * @param checkString 待检测的字符创
     * @return
     */
    public static boolean checkUserAccountSymbol(String checkString) {
        // 除了字母数字下划线之外的字符为非法字符
        Pattern pattern = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9_]{0,}+$");
        // 指定设置非法字符
        Matcher matcher = pattern.matcher(checkString);
        if (matcher.matches()) {
            return true;
        } else
            return false;
    }


    /**
     * 判断字符串中是否包含中文字符
     *
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean containChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 验证ip是否合法
     *
     * @param text ip地址
     * @return 验证信息
     */
    public static boolean ipCheck(String text) {
        // 定义正则表达式
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        // 判断ip地址是否与正则表达式匹配
        return text.matches(regex);
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find())
                    return "";
                if (editText.getText().toString().length() >= 18)  //edittext maxlength属性无效，因为设置了过滤器，在这里需要重新设置
                    return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

}
