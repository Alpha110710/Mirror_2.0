package com.example.dllo.mirror_20.projectshare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.allcategories.MyRvOnClickListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by dllo on 16/6/23.
 */
public class ProjectShareRvAdapter extends RecyclerView.Adapter<ProjectShareRvAdapter.ProjectShareViewHolder> {
    private Context context;
    private ProjectShareBean bean;
    private NetworkTools tools;
    private MyRvOnClickListener listener;

    public void setListener(MyRvOnClickListener listener) {
        this.listener = listener;
    }

    public ProjectShareRvAdapter(Context context) {
        this.context = context;
        tools = new NetworkTools();
    }

    public void setBean(ProjectShareBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public ProjectShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_projectshare_recyclrview, parent, false);
        ProjectShareViewHolder holder = new ProjectShareViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ProjectShareViewHolder holder, int position) {
        tools.getNetworkImage(bean.getData().getList().get(position).getStory_img(), holder.imageView);
        holder.textView.setText(bean.getData().getList().get(position).getStory_title());

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
        return bean.getData().getList() == null ? 0 : bean.getData().getList().size();

    }

    class ProjectShareViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        AutoLinearLayout linearLayout;

        public ProjectShareViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            linearLayout = (AutoLinearLayout) itemView.findViewById(R.id.project_share_rv_item_ll);
            textView = (TextView) itemView.findViewById(R.id.project_share_rv_item_introduce);
            imageView = (ImageView) itemView.findViewById(R.id.project_share_rv_item_iv);
        }
    }
}
