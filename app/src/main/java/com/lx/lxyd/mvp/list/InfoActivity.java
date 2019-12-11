package com.lx.lxyd.mvp.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lx.lxyd.R;
import com.lx.lxyd.adapter.FilterBureauAdapter;
import com.lx.lxyd.adapter.GridSpacingItemDecoration;
import com.lx.lxyd.bean.RxBusEntity;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.hisBean;
import com.lx.lxyd.bean.infoData;
import com.lx.lxyd.utils.RxBus;
import com.lx.lxyd.utils.Util;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView = null;
    private FilterBureauAdapter adapter;
    private Button info_play, info_col;
    private infoData mcolBean =null;
    private List<colBean> dataList = null;
    private List<infoData> infoDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setView();
        mRecyclerView = findViewById(R.id.cusom_swipe_view);
        info_play = findViewById(R.id.info_play);
        info_col = findViewById(R.id.info_col);
        Intent i = getIntent();
        mcolBean = i.getParcelableExtra("info");
        getData();
        initFilterBureau();
    }

    public void initFilterBureau() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(InfoActivity.this, 5, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 5, Util.dip2px(InfoActivity.this, 16), true));
        adapter = new FilterBureauAdapter(InfoActivity.this, dataList);
        mRecyclerView.setAdapter(adapter);
        info_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                System.out.println(dateFormat.format(date));
                hisBean mkhisBean = new hisBean();
                List<hisBean> hisBeanlist = DataSupport.where("title = ? ", mcolBean.getTitle()).find(hisBean.class);
                if (hisBeanlist == null || hisBeanlist.size() == 0) {
                } else {
                    for (int i = 0; i < hisBeanlist.size(); i++) {
                        hisBeanlist.get(i).delete();
                    }
                }

                mkhisBean.setThumbnail_Url(mcolBean.getThumbnail_Url());
                mkhisBean.setAudio_url(mcolBean.getAudio_url());
                mkhisBean.setTitle(mcolBean.getTitle());
                mkhisBean.setTopId(mcolBean.getTopId());
                mkhisBean.setCreateTime(dateFormat.format(date));
                if (mkhisBean.save()) {
                    RxBusEntity rxBusEntity = new RxBusEntity();
                    rxBusEntity.setMsg("2");
                    RxBus.getDefault().post(rxBusEntity);
                } else {
                }
                Intent i = new Intent(InfoActivity.this, PlayerActivity.class);
                i.putExtra("info", mcolBean);
                i.putExtra("flag","0");
                startActivity(i);
            }
        });
        info_col.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                System.out.println(dateFormat.format(date));
                colBean mkcolBean = new colBean();
                List<colBean> colBeanlist = DataSupport.where("title = ? ", mcolBean.getTitle()).find(colBean.class);
                if (colBeanlist == null || colBeanlist.size() == 0) {
                } else {
                    for (int i = 0; i < colBeanlist.size(); i++) {
                        colBeanlist.get(i).delete();
                    }
                }
                mkcolBean.setThumbnail_Url(mcolBean.getThumbnail_Url());
                mkcolBean.setAudio_url(mcolBean.getAudio_url());
                mkcolBean.setTitle(mcolBean.getTitle());
                mkcolBean.setTopId(mcolBean.getTopId());
                mkcolBean.setCreateTime(dateFormat.format(date));
                if (mkcolBean.save()) {
                    Toast.makeText(InfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    RxBusEntity rxBusEntity = new RxBusEntity();
                    rxBusEntity.setMsg("1");
                    RxBus.getDefault().post(rxBusEntity);
                } else {
                    Toast.makeText(InfoActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public  void  setView(){
        Window window = this.getWindow();
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
        }
        int statusBarHeight = 10;
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            if (lp != null && lp.topMargin >= statusBarHeight) {
                lp.topMargin -= statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
    }
    public void getData() {

        infoDataList = DataSupport.where("topId=?", mcolBean.getTopId()).limit(5).offset(0).find(infoData.class);
        if (infoDataList == null) {
            return;
        }
        dataList = new ArrayList<>();
        for (int i = 0; i < infoDataList.size(); i++) {
            colBean colBean = new colBean();
            colBean.setTitle(infoDataList.get(i).getTitle());
            colBean.setThumbnail_Url(infoDataList.get(i).getThumbnail_Url());
            dataList.add(colBean);
        }
    }
}
