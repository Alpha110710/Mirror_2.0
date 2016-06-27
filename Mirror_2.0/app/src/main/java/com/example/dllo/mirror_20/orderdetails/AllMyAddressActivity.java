package com.example.dllo.mirror_20.orderdetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dllo on 16/6/24.
 */
public class AllMyAddressActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private TextView addAddress;
    private ImageView back;
    private ListView listView;
    private AllMyAddressLvAdapter adapter;
    private NetworkTools tools;
    private String url = "http://api.mirroreye.cn/index.php/user/address_list";
    private Map<String,String> map;
    private AllMyAddressBean bean;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_all_my_address);
        addAddress = (TextView) findViewById(R.id.all_my_address_add_address_tv);
        back = (ImageView) findViewById(R.id.all_my_address_back_iv);
        listView = (ListView) findViewById(R.id.all_my_address_list_view);

        back.setOnClickListener(this);
        addAddress.setOnClickListener(this);

        adapter = new AllMyAddressLvAdapter(this);
        tools = new NetworkTools();
        initMap();

        tools.getNetworkPostData(url, map, new NetworkListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result,AllMyAddressBean.class);
                if (!bean.getData().getList().toString().equals("[]")) {
                    adapter.setList(bean.getData().getList());
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_my_address_back_iv:
                Intent intent = new Intent(this,OrderDetailsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.all_my_address_add_address_tv:
                Intent addIntent = new Intent(this,AddOrModifyAddressActivity.class);
                startActivity(addIntent);
                finish();
                break;
        }

    }

    //ListView的短点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,OrderDetailsActivity.class);
        startActivity(intent);
        initDefault(position);

    }
    //map中添加数据
    public void initMap(){
        SharedPreferences getSp = getSharedPreferences("test",MODE_PRIVATE);
        String token = getSp.getString("token",null);
        map = new HashMap<>();
        map.put("token",token);
        map.put("device_type","1");

    }


    public void initDefault(int pos){
        SharedPreferences getSp = getSharedPreferences("test",MODE_PRIVATE);
        String token = getSp.getString("token",null);
        String addUrl = "http://api.mirroreye.cn/index.php/user/mr_address";
        Map<String,String> addMap = new HashMap<>();
        addMap.put("token",token);
        addMap.put("addr_id",bean.getData().getList().get(pos).getAddr_id());


        tools.getNetworkPostData(addUrl, addMap, new NetworkListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                SuccessfulOrFailureBean bean = gson.fromJson(result,SuccessfulOrFailureBean.class);
                if (bean.getResult().equals("1")){
                    Toast.makeText(AllMyAddressActivity.this, "添加默认地址成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AllMyAddressActivity.this, "添加默认地址失败", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailed(VolleyError error) {
                Toast.makeText(AllMyAddressActivity.this, "添加默认地址失败", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        deleteData(position);
        return true;
    }


    public void deleteData(final int pos){
        final SharedPreferences getSp = getSharedPreferences("test",MODE_PRIVATE);
        String token = getSp.getString("token",null);
        String deleteUrl = "http://api.mirroreye.cn/index.php/user/del_address";
        Map<String,String> deleteMap = new HashMap<>();
        deleteMap.put("token",token);
        deleteMap.put("addr_id",bean.getData().getList().get(pos).getAddr_id());

        tools.getNetworkPostData(deleteUrl, deleteMap, new NetworkListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                SuccessfulOrFailureBean bean1 = gson.fromJson(result,SuccessfulOrFailureBean.class);
                if (bean1.getResult().equals("1")) {
                    Toast.makeText(AllMyAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    List<AllMyAddressBean.DataBean.ListBean> data = bean.getData().getList();
                    data.remove(pos);
                    adapter.setList(data);
                    listView.setAdapter(adapter);
                }else {
                    Toast.makeText(AllMyAddressActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                Toast.makeText(AllMyAddressActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
