package com.luo.park.frame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luo.park.Constant;
import com.luo.park.R;
import com.luo.park.utils.Logger;

/**
 * BaseActivity
 * <p/>
 * Created by luoyingxing on 16/6/9.
 */
public class BaseActivity extends ActionBarActivity implements View.OnClickListener, BackHandledInterface {
    private BackHandledFragment mBackHandedFragment;
    protected String mTag;
    protected Logger mLog;
    protected ViewGroup mActionbarLayout;
    protected View mBackView;
    protected TextView mTitleView;
    protected ImageView mRightImage;
    protected TextView mRightText;
    protected CharSequence mTitle = "";

    View.OnClickListener mRightImageOnClickListener;
    View.OnClickListener mRightTextOnClickListener;

    protected int getLayout() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getClass().getSimpleName();
        mLog = new Logger(mTag, Log.VERBOSE);
        mLog.d("onCreate");
        //沉浸式状态栏 1/3
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(this, true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.theme_color);
//        }
        setContentView(getLayout());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_custom_view);
        Toolbar parent = (Toolbar) actionBar.getCustomView().getParent();
        parent.setContentInsetsAbsolute(0, 0);

        mActionbarLayout = (RelativeLayout) findViewById(R.id.actionbar_custom_view_container);

        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(R.string.app_name);

        mRightImage = (ImageView) findViewById(R.id.right_image);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setOnClickListener(this);

        mRightText = (TextView) findViewById(R.id.right_text);
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setOnClickListener(this);

        mBackView = findViewById(R.id.back);
        mBackView.setVisibility(View.VISIBLE);
        mBackView.setOnClickListener(this);
        mTitleView.setText(mTitle);
        String fragmentName = getIntent().getStringExtra(Constant.ARGS_FRAGMENT_NAME);
        switchFragment(fragmentName);
    }

    //沉浸式状态栏自定义颜色 2/3   加3/3 xmlandroid:clipToPadding="true"  android:fitsSystemWindows="true"
//    @TargetApi(19)
//    private static void setTranslucentStatus(Activity activity, boolean on) {
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mLog.d("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLog.d("onRestart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLog.d("onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
        mLog.d("onResume()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLog.d("onSaveInstanceState()");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
        mLog.d("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLog.d("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLog.d("onDestroy()");
    }

    public TextView getmTitleView() {
        return mTitleView;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle = title;
        mTitleView.setText(mTitle);
    }

    public ViewGroup getmActionbarLayout() {
        return mActionbarLayout;
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            goBack();
        }
    }

    public void switchFragment(String fragmentName) {
        if (fragmentName != null) {
            Fragment fragment = Fragment.instantiate(this, fragmentName, getIntent().getExtras());
            switchFragment(fragment);
        }
    }

    public void switchFragment(Fragment fragment) {
        switchFragment(fragment, false);
    }

    public void switchFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_right_in,
                R.anim.fragment_left_out,
                R.anim.fragment_left_in,
                R.anim.fragment_right_out
        );

        transaction.replace(R.id.container, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    public void goBack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public ViewGroup getActionbarLayout() {
        return mActionbarLayout;
    }

    public void showBackView(boolean visible) {
        mBackView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public View getBackView() {
        return mBackView;
    }

    public ImageView getRightImage() {
        return mRightImage;
    }

    public TextView getRightText() {
        return mRightText;
    }

    public void setOnRightImageClick(View.OnClickListener listener) {
        mRightImageOnClickListener = listener;
    }

    public void setOnRightTextClick(View.OnClickListener listener) {
        mRightTextOnClickListener = listener;
    }

    public void onRightImageClick() {
        if (mRightImageOnClickListener != null) {
            mRightImageOnClickListener.onClick(mRightImage);
        }
    }

    public void onRightTextClick() {
        if (mRightTextOnClickListener != null) {
            mRightTextOnClickListener.onClick(mRightText);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                goBack();
                break;
            case R.id.right_image:
                onRightImageClick();
                break;
            case R.id.right_text:
                onRightTextClick();
                break;
        }
    }

    protected void showToast(Object msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(Object msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_LONG).show();
    }
}
