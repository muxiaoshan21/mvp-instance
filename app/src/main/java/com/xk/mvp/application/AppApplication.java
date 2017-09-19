package com.xk.mvp.application;

import com.socks.library.KLog;
import com.xk.mvp.BuildConfig;

import org.litepal.LitePalApplication;

/**
 * Created by xk on 2017/9/19.
 */

public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //第三方SDK的初始化
        KLog.init(BuildConfig.DEBUG);//初始化KLog
        LitePalApplication.initialize(getApplicationContext());//初始化litePal
    }

}
