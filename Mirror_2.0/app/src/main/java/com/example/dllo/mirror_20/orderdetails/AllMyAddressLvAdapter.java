package com.example.dllo.mirror_20.orderdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror_20.R;

import java.util.List;

/**
 * Created by dllo on 16/6/25.
 */
public class AllMyAddressLvAdapter extends BaseAdapter {
    private List<AllMyAddressBean.DataBean.ListBean> list;
    private Context context;

    public AllMyAddressLvAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AllMyAddressBean.DataBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AllMyAddressViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_all_my_address_activity_list_view, parent, false);
            viewHolder = new AllMyAddressViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AllMyAddressViewHolder) convertView.getTag();
        }
        viewHolder.recipient.setText(list.get(position).getUsername());
        viewHolder.address.setText(list.get(position).getAddr_info());
        viewHolder.number.setText(list.get(position).getCellphone());
        viewHolder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddOrModifyAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("listBean",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });


        return convertView;
    }


    class AllMyAddressViewHolder {
        TextView recipient, address, number, delete;
        ImageView modify;


        public AllMyAddressViewHolder(View view) {
            modify = (ImageView) view.findViewById(R.id.all_my_address_lv_title_modify);
            recipient = (TextView) view.findViewById(R.id.all_my_address_lv_title_recipient);
            address = (TextView) view.findViewById(R.id.all_my_address_lv_title_address);
            number = (TextView) view.findViewById(R.id.all_my_address_lv_title_number);
            delete = (TextView) view.findViewById(R.id.all_my_address_lv_title_delete);
        }
    }
}
