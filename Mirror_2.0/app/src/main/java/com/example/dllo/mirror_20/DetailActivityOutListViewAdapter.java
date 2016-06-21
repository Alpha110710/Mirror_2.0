package com.example.dllo.mirror_20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/21.
 */
public class DetailActivityOutListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> strs;

    public DetailActivityOutListViewAdapter(Context context) {
        this.context = context;
    }

    public void setStrs(ArrayList<String> strs) {
        this.strs = strs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strs == null ? 0 : strs.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailActivityOutListViewViewHolder viewViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_activity_out_listview, parent, false);
            viewViewHolder = new DetailActivityOutListViewViewHolder(convertView);
            convertView.setTag(viewViewHolder);
        }else {
            viewViewHolder = (DetailActivityOutListViewViewHolder) convertView.getTag();
        }

        viewViewHolder.itemDetailActivityOutListviewTv.setText("111");

        return convertView;
    }

    class DetailActivityOutListViewViewHolder {

        private final TextView itemDetailActivityOutListviewTv;

        public DetailActivityOutListViewViewHolder(View itemView) {
            itemDetailActivityOutListviewTv = (TextView) itemView.findViewById(R.id.item_detail_activity_out_listview_tv);


        }
    }
}
