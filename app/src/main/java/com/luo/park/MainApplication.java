package com.luo.park;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.luo.park.utils.FileUtils;
import com.luo.park.utils.Logger;


/**
 * MainApplication
 * <p/>
 * Created by luoyingxing on 16/1/19.
 */
public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();
    private static final Logger mLog = new Logger(TAG, Log.VERBOSE);
    private static MainApplication mApp;
    private String mLoginCookie;


    public static Context getAppContext() {
        return mApp;
    }

    public static MainApplication getApp() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        Fresco.initialize(mApp);
//        MobclickAgent.setCatchUncaughtExceptions(false);
    }

    public String getLoginCookie() {
        if (mLoginCookie == null) {
            mLoginCookie = FileUtils.getPref(Constant.PREFS_LOGIN_COOKIE);
        }
        return mLoginCookie;
    }


    public void setLoginCookie(String loginCookie) {
        this.mLoginCookie = loginCookie;
        if (loginCookie == null) {
            FileUtils.removePref(Constant.PREFS_LOGIN_COOKIE);
        } else {
            FileUtils.savePref(Constant.PREFS_LOGIN_COOKIE, loginCookie);
        }
    }

}