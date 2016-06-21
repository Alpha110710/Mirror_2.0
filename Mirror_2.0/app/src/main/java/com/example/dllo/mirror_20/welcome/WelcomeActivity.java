package com.example.dllo.mirror_20.welcome;

import android.content.Intent;
import android.os.CountDownTimer;

import com.example.dllo.mirror_20.main.MainActivity;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;

/**
 * Created by dllo on 16/6/20.
 */
public class WelcomeActivity extends BaseActivity {

    private CountDownTimer countDownTimer;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_welcome);

        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
