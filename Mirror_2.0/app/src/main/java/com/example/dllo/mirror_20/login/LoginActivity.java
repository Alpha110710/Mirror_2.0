package com.example.dllo.mirror_20.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.App;
import com.example.dllo.mirror_20.base.BaseActivity;

/**
 * Created by dllo on 16/6/22.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneNumber,password;
    private Button login,register;
    private ImageView close;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_login);

        phoneNumber= (EditText) findViewById(R.id.activity_login_phoneNumber);
        password= (EditText) findViewById(R.id.activity_login_password);
        close= (ImageView) findViewById(R.id.login_close);
        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.login_register_id);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        close.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                final String phone=phoneNumber.getText().toString();
                final String passwords=password.getText().toString();


                break;
            case R.id.login_register_id:
                Intent intent=new Intent(App.context,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_close:
                finish();
                break;

        }
    }
}
