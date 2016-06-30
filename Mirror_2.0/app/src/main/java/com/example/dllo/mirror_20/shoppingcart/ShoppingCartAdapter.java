package com.example.dllo.mirror_20.shoppingcart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dllo.mirror_20.Bean.DBBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.allcategories.MyRvOnClickListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/27.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyShoppingCartViewHolder> {
    private ArrayList<DBBean>data;
    private Context context;
    private NetworkTools tools;
    private MyRvOnClickListener listener;

    public void setData(ArrayList<DBBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(MyRvOnClickListener listener) {
        this.listener = listener;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ShoppingCartAdapter(Context context) {
        this.context = context;
        tools=new NetworkTools();
    }

    @Override
    public MyShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_allcategories_recyclerview, parent, false);
        MyShoppingCartViewHolder holder = new MyShoppingCartViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyShoppingCartViewHolder holder, int position) {
        holder.layout.setVisibility(View.VISIBLE);

        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String url=data.get(position).getGoods_img();
        tools.getNetworkImage(url, holder.imageView,holder.bar);
        holder.englishTv.setText(data.get(position).getGoods_name());
        holder.moneyTv.setText(data.get(position).getGoods_price());
        holder.originTv.setText(data.get(position).getProduct_area());
        holder.introduceTv.setText(data.get(position).getModel());
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
        return data==null?0:data.size();
    }

    class MyShoppingCartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView englishTv, moneyTv, originTv, introduceTv;
        AutoRelativeLayout layout;
        AutoLinearLayout linearLayout;
        ProgressBar bar;

        public MyShoppingCartViewHolder(View itemView) {
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
