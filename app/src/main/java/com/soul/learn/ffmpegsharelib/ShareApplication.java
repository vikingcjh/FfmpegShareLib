package com.soul.learn.ffmpegsharelib;

import android.app.Application;

/**
 * Created by cjh on 2017/8/21.
 */

public class ShareApplication extends Application {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
