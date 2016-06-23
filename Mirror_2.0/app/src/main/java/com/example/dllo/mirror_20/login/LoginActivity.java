package com.example.dllo.mirror_20.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.App;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.networktools.URLValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dllo on 16/6/22.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneNumber, password;
    private Button login, register;
    private ImageView close;
    private String phone;
    private String passwords;
    private CheckBox checkBox;
    private NetworkTools networkTools;
    private Boolean flag;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_login);

        phoneNumber = (EditText) findViewById(R.id.activity_login_phoneNumber);
        password = (EditText) findViewById(R.id.activity_login_password);
        close = (ImageView) findViewById(R.id.login_close);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.login_register_id);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        login.setOnClickListener(this);
        login.setEnabled(false);
        register.setOnClickListener(this);
        close.setOnClickListener(this);
        networkTools = new NetworkTools();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phoneNumber");
        passwords = intent.getStringExtra("password");
        phoneNumber.setText(phone);
        password.setText(passwords);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    login.setBackgroundResource(R.mipmap.createuserbuttton);
                    login.setEnabled(true);
                } else {
                    login.setBackgroundResource(R.mipmap.unusedbutton);
                    login.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                phone = phoneNumber.getText().toString();
                passwords = password.getText().toString();
                // login.setEnabled(false);
                if (phoneNumber.length() != 11) {
                    Toast.makeText(this, "手机号位数不够", Toast.LENGTH_SHORT).show();
                } else if (isMobile(phone) == false) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else if (!passwords.equals("")) {
                    login.setEnabled(true);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phone_number", phone);
                    map.put("password", passwords);
                    networkTools.getNetworkPostData(URLValue.LOGIN, map, new NetworkListener() {
                        @Override
                        public void onSuccessed(String result) {
                            LoginBean loginBean = new LoginBean();
                           // Log.d("LoginActivity", result.toString());

                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.has("result")) {
                                    loginBean.setResult(object.getString("result"));

                                    if (loginBean.getResult().equals("")) {
                                        if (object.has("msg")){
                                            loginBean.setMsg(object.getString("msg"));
                                            Toast.makeText(LoginActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (loginBean.getResult().equals("1")) {

                                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                        if (object.has("data")){
                                            LoginBean.DataBean dataBean = new LoginBean.DataBean();
                                            JSONObject object1 = new JSONObject(object.getString("data"));
                                            if (object1.has("token")){
                                                dataBean.setToken(object1.getString("token"));
                                            }
                                            if (object1.has("uid")){
                                                dataBean.setUid(object1.getString("uid"));
                                            }
                                            Log.d("LoginActivity~~~~~~", dataBean.getToken());
                                            Log.d("LoginActivity!!!!!!", dataBean.getUid());
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }

                        @Override
                        public void onFailed(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.login_register_id:
                Intent intent = new Intent(App.context, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_close:
                finish();
                break;

        }
    }

    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
