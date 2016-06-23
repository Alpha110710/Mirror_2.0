package com.example.dllo.mirror_20.allcategories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesRVAdapter extends RecyclerView.Adapter<AllCategoriesRVAdapter.MyViewHolder> {

    private Context context;
    private DataAllBean bean;
    private NetworkTools tools;
    private MyRvOnClickListener listener;

    public void setListener(MyRvOnClickListener listener) {
        this.listener = listener;
    }

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_allcategories_recyclerview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String url = bean.getData().getList().get(position).getData_info().getGoods_img();
        String url2 = bean.getData().getList().get(position).getData_info().getStory_img();
        switch (bean.getData().getList().get(position).getType()) {
            case "1":
                holder.layout.setVisibility(View.VISIBLE);
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                tools.getNetworkImage(url, holder.imageView);

                holder.englishTv.setText(bean.getData().getList().get(position).getData_info().getGoods_name());
                holder.moneyTv.setText("¥" + bean.getData().getList().get(position).getData_info().getGoods_price());
                holder.originTv.setText(bean.getData().getList().get(position).getData_info().getProduct_area());
                holder.introduceTv.setText(bean.getData().getList().get(position).getData_info().getBrand());
                break;
            case "2":
                holder.layout.setVisibility(View.GONE);
                tools.getNetworkImage(url2, holder.imageView);

                holder.introduceTv.setText(bean.getData().getList().get(position).getData_info().getStory_title());

                break;

        }
        if (listener != null) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(holder.getLayoutPosition());
                }
            });
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
        AutoLinearLayout linearLayout;
        ProgressBar bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.all_categories_rv_item_iv);
            englishTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_english);
            moneyTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_money);
            originTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_origin);
            introduceTv = (TextView) itemView.findViewById(R.id.all_categories_rv_item_introduce);
            layout = (AutoRelativeLayout) itemView.findViewById(R.id.layout);
            linearLayout = (AutoLinearLayout) itemView.findViewById(R.id.all_categories_rv_item_ll);
            bar = (ProgressBar) itemView.findViewById(R.id.all_categories_rv_item_pb);
        }
    }


}
