package com.example.dllo.mirror_20.allcategories;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class DetailActivityOutListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> strs;
    List<DataAllBean.DataBean.ListBean.DataInfoBean.GoodsDataBean> goodsDataBeans;

    public DetailActivityOutListViewAdapter(Context context) {
        this.context = context;
    }

    public void setGoodsDataBeans(List<DataAllBean.DataBean.ListBean.DataInfoBean.GoodsDataBean> goodsDataBeans) {
        this.goodsDataBeans = goodsDataBeans;
        notifyDataSetChanged();
    }

    public void setStrs(ArrayList<String> strs) {
        this.strs = strs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return goodsDataBeans == null ? 0 : goodsDataBeans.size();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_activity_out_listview, parent, false);
            viewViewHolder = new DetailActivityOutListViewViewHolder(convertView);
            convertView.setTag(viewViewHolder);
        } else {
            viewViewHolder = (DetailActivityOutListViewViewHolder) convertView.getTag();
        }


        viewViewHolder.itemDetailActivityOutIntroContentTv.setText(goodsDataBeans.get(position).getIntroContent());
        //TODO:
        viewViewHolder.itemDetailActivityOutCountryTv.setText("韓國");
        viewViewHolder.itemDetailActivityOutEnglishTv.setText(goodsDataBeans.get(position).getEnglish());
        viewViewHolder.itemDetailActivityOutLocationTv.setText("來自時尚之都韓國");

        return convertView;
    }

    class DetailActivityOutListViewViewHolder {
        private final TextView itemDetailActivityOutIntroContentTv;
        private final TextView itemDetailActivityOutLocationTv;
        private final TextView itemDetailActivityOutEnglishTv;
        private final TextView itemDetailActivityOutCountryTv;


        public DetailActivityOutListViewViewHolder(View itemView) {
            itemDetailActivityOutIntroContentTv = (TextView) itemView.findViewById(R.id.item_detail_activity_out_intro_content_tv);
            itemDetailActivityOutLocationTv = (TextView) itemView.findViewById(R.id.item_detail_activity_out_location_tv);
            itemDetailActivityOutEnglishTv = (TextView) itemView.findViewById(R.id.item_detail_activity_out_english_tv);
            itemDetailActivityOutCountryTv = (TextView) itemView.findViewById(R.id.item_detail_activity_out_country_tv);
        }
    }
}
