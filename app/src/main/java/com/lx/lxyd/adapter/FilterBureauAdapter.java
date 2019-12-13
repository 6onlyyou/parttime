package com.lx.lxyd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.lxyd.R;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.constant.OnItemClickListener;
import com.lx.lxyd.utils.GlideUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Data：2019/12/9-12:33
 * Author: fushuaige
 */
public class FilterBureauAdapter extends RecyclerView.Adapter<FilterBureauAdapter.FilterBureauHolder> {

    private Context mContext;
    private List<colBean> mList;
    private Map<Integer, FilterBureauHolder> mHolderMap;

    public FilterBureauAdapter(Context context, @NonNull List<colBean> list) {
        mContext = context;
        mList = list;
        mHolderMap = new HashMap<>();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public FilterBureauHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.colitem, parent, false);
        FilterBureauHolder holder = new FilterBureauHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(final FilterBureauHolder holder, final int position) {
        holder.btnBureau.setText(mList.get(position).getTitle());
        GlideUtil.loadPlaceHolder(holder.itemView.getContext(), mList.get(position).getThumbnail_Url(),   holder.col_img);
        holder.col_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
        // 保存子项的ViewHolder
        mHolderMap.put(position, holder);
    }

    /**
     * 刷新区局按钮点击状态
     */
    public void refreshBureau(int position) {
        for (Map.Entry<Integer, FilterBureauHolder> entry : mHolderMap.entrySet()) {
            FilterBureauHolder holder = entry.getValue();
            // 点击项背景以及字体变色，其它项背景及字体设置为默认颜色
//            if (entry.getKey() == position){
//                holder.btnBureau.setBackgroundResource(R.mipmap.icon_filterbox_on);
//                holder.btnBureau.setTextColor(mContext.getResources().getColor(R.color.main_blue));
//            } else {
//                holder.btnBureau.setBackgroundResource(R.mipmap.icon_filterbox_off);
//                holder.btnBureau.setTextColor(mContext.getResources().getColor(R.color.main_gray));
//            }
        }
    }

    class FilterBureauHolder extends RecyclerView.ViewHolder {
        TextView btnBureau;
        ImageView col_img;

        public FilterBureauHolder(View itemView) {
            super(itemView);
            btnBureau = itemView.findViewById(R.id.col_name);
            col_img = itemView.findViewById(R.id.col_img);

        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
