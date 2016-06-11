package com.luo.park.scan;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luo.park.R;
import com.luo.park.frame.BaseFragment;

/**
 * ScanFragment
 * <p/>
 * Created by luoyingxing on 16/6/9.
 */
public class ScanFragment extends BaseFragment implements View.OnTouchListener, View.OnClickListener {
    private View mRootView;
    private LinearLayout mCarInfoLayout;
    private LinearLayout mCardInfoLayout;
    private TextView mIntTV;
    private TextView mOutTV;
    private TextView mTimeoutTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        }
        mRootView = inflater.inflate(R.layout.fragment_scan, container, false);
        init();
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.setTitle("扫一扫");
        init();
    }

    private void init() {
        findView();
        setAdapter();
    }

    private void findView() {
        mCarInfoLayout = (LinearLayout) mRootView.findViewById(R.id.ll_scan_car_info);
        mCardInfoLayout = (LinearLayout) mRootView.findViewById(R.id.ll_scan_card_info);
        mIntTV = (TextView) mRootView.findViewById(R.id.tv_scan_in);
        mOutTV = (TextView) mRootView.findViewById(R.id.tv_scan_out);
        mTimeoutTV = (TextView) mRootView.findViewById(R.id.tv_scan_timeout);
    }

    private void setAdapter() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.92f, 1.0f);
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.92f, 1.0f);
                ObjectAnimator.ofPropertyValuesHolder(v, pvhX, pvhY).setDuration(200).start();
                break;
        }
        return false;
    }


}