package com.lx.lxyd.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lx.lxyd.R;
import com.yan.tvprojectutils.MarqueeText;

/**
 * Created by yan on 2017/7/21.
 */
public class NavMovieHolder extends RecyclerView.ViewHolder {
    public MarqueeText tvTitle;
    public View pflContainer;

    public NavMovieHolder(View itemView) {
        super(itemView);
        if (itemView.findViewById(R.id.tv_movie_nav) != null) {
            tvTitle = (MarqueeText) itemView.findViewById(R.id.tv_movie_nav);
            pflContainer = itemView.findViewById(R.id.pfl_container);
        }
    }

}
