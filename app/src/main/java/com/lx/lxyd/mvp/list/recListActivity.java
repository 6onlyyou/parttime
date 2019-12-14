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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lx.lxyd.R;
import com.lx.lxyd.adapter.FilterBureauAdapter;
import com.lx.lxyd.adapter.GridSpacingItemDecoration;
import com.lx.lxyd.adapter.NavMovieColAdapter;
import com.lx.lxyd.bean.allListData;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.colMBean;
import com.lx.lxyd.bean.infoData;
import com.lx.lxyd.constant.OnItemClickListener;
import com.lx.lxyd.utils.DataString;
import com.lx.lxyd.utils.Util;
import com.lx.lxyd.view.NavMovieAdapter;
import com.yan.tvprojectutils.FocusRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class recListActivity extends AppCompatActivity {
    private TextView home_Ttime, home_Ttim;
    private Button  rec_text, rec_weibaike, song, top, next;
    private List<infoData> infoDataList = new ArrayList<>();
    private List<colMBean> colMBeanList = new ArrayList<colMBean>();
    private NavMovieColAdapter dataColAdapter;
    private FocusRecyclerView rvData;
    private List<String> meunList = new ArrayList<>();
    private int pagesize = 0;
    private int pageid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_list);
        Intent intent = getIntent();
        String topid = intent.getStringExtra("topid");
        List<allListData> allPersons = DataSupport.findAll(allListData.class);
        Log.d("allPersons", allPersons.size() + "");
        List<allListData> mallListData = DataSupport.where("topId=?", topid).find(allListData.class);
        for (allListData msallListData : mallListData) {
            meunList.add(msallListData.getMainId());
        }
        initData();
        new TimeThread().start();
        home_Ttim =  findViewById(R.id.home_Ttim);
        next =  findViewById(R.id.next);
        top =  findViewById(R.id.top);
        home_Ttime =  findViewById(R.id.home_Ttime);
        rvData = findViewById(R.id.rv_data);
        rec_text =findViewById(R.id.rec_text);
        rec_weibaike =  findViewById(R.id.rec_weibaike);
        song = findViewById(R.id.song);
        home_Ttime.setText(DataString.StringData());
        initFilterBureau();
    }

    public void initFilterBureau() {
        GridLayoutManager focusGridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
        rvData.setLayoutManager(focusGridLayoutManager1);
        rvData.setAdapter(dataColAdapter = new NavMovieColAdapter(this, colMBeanList));
        rvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (rvData.isRecyclerViewToBottom()) {
                    }
                }
            }
        });
        getDataFirst(0,0);

        rec_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = 0;
                pageid = 0;
                getDataFirst(pageid, pagesize);
            }
        });
        rec_weibaike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = 0;
                pageid = 1;
                getDataFirst(pageid, pagesize);
            }
        });
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = 0;
                pageid = 2;

                getDataFirst(pageid, pagesize);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = pagesize + 8;
                getDataFirst(pageid, pagesize);

            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pagesize = pagesize - 8;
                if (pagesize < 0) {
                    return;
                }
                getDataFirst(pageid, pagesize);
            }
        });
    }


    private void initData() {
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

    private void getDataFirst(int topid, int page) {
//         List<colMBean> colMBeanList1 = new ArrayList<colMBean>();
//        rvData.setAdapter(dataColAdapter = new NavMovieColAdapter(getApplicationContext(), colMBeanList1));
//        rvData.setAdapter(dataColAdapter = new NavMovieColAdapter(getApplicationContext(), colMBeanList));
//        dataColAdapter.notifyDataSetChanged();
        int tempSize1 = infoDataList.size();
        infoDataList = DataSupport.where("topId=?", meunList.get(topid)).limit(8).offset(page).find(infoData.class);
        if (infoDataList == null) {
            return;
        }
        for (int i = 0; i < infoDataList.size(); i++) {
            colMBean mhomeBean = new colMBean();
            mhomeBean.setTitle(infoDataList.get(i).getTitle());
            mhomeBean.setThumbnail_Url(infoDataList.get(i).getThumbnail_Url());
            colMBeanList.add(mhomeBean);
        }
        dataColAdapter.notifyItemRangeInserted(tempSize1, colMBeanList.size() - tempSize1);
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
