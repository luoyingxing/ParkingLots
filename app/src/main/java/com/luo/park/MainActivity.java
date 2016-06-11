package com.luo.park;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luo.park.card.CardFragment;
import com.luo.park.entity.Project;
import com.luo.park.frame.BaseActivity;
import com.luo.park.scan.ScanFragment;


public class MainActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {
    private TextView mCurrentCountTV;
    private TextView mRemainCountTV;
    private GridView mGridView;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBackView.setVisibility(View.GONE);
        mTitleView.setText(getResources().getString(R.string.app_name));
        init();
    }

    private void init() {
        findView();
        initDate();
        setAdapter();
    }

    private void findView() {
        mCurrentCountTV = (TextView) findViewById(R.id.tv_home_current_car_count);
        mRemainCountTV = (TextView) findViewById(R.id.tv_home_remain_car_count);
        mGridView = (GridView) findViewById(R.id.gv_home_function);
    }

    private void initDate() {
        mCurrentCountTV.setText("88");
        mRemainCountTV.setText("22");
    }

    private void setAdapter() {
        MyAdapter mMyAdapter = new MyAdapter(MainActivity.this);
        mMyAdapter.addAll(Project.getProjectList());
        mGridView.setAdapter(mMyAdapter);
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag == null) {
            return;
        }
        Project item = (Project) tag;
        switch (item.getId()) {
            case 80001:
                Intent intent1 = new Intent(MainActivity.this, BaseActivity.class);
                intent1.putExtra(Constant.ARGS_FRAGMENT_NAME, ScanFragment.class.getName());
                startActivity(intent1);
                break;
            case 80002:
                break;
            case 80003:
                showToast("该功能暂未开放");
                break;
            case 80004:
                break;
            case 80005:
                Intent intent2 = new Intent(MainActivity.this, BaseActivity.class);
                intent2.putExtra(Constant.ARGS_FRAGMENT_NAME, CardFragment.class.getName());
                startActivity(intent2);
                break;
            case 80006:
                showToast("该功能暂未开放");
                break;
            default:
                break;
        }
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

    private class MyAdapter extends ArrayAdapter<Project> {

        public MyAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_gridview_item, null);
                viewHolder = new GridViewHolder();
                viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_main_item);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_main_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (GridViewHolder) convertView.getTag();
            }

            Project project = getItem(position);

            viewHolder.image.setImageResource(project.getImageId());
            viewHolder.title.setText(project.getTitle());
            viewHolder.image.setTag(project);
            viewHolder.image.setOnTouchListener(MainActivity.this);
            viewHolder.image.setOnClickListener(MainActivity.this);

            return convertView;
        }

        class GridViewHolder {
            ImageView image;
            TextView title;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            showToast("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

}
