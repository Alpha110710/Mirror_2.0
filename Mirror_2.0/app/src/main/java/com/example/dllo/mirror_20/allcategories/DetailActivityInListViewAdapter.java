package com.example.dllo.mirror_20.allcategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.networktools.NetworkTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class DetailActivityInListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> strs;
    private List<DataAllBean.DataBean.ListBean.DataInfoBean.DesignDesBean> designDesBeans;
    private NetworkTools networkTools;

    public void setDesignDesBeans(List<DataAllBean.DataBean.ListBean.DataInfoBean.DesignDesBean> designDesBeans) {
        this.designDesBeans = designDesBeans;
        notifyDataSetChanged();
    }

    public DetailActivityInListViewAdapter(Context context) {
        this.context = context;
        networkTools = new NetworkTools();
    }

    public void setStrs(ArrayList<String> strs) {
        this.strs = strs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return designDesBeans == null ? 0 : designDesBeans.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_activity_in_listview, parent, false);
            viewViewHolder = new DetailActivityOutListViewViewHolder(convertView);
            convertView.setTag(viewViewHolder);
        } else {
            viewViewHolder = (DetailActivityOutListViewViewHolder) convertView.getTag();
        }

        viewViewHolder.itemDetailActivityOutListviewImg.setScaleType(ImageView.ScaleType.FIT_XY);
        networkTools.getNetworkImage(designDesBeans.get(position).getImg(), viewViewHolder.itemDetailActivityOutListviewImg);

        return convertView;
    }

    class DetailActivityOutListViewViewHolder {

        private final ImageView itemDetailActivityOutListviewImg;

        public DetailActivityOutListViewViewHolder(View itemView) {
            itemDetailActivityOutListviewImg = (ImageView) itemView.findViewById(R.id.item_detail_activity_in_listview_img);


        }
    }
}
