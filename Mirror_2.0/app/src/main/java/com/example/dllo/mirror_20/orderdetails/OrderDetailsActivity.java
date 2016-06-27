package com.example.dllo.mirror_20.orderdetails;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/6/24.
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private TextView confirm, addAddress, goodsName, money, recipient, address, number, pleaseAdd;
    private ImageView imageView, back;
    private NetworkTools tools;
    private AutoLinearLayout layout;
    private String url = "http://api.mirroreye.cn/index.php/user/address_list";
    private Map<String,String> map;
    private AllMyAddressBean bean;


    @Override
    public void initActivity() {
        setContentView(R.layout.activity_order_details);
        tools = new NetworkTools();


        imageView = (ImageView) findViewById(R.id.order_details_goods_iv);
        back = (ImageView) findViewById(R.id.order_details_back_iv);
        addAddress = (TextView) findViewById(R.id.order_details_address_tv);
        confirm = (TextView) findViewById(R.id.order_details_confirm);
        goodsName = (TextView) findViewById(R.id.order_details_goods_name);
        money = (TextView) findViewById(R.id.order_details_goods_money);
        recipient = (TextView) findViewById(R.id.order_details_recipient);
        address = (TextView) findViewById(R.id.order_details_address);
        number = (TextView) findViewById(R.id.order_details_number);
        pleaseAdd = (TextView) findViewById(R.id.order_details_please_add);
        layout = (AutoLinearLayout) findViewById(R.id.order_details_layout_recipient_information);
        back.setOnClickListener(this);
        addAddress.setOnClickListener(this);
        confirm.setOnClickListener(this);
        initMap();//给map加数据

        tools.getNetworkPostData(url, map, new NetworkListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result,AllMyAddressBean.class);

               if (bean.getData().getList().toString().equals("[]")){
                   pleaseAdd.setVisibility(View.VISIBLE);
                   layout.setVisibility(View.GONE);
               }else {
                   pleaseAdd.setVisibility(View.GONE);
                   layout.setVisibility(View.VISIBLE);
                   addBean();

               }

            }

            @Override
            public void onFailed(VolleyError error) {
                pleaseAdd.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            }
        });


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_details_address_tv:
                Intent intent = new Intent(this, AllMyAddressActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.order_details_confirm:

                Toast.makeText(this, "这里即将弹出Dialog，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_details_back_iv:
                finish();
                break;


        }

    }

    //map中添加数据
    public void initMap(){
        SharedPreferences getSp = getSharedPreferences("test",MODE_PRIVATE);
        String token = getSp.getString("token",null);

        map = new HashMap<>();
        map.put("token",token);
        map.put("device_type","1");

    }
    public void addBean(){
        for (int i = 0; i < bean.getData().getList().size(); i++) {
            if (bean.getData().getList().get(i).getIf_moren().equals("1")) {
                recipient.setText("收件人:"+bean.getData().getList().get(i).getUsername());
                number.setText("电话: "+bean.getData().getList().get(i).getCellphone());
                address.setText("地址: "+bean.getData().getList().get(i).getAddr_info());
            }
        }
    }

}
