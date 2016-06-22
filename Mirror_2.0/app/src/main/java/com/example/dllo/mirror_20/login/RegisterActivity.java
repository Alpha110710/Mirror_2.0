package com.example.dllo.mirror_20.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;


/**
 * Created by dllo on 16/6/20.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone,password,verification;
    private Button register,sendVerification;
    private ImageView close;
    private String phoneNumber;
    @Override
    public void initActivity() {
        setContentView(R.layout.activity_register);

        phone= (EditText) findViewById(R.id.activity_register_phoneNumber);
        password= (EditText) findViewById(R.id.activity_register_set_password);
        verification= (EditText) findViewById(R.id.activity_register_verification_code);

        close= (ImageView) findViewById(R.id.register_close);
        register= (Button) findViewById(R.id.register_create_id);
        sendVerification= (Button) findViewById(R.id.register_send_verification);

        register.setOnClickListener(this);
        close.setOnClickListener(this);
        sendVerification.setOnClickListener(this);

        phoneNumber=phone.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_close:
                finish();
                break;
            case R.id.register_create_id:
                final String verifications=verification.getText().toString();
                final String passwords=password.getText().toString();



                break;
            case R.id.register_send_verification:

                break;
        }
    }
}
