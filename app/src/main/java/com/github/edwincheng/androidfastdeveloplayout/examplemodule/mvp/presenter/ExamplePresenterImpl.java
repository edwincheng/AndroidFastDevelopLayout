package com.github.edwincheng.androidfastdeveloplayout.examplemodule.mvp.presenter;

import android.content.Context;

import com.github.edwincheng.androidfastdeveloplayout.base.mvp.BasePresenter;
import com.github.edwincheng.androidfastdeveloplayout.examplemodule.mvp.contract.ExampleContract;
import com.github.edwincheng.androidfastdeveloplayout.examplemodule.mvp.model.ExampleModelImpl;
import org.greenrobot.eventbus.EventBus;

public class ExamplePresenterImpl extends BasePresenter<ExampleContract.ExampleView, ExampleContract.ExampleModel>
        implements ExampleContract.ExamplePresenter {
    /** 上下文 */
    private Context mContext;

    public ExamplePresenterImpl(ExampleContract.ExampleView view) {
        super(view);
        EventBus.getDefault().register(this);
        mContext = getView().getActivity();
        mModel = new ExampleModelImpl();
    }

    ///////////// getter and setter  ////////////
    /**
     * get the activity context
     * @return
     */
    public Context getmContext() {
        return mContext;
    }




}
