package com.example.dllo.mirror_20.login;


import android.content.Intent;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.networktools.URLValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by dllo on 16/6/20.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone, password, verification;
    private Button register;
    private TextView sendVerification;
    private ImageView close;
    private String phoneNumber = null;
    private NetworkTools networkTools;
    private CheckBox checkBox;
    private Boolean flag = true;


    @Override
    public void initActivity() {
        networkTools = new NetworkTools();

        setContentView(R.layout.activity_register);

        phone = (EditText) findViewById(R.id.activity_register_phoneNumber);
        password = (EditText) findViewById(R.id.activity_register_set_password);
        verification = (EditText) findViewById(R.id.activity_register_verification_code);

        close = (ImageView) findViewById(R.id.register_close);
        register = (Button) findViewById(R.id.register_create_id);
        sendVerification = (TextView) findViewById(R.id.register_send_verification);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);

        register.setOnClickListener(this);
        close.setOnClickListener(this);
        sendVerification.setOnClickListener(this);
        //checkBox选择是否显示密码
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
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_close:
                finish();
                break;
            case R.id.register_create_id:
                phoneNumber = phone.getText().toString();
                final String verifications = verification.getText().toString();
                final String passwords = password.getText().toString();
                if (phoneNumber.equals("")) {//手机号如果空的时候
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (verifications.equals("")) {//验证码为空
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else if (passwords.equals("")) {//密码为空
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("phone number", phoneNumber);
                    map1.put("number", verifications);
                    map1.put("password", passwords);
                    networkTools.getNetworkPostData(URLValue.REGISTER, map1, new NetworkListener() {
                        @Override
                        public void onSuccessed(String result) {
                           // Log.d("RegisterActivity", result.toString());
                            LoginBean loginBean = new LoginBean();
                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.has("result")) {
                                    loginBean.setResult(object.getString("result"));

                                    if (loginBean.getResult().equals("")) {
                                        if (object.has("msg")){
                                            loginBean.setMsg(object.getString("msg"));
                                            Toast.makeText(RegisterActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (loginBean.getResult().equals("1")) {
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        //在跳转时候把手机号和密码传走
                                        intent.putExtra("phoneNumber", phoneNumber);
                                        intent.putExtra("password", passwords);
                                        startActivity(intent);
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
                                        finish();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "注册失败,换个账号试试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.register_send_verification:
                phoneNumber = phone.getText().toString();
                if (phoneNumber.length() != 11) {//判断手机号长度
                    Toast.makeText(this, "手机号位数不够", Toast.LENGTH_SHORT).show();
                } else if (isMobile(phoneNumber) == false) {//判断是否手机号
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else if (flag == true) {//点进来时候判断是否 能够点击
                    flag=false;
                    sendVerification.setEnabled(false);//倒计时结束前不让点击
                    //按键背景颜色变灰
                    sendVerification.setBackgroundResource(R.color.light_grey);
                    //倒计时
                    final CountDownTimer timer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            //替换按钮上的字
                            sendVerification.setText(millisUntilFinished / 1000 + "秒重新发送");
                        }
                        @Override
                        public void onFinish() {//倒计时结束时候
                            //给按钮的字换回来
                            sendVerification.setText("发送验证码");
                            //背景也换回来
                            sendVerification.setBackgroundResource(R.mipmap.yanzhengcodepressbutton);
                            flag = true;//标记变为可用状态
                            sendVerification.setEnabled(true);//按钮可点击了
                        }
                    }.start();
                    Toast.makeText(this, "正在发送,就等一下", Toast.LENGTH_SHORT).show();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phone number", phoneNumber);
                    //网络请求
                    networkTools.getNetworkPostData(URLValue.VERIFICATION, map, new NetworkListener() {
                        @Override
                        public void onSuccessed(String result) {
                           // Log.d("RegisterActivity", result.toString());
                            LoginBean loginBean = new LoginBean();
                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.has("result")) {
                                    loginBean.setResult(object.getString("result"));
                                    if (loginBean.getResult().equals("")) {
                                        if (object.has("msg")){
                                            loginBean.setMsg(object.getString("msg"));
                                            Toast.makeText(RegisterActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (loginBean.getResult().equals("1")) {
                                        Toast.makeText(RegisterActivity.this, "发送成功,请查收信息", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailed(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "请求失败,请确认手机网络", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (flag == false) {
                    Toast.makeText(this, "倒计时结束再发送", Toast.LENGTH_SHORT).show();
                }
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

