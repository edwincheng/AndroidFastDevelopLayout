package com.github.edwincheng.androidfastdeveloplayout.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.edwincheng.androidfastdeveloplayout.R;
import com.github.edwincheng.androidfastdeveloplayout.application.ProjectApplication;

public class ToastUtil {
    private static ToastUtil toastUtil;
    private static TextView textView;
    private static Toast toast;
    private boolean isShortFinish = true;
    private boolean isLongFinish = true;

    /**
     * 单例对象
     */
    public synchronized static ToastUtil getInstance() {
        if (toastUtil == null) {
            synchronized (ToastUtil.class) {
                if (toastUtil == null) {
                    toastUtil = new ToastUtil();

                    Context context = ProjectApplication.getmContext();
                    toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER, 0, 0);

                    textView = new TextView(context);
                    textView.setBackgroundResource(R.drawable.bg_toast);
                    textView.setGravity(Gravity.CENTER);
                    textView.setAlpha(0.9f);
                    textView.setPadding(ScreenUtil.dip2px(20),ScreenUtil.dip2px(10),ScreenUtil.dip2px(20),ScreenUtil.dip2px(10));
                    textView.setTextSize(ScreenUtil.sp2px(context, context.getResources().getDimension(R.dimen.text_Toast)));
                    textView.setTextColor(Color.WHITE);
                }
            }
        }
        return toastUtil;
    }


    public void showShort(String content){
        textView.setText(content);
        toast.setView(textView);
        toast.setDuration(Toast.LENGTH_SHORT);

        View view = toast.getView();
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.i("Toast","------onViewDetachedFromWindow");
                isShortFinish = true;
            }
            @Override
            public void onViewAttachedToWindow(View v) {

            }
        });

        if (isShortFinish) {
            toast.show();
            isShortFinish = false;
        }
    }


    public void showLong(String content){
        textView.setText(content);
        toast.setView(textView);
        toast.setDuration(Toast.LENGTH_LONG);

        View view = toast.getView();
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewDetachedFromWindow(View v) {
                isLongFinish = true;
            }
            @Override
            public void onViewAttachedToWindow(View v) {

            }
        });

        if (isLongFinish) {
            toast.show();
            isLongFinish = false;
        }
    }
}
