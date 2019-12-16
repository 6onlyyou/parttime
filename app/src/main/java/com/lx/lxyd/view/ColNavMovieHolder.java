package com.lx.lxyd.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lx.lxyd.R;
import com.yan.tvprojectutils.MarqueeText;

/**
 * Created by yan on 2017/7/21.
 */
public class ColNavMovieHolder extends RecyclerView.ViewHolder {
    public MarqueeText tvTitle;
    public View pflContainer;
    public RoundImageView ivPoster;
    public ColNavMovieHolder(View itemView) {
        super(itemView);
        ivPoster = itemView.findViewById(R.id.col_img1);
        if (itemView.findViewById(R.id.tv_movie_nav) != null) {
            tvTitle = (MarqueeText) itemView.findViewById(R.id.tv_movie_nav);
            pflContainer = itemView.findViewById(R.id.pfl_container1);
        }
    }

}
