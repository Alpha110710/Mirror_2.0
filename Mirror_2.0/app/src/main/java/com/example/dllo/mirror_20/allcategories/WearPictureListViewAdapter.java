package com.example.dllo.mirror_20.allcategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.dllo.mirror_20.Bean.WearPictureBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/25.
 */
public class WearPictureListViewAdapter extends BaseAdapter {
    private WearPictureBean.DataBean.ListBean.DataInfoBean dataInfoBean;
    private Context context;
    private NetworkTools networkTools;
    private ArrayList<String> imgUrls;

    public WearPictureListViewAdapter(Context context) {
        this.context = context;
        networkTools = new NetworkTools();
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return imgUrls == null ? 0 : imgUrls.size();
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

        WearPictureListViewViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_wear_picture, parent, false);
            viewHolder = new WearPictureListViewViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (WearPictureListViewViewHolder) convertView.getTag();
        }
//        networkTools.getNetworkImage(imgUrls.get(position), viewHolder.itemWearPictureImg);
        Picasso.with(context).load(imgUrls.get(position)).centerInside().resize(800, 800).placeholder(R.mipmap.white_background)
                .into(viewHolder.itemWearPictureImg);
        return convertView;
    }

    class WearPictureListViewViewHolder {

        private final ImageView itemWearPictureImg;

        public WearPictureListViewViewHolder(View itemView) {
            itemWearPictureImg = (ImageView) itemView.findViewById(R.id.item_wear_picture_img);
        }
    }
}
