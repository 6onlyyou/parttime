package com.lx.lxyd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lx.lxyd.R;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.hisBean;
import com.lx.lxyd.bean.hisMBean;
import com.lx.lxyd.mvp.list.PlayerActivity;
import com.lx.lxyd.utils.GlideUtil;
import com.lx.lxyd.view.AnimationHelper;
import com.lx.lxyd.view.ColNavMovieHolder;
import com.yan.tvprojectutils.MarqueeText;

import java.util.List;

/**
 * Created by yan on 2017/7/21.
 */

public class NavMovieHisAdapter extends RecyclerView.Adapter<ColNavMovieHolder> {
    protected final Context context;
    private final List<hisMBean> stringList;

    public NavMovieHisAdapter(Context context, List<hisMBean> objectList) {
        this.stringList = objectList;
        this.context = context;
    }

    @Override
    public ColNavMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColNavMovieHolder(LayoutInflater.from(context)
                .inflate(R.layout.colitem_all_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(ColNavMovieHolder holder, final int position) {
        holder.pflContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PlayerActivity.class);
                i.putExtra("info", stringList.get(position));
                i.putExtra("flag", "1");
                context.startActivity(i);
            }
        });
//        GlideUtil.loadPlaceHolder(holder.itemView.getContext(), stringList.get(position).getThumbnail_Url(),  holder.ivPoster);

        holder.tvTitle.setText(stringList.get(position).getTitle());
        holder.pflContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AnimationHelper animationHelper = new AnimationHelper();

                animationHelper.setRatioX((v.getWidth() - dipToPx(context, 10)) / ((ViewGroup) v).getChildAt(0).getWidth());
                animationHelper.setRatioY((v.getHeight() - dipToPx(context, 10)) / ((ViewGroup) v).getChildAt(0).getHeight());
                MarqueeText tvTitle = (MarqueeText) v.findViewById(R.id.tv_movie_nav);
                tvTitle.setSelected(hasFocus);
                if (hasFocus) {
                    animationHelper.starLargeAnimation(((ViewGroup) v).getChildAt(0));
                    tvTitle.startScroll();
                } else {
                    animationHelper.starSmallAnimation(((ViewGroup) v).getChildAt(0));
                    tvTitle.stopScroll();
                }
            }

        });
    }

    private float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

}
