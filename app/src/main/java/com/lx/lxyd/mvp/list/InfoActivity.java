package com.lx.lxyd.mvp.list;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lx.lxyd.R;
import com.lx.lxyd.adapter.FilterBureauAdapter;
import com.lx.lxyd.adapter.NavMovieHisAdapter;
import com.lx.lxyd.bean.RxBusEntity;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.colMBean;
import com.lx.lxyd.bean.hisBean;
import com.lx.lxyd.bean.hisMBean;
import com.lx.lxyd.bean.infoData;
import com.lx.lxyd.utils.DataString;
import com.lx.lxyd.utils.GlideUtil;
import com.lx.lxyd.utils.RxBus;
import com.yan.tvprojectutils.FocusRecyclerView;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    private FilterBureauAdapter adapter;
    private NavMovieHisAdapter dataAdapter;
    private FocusRecyclerView rvData;
    private Button info_play, info_col;
    private colMBean mcolBean = null;
    private TextView home_Ttime, home_Ttim;
    private List<hisMBean> hisMBeanList = new ArrayList<hisMBean>();
    ImageView info_img;
    TextView info_name;
    private List<colBean> dataList = null;
    private List<infoData> infoDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setView();
        home_Ttim = findViewById(R.id.home_Ttim);
        home_Ttime = findViewById(R.id.home_Ttime);
        rvData = findViewById(R.id.rv_data);
        info_play = findViewById(R.id.info_play);
        info_col = findViewById(R.id.info_col);
        info_name = findViewById(R.id.info_name);
        info_img = findViewById(R.id.info_img);
        home_Ttime.setText(DataString.StringData());
        Intent i = getIntent();
        mcolBean = i.getParcelableExtra("info");
        getData();
        initFilterBureau();
    }

    public void initFilterBureau() {
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
                i.putExtra("flag", "1");
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

    public void setView() {
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
        new TimeThread().start();
    }

    public void getData() {

        info_name.setText(mcolBean.getTitle());
        GlideUtil.loadPlaceHolder(this, mcolBean.getThumbnail_Url(), info_img);
        GridLayoutManager focusGridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 5);
        rvData.setLayoutManager(focusGridLayoutManager2);
        rvData.setAdapter(dataAdapter = new NavMovieHisAdapter(this, hisMBeanList));
        rvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (rvData.isRecyclerViewToBottom()) {
                    }
                }
            }
        });
        infoDataList = DataSupport.where("topId=?", mcolBean.getTopId()).limit(5).offset(0).find(infoData.class);
        if (infoDataList == null) {
            return;
        }
        for (int i = 0; i < infoDataList.size(); i++) {
            hisMBean mhomeBean = new hisMBean();
            mhomeBean.setAudio_url(infoDataList.get(i).getAudio_url());
            mhomeBean.setTitle(infoDataList.get(i).getTitle());
            mhomeBean.setThumbnail_Url(infoDataList.get(i).getThumbnail_Url());
            mhomeBean.setTopId(infoDataList.get(i).getTopId());
            mhomeBean.setCreate_time(infoDataList.get(i).getCreate_time());
            hisMBeanList.add(mhomeBean);
        }
        dataAdapter.notifyDataSetChanged();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);//时间显示格式
                    home_Ttim.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }
        }
    };

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
