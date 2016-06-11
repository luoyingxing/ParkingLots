package com.luo.park.card;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.luo.park.R;
import com.luo.park.entity.CarInfo;
import com.luo.park.frame.BaseFragment;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * CardFragment
 * <p/>
 * Created by luoyingxing on 16/6/10.
 */
public class CardFragment extends BaseFragment implements View.OnTouchListener, View.OnClickListener {
    private View mRootView;
    private RadioGroup mRadioGroup;
    private RadioButton mSexManButton;
    private RadioButton mSexWomanButton;
    private EditText mCarNumberEd;
    private Spinner mCarTypeSpinner;
    private EditText mNameEd;
    private EditText mPhoneEd;
    private Spinner mCardSpinner;
    private EditText mChargeEd;
    private EditText mDepositEd;
    private Button mSubmitBtn;
    private TextView mStartTimeTV;
    private TextView mEndTimeTV;

    private FinalDb mFinalDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        }
        mRootView = inflater.inflate(R.layout.fragment_card, container, false);
        init();
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.setTitle("办卡登记");
    }

    private void init() {
        mFinalDb = FinalDb.create(getActivity());
        List<CarInfo> carInfoList = new ArrayList<>();
        carInfoList.addAll(mFinalDb.findAll(CarInfo.class));
        for (int i = 0; i < carInfoList.size(); i++) {
            mLog.e("第" + i + "个数据:" + carInfoList.get(i).getCarNumber());
        }
        findView();
        initDate();
        setOnListener();
    }

    private void findView() {
        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.rg_card_sex);
        mSexManButton = (RadioButton) mRootView.findViewById(R.id.rb_card_sex_man);
        mSexWomanButton = (RadioButton) mRootView.findViewById(R.id.rb_card_sex_woman);
        mCarNumberEd = (EditText) mRootView.findViewById(R.id.ed_card_car_number);
        mCarTypeSpinner = (Spinner) mRootView.findViewById(R.id.sp_card_type);
        mNameEd = (EditText) mRootView.findViewById(R.id.ed_card_name);
        mPhoneEd = (EditText) mRootView.findViewById(R.id.ed_card_phone);
        mCardSpinner = (Spinner) mRootView.findViewById(R.id.sp_card_card_type);
        mChargeEd = (EditText) mRootView.findViewById(R.id.ed_card_charge);
        mDepositEd = (EditText) mRootView.findViewById(R.id.ed_card_deposit);
        mStartTimeTV = (TextView) mRootView.findViewById(R.id.tv_card_start_time);
        mEndTimeTV = (TextView) mRootView.findViewById(R.id.tv_card_end_time);
        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_card_submit);
    }

    private static final String[] mCarTypeString = {"小轿车", "面包车", "货车"};
    private ArrayAdapter<String> mCarTypeAdapter;

    private static final String[] mCardTypeString = {"月卡", "年卡", "临时卡"};
    private ArrayAdapter<String> mCardAdapter;

    private String mSex = "男士";
    private String mCarType = "";
    private String mCardType = "";

    private void initDate() {
        mSexManButton.setChecked(true);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_card_sex_man:
                        mSex = "男士";
                        break;
                    case R.id.rb_card_sex_woman:
                        mSex = "女士";
                        break;
                }
            }
        });

        mCarTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mCarTypeString);
        mCarTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCarTypeSpinner.setAdapter(mCarTypeAdapter);
        mCarTypeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mLog.e("您选择的是：" + mCarTypeAdapter.getItem(arg2));
                mCarType = mCarTypeAdapter.getItem(arg2);
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });

        mCardAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mCardTypeString);
        mCardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCardSpinner.setAdapter(mCardAdapter);
        mCardSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mLog.e("您选择的是：" + mCardAdapter.getItem(arg2));
                mCardType = mCardAdapter.getItem(arg2);
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOnListener() {
        mStartTimeTV.setOnClickListener(CardFragment.this);
        mEndTimeTV.setOnClickListener(CardFragment.this);
        mSubmitBtn.setOnClickListener(CardFragment.this);
        mSubmitBtn.setOnTouchListener(CardFragment.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_card_start_time:
                getTime(TIME_START);
                break;
            case R.id.tv_card_end_time:
                getTime(TIME_END);
                break;
            case R.id.btn_card_submit:
                submitData();
                break;
        }
    }

    private Calendar calendar = null;
    private Dialog dialog = null;
    private static final int TIME_START = 1;
    private static final int TIME_END = 2;
    private String mStartTime = "";
    private String mEndTime = "";

    private void getTime(final int time) {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        mLog.e("您选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                        switch (time) {
                            case TIME_START:
                                mStartTimeTV.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                mStartTime = year + "-" + (month + 1) + "-" + dayOfMonth;
                                break;
                            case TIME_END:
                                mEndTimeTV.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                mEndTime = year + "-" + (month + 1) + "-" + dayOfMonth;
                                break;
                        }
                    }
                },
                calendar.get(Calendar.YEAR), // 传入年份
                calendar.get(Calendar.MONTH), // 传入月份
                calendar.get(Calendar.DAY_OF_MONTH) // 传入天数
        );
        dialog.show();
    }

    private void submitData() {
        mLog.e(mSex);
        mLog.e(mCarType + "/" + mCardType);
        mLog.e(mStartTime + "/" + mEndTime);

        String carName = mCarNumberEd.getText().toString().trim();
        String name = mNameEd.getText().toString().trim();
        String phone = mPhoneEd.getText().toString().trim();
        float deposit = mDepositEd.getText().toString().isEmpty() ? 0f : Float.valueOf(mDepositEd.getText().toString().trim());
        float charge = mChargeEd.getText().toString().isEmpty() ? 0f : Float.valueOf(mChargeEd.getText().toString().trim());

        if (carName.isEmpty()) {
            showToast("请输入车牌号码!");
        } else if (name.isEmpty()) {
            showToast("请输入车主姓名!");
        } else if (phone.isEmpty()) {
            showToast("请输入联系电话!");
        } else if (mStartTime.isEmpty()) {
            showToast("请选择办卡日期!");
        } else if (mEndTime.isEmpty()) {
            showToast("请选择办卡日期!");
        } else {
            CarInfo carInfo = new CarInfo();
            carInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            carInfo.setCarNumber(carName);
            carInfo.setCarType(mCarType);
            carInfo.setSex(mSex);
            carInfo.setName(name);
            carInfo.setPhone(phone);
            carInfo.setCardType(mCardType);
            carInfo.setDeposit(deposit);
            carInfo.setCharge(charge);
            carInfo.setCardStartTime(mStartTime);
            carInfo.setCardEndTime(mEndTime);

            mLog.e(carInfo.toString());
            commitCarInfo(carInfo);
        }
    }

    private void commitCarInfo(CarInfo carInfo) {
        mFinalDb.save(carInfo);
        showToast("添加成功!");
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
