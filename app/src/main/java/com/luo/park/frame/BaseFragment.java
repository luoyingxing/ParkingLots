package com.luo.park.frame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.luo.park.MainApplication;
import com.luo.park.R;
import com.luo.park.frame.network.MyVolley;
import com.luo.park.utils.Logger;
import com.luo.park.utils.NetworkUtils;

/**
 * BaseFragment
 * <p/>
 * Created by luoyingxing on 16/6/9.
 */
public class BaseFragment extends BackHandledFragment {
    protected String mTag;
    protected Logger mLog;
    protected BaseActivity mActivity;
    protected ProgressDialog mDialog;

    @Override
    public void onAttach(Activity activity) {
        mTag = getClass().getSimpleName();
        mLog = new Logger(mTag, Log.VERBOSE);
        mLog.v("OnAttach()");
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLog.v("onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLog.v("onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mLog.v("onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        mActivity.setTitle(getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        mLog.v("onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        mLog.v("onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        mLog.v("onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        mLog.v("onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mLog.v("onDestroyView()");
        super.onDestroyView();
        hideIME();
        hideDialog();
        MyVolley.getInstance().getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        mActivity.getRightImage().setImageDrawable(null);
        mActivity.setOnRightImageClick(null);
        mActivity.getRightText().setText("");
        mActivity.setOnRightTextClick(null);
    }

    @Override
    public void onDestroy() {
        mLog.v("onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mLog.v("onDetach()");
        super.onDetach();
    }

    /**
     * 子类可重载该方法,以监听物理返回键.
     *
     * @return 如果希望覆盖默认实现, 则返回true
     */
    public boolean onBackPressed() {
        return false;
    }

    private Toast toast = null;

    protected void showToast(Object msg) {
        if (toast != null) {
            toast.cancel();
            toast = Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT);
        } else
            toast = Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void showLongToast(Object msg) {
        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_LONG).show();
    }

    public void showNetworkError() {
        showToast(getResources().getString(R.string.msg_network_error));
    }

    public void showDialog() {
        showDialog(getResources().getString(R.string.msg_network_loading));
    }

    public void showDialog(String message) {
        showDialog(null, message);
    }

    public void showDialog(String title, String message) {
        hideDialog();
        mDialog = ProgressDialog.show(getActivity(), title, message);
        mDialog.setCanceledOnTouchOutside(true);
    }

    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void showIME() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getActivity().getCurrentFocus(), 0);
        mLog.d("showIME()");
    }

    public void showIMEForce(View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                showIME();
            }
        }, 100);
    }


    public void hideIME() {
        mLog.d("inputMode=" + getActivity().getWindow().getAttributes().softInputMode);
        if (getActivity().getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            return;
        }
        mLog.d("hideIME()");
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            mLog.d("hideIME() failed");
        }
    }

    public boolean checkNetwork() {
        if (NetworkUtils.isNetConnected()) {
            return true;
        }
        showNetworkError();
        return false;
    }

    /**
     * @return 已登录返回true
     */
    public boolean checkLogin() {
        String cookie = MainApplication.getApp().getLoginCookie();
        return cookie != null && !cookie.isEmpty();
    }

    /**
     * @return 已登录返回true, 如果未登录，且开启autoJump，将自动跳转至登录界面
     */
    public boolean checkLogin(boolean autoJump) {
        if (!checkLogin()) {
            if (autoJump) {
//                Intent loginIntent = new Intent(getActivity(), BaseActivity.class);
//                loginIntent.putExtra(Constant.ARGS_FRAGMENT_NAME, LoginFragment.class.getName());
//                getActivity().startActivity(loginIntent);
            }
            return false;
        }
        return true;
    }

    public void savePref(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit().putString(key, value).commit();
    }

    public String getPref(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(key, "");
    }

}
