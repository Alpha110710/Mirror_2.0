package com.example.dllo.mirror_20.orderdetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/6/25.
 */
public class AddOrModifyAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText userName, userNumber, userAddress;
    private TextView titleName, confirm;
    private ImageView back;
    private NetworkTools tools;
    private AllMyAddressBean.DataBean.ListBean bean;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_add_or_modify_address);
        tools = new NetworkTools();//初始化工具类

        userName = (EditText) findViewById(R.id.add_or_modify_user_name);
        userNumber = (EditText) findViewById(R.id.add_or_modify_user_number);
        userAddress = (EditText) findViewById(R.id.add_or_modify_user_address);
        titleName = (TextView) findViewById(R.id.add_or_modify_name_tv);
        confirm = (TextView) findViewById(R.id.add_or_modify_confirm);
        back = (ImageView) findViewById(R.id.add_or_modify_back_iv);

        bean = getIntent().getParcelableExtra("listBean");

        if (bean == null) {
            titleName.setText("添加地址");
            confirm.setText("提交地址");
        } else {
            modifyData();
        }


        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_or_modify_back_iv:
                if (confirm.getText().toString().equals("提交地址")) {
                    Intent intent = new Intent(this, AllMyAddressActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, OrderDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.add_or_modify_confirm:
                if (confirm.getText().toString().equals("提交地址")) {
                    addAddress();
                } else {
                    modifyAddress();
                }

                break;
        }
    }

    public void addAddress() {
        if (userName.getText().length() != 0 && userNumber.getText().length() != 0
                && userAddress.getText().length() != 0) {

            SharedPreferences getSp = getSharedPreferences("test", MODE_PRIVATE);
            String token = getSp.getString("token", null);
            String addUrl = "http://api.mirroreye.cn/index.php/user/add_address";
            Map<String, String> addMap = new HashMap<>();
            addMap.put("token", token);
            addMap.put("username", userName.getText().toString());
            addMap.put("cellphone", userNumber.getText().toString());
            addMap.put("addr_info", userAddress.getText().toString());


            tools.getNetworkPostData(addUrl, addMap, new NetworkListener() {
                @Override
                public void onSuccessed(String result) {
                    Gson gson = new Gson();
                    SuccessfulOrFailureBean bean = gson.fromJson(result, SuccessfulOrFailureBean.class);
                    if (bean.getResult().equals("1")) {
                        Toast.makeText(AddOrModifyAddressActivity.this, "添加地址成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddOrModifyAddressActivity.this, AllMyAddressActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddOrModifyAddressActivity.this, "添加地址失败", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailed(VolleyError error) {
                    Toast.makeText(AddOrModifyAddressActivity.this, "发送添加地址失败", Toast.LENGTH_SHORT).show();

                }

            });

        } else {
            if (userName.getText().toString().isEmpty()) {
                Toast.makeText(this, "联系人姓名不能为空", Toast.LENGTH_SHORT).show();
            } else if (userNumber.getText().length() == 0) {
                Toast.makeText(this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
            } else if (userAddress.getText().length() == 0) {
                Toast.makeText(this, "联系地址不能为空", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void modifyAddress() {
        if (userName.getText().length() != 0 || userNumber.getText().length() != 0
                || userAddress.getText().length() != 0) {
            SharedPreferences getSp = getSharedPreferences("test", MODE_PRIVATE);
            String token = getSp.getString("token", null);
            String addUrl = "http://api.mirroreye.cn/index.php/user/edit_address";
            Map<String, String> modifyMap = new HashMap<>();
            modifyMap.put("token", token);
            modifyMap.put("addr_id", bean.getAddr_id());
            modifyMap.put("username", userName.getText().toString());
            modifyMap.put("cellphone", userNumber.getText().toString());
            modifyMap.put("addr_info", userAddress.getText().toString());


            tools.getNetworkPostData(addUrl, modifyMap, new NetworkListener() {
                @Override
                public void onSuccessed(String result) {
                    Gson gson = new Gson();
                    SuccessfulOrFailureBean bean = gson.fromJson(result, SuccessfulOrFailureBean.class);
                    if (bean.getResult().equals("1")) {
                        Toast.makeText(AddOrModifyAddressActivity.this, "修改地址成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddOrModifyAddressActivity.this, AllMyAddressActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddOrModifyAddressActivity.this, "修改地址失败", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailed(VolleyError error) {
                    Toast.makeText(AddOrModifyAddressActivity.this, "发送添加地址失败", Toast.LENGTH_SHORT).show();

                }

            });

        }else {
            Toast.makeText(this, "必须有一项不为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void modifyData() {
        titleName.setText("修改地址");
        confirm.setText("修改地址");
        userName.setText(bean.getUsername());
        userNumber.setText(bean.getCellphone());
        userAddress.setText(bean.getAddr_info());
    }
}
