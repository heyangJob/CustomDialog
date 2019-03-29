package com.dhcc.dialog.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dhcc.dialog.R;
import com.dhcc.dialog.custom.CustomerKeyboard;
import com.dhcc.dialog.dialog.CommonDialog;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CustomerKeyboard
        .CustomerKeyboardClickListener {

    private MyHandler mHandler;

    private static final int MESSAGE = 0x111;
    private static final String TAG = MainActivity.class.getSimpleName();

    private CustomerKeyboard mCustomerKeyboard;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton = findViewById(R.id.btn);
        mButton.setOnClickListener(this);


    }

    public void showDialog() {

        // 弹出dialog  从底部并且带动画
        CommonDialog.Builder builder = new CommonDialog.Builder(this);
        builder.setContentView(R.layout.dialog_customer_keyboard).fromBottom(true).fullWidth().create();
        builder.show();

        mCustomerKeyboard = builder.getView(R.id.custom_key_board);
        mCustomerKeyboard.setOnCustomerKeyboardClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MainActivity.MESSAGE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                mHandler = new MyHandler(MainActivity.this);
                mHandler.sendEmptyMessage(MainActivity.MESSAGE);
                break;
        }
    }

    @Override
    public void click(String number) {
        Log.e(TAG, "number====" + number);
    }

    @Override
    public void delete() {

    }

    static class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) mWeakReference.get();
            if (activity != null) {
                if (msg.what == MainActivity.MESSAGE) {
                    activity.showDialog();
                }
            }
        }
    }
}
