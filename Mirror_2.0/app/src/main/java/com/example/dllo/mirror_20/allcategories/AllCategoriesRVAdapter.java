package com.example.dllo.mirror_20.allcategories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesRVAdapter extends RecyclerView.Adapter<AllCategoriesRVAdapter.MyViewHolder> {

    private Context context;
    private DataAllBean bean;
    private NetworkTools tools;

    public AllCategoriesRVAdapter(Context context) {
        this.context = context;
        tools = new NetworkTools();
    }

    public void setBean(DataAllBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_allcategories_recycleerview_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String url = bean.getData().getList().get(position).getData_info().getGoods_img();
        switch (bean.getData().getList().get(position).getType()) {
            case "1":
                holder.layout.setVisibility(View.VISIBLE);
//                Picasso.with(context).load(url).fit().placeholder(R.mipmap.bg).into(holder.imageView);
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                tools.getNetworkImage(url,holder.imageView);
                holder.englishTv.setText(bean.getData().getList().get(position).getData_info().getGoods_name());
                holder.moneyTv.setText("¥" + bean.getData().getList().get(position).getData_info().getGoods_price());
                holder.originTv.setText(bean.getData().getList().get(position).getData_info().getProduct_area());
                holder.introduceTv.setText(bean.getData().getList().get(position).getData_info().getBrand());
                break;
            case "2":
                holder.layout.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                holder.introduceTv.setText("整啥玩意呢");
                break;

        }

    }

    @Override
    public int getItemCount() {
        return bean == null ? 0 : bean.getData().getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView englishTv, moneyTv, originTv, introduceTv;
        AutoRelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.all_categories_rv_item_iv);
            englishTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_english);
            moneyTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_money);
            originTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_origin);
            introduceTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_introduce);
            layout = (AutoRelativeLayout) itemView.findViewById(R.id.layout);
        }
    }
}
