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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lx.lxyd.R;
import com.lx.lxyd.adapter.FilterBureauAdapter;
import com.lx.lxyd.adapter.GridSpacingItemDecoration;
import com.lx.lxyd.bean.allListData;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.infoData;
import com.lx.lxyd.constant.OnItemClickListener;
import com.lx.lxyd.utils.DataString;
import com.lx.lxyd.utils.Util;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class recListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView = null;
    private TextView home_Ttime, home_Ttim, rec_text, rec_weibaike, song, top, next;
    private List<colBean> dataList = null;
    private List<infoData> infoDataList = new ArrayList<>();
    private FilterBureauAdapter adapter;
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
        home_Ttim = (TextView) findViewById(R.id.home_Ttim);
        next = (TextView) findViewById(R.id.next);
        top = (TextView) findViewById(R.id.top);
        home_Ttime = (TextView) findViewById(R.id.home_Ttime);
        mRecyclerView = findViewById(R.id.cusom_swipe_view);
        rec_text = (TextView) findViewById(R.id.rec_text);
        rec_weibaike = (TextView) findViewById(R.id.rec_weibaike);
        song = (TextView) findViewById(R.id.song);
        home_Ttime.setText(DataString.StringData());
        initFilterBureau();
    }

    public void initFilterBureau() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(recListActivity.this, 4, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 4, Util.dip2px(recListActivity.this, 16), true));
        adapter = new FilterBureauAdapter(recListActivity.this, dataList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(recListActivity.this, InfoActivity.class);
                i.putExtra("info", infoDataList.get(position));
                i.putExtra("flag", "0");
                startActivity(i);
//                adapter.refreshBureau(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        rec_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = 0;
                pageid = 0;
                getData(pageid, pagesize);
                adapter = new FilterBureauAdapter(recListActivity.this, dataList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        rec_weibaike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = 0;
                pageid = 1;
                getData(pageid, pagesize);
                adapter = new FilterBureauAdapter(recListActivity.this, dataList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = 0;
                pageid = 2;
                getData(pageid, pagesize);
//                adapter = new FilterBureauAdapter(recListActivity.this, dataList);
//                mRecyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesize = pagesize + 8;
                getData(pageid, pagesize);

            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pagesize = pagesize - 8;
                if (pagesize < 0) {
                    return;
                }
                getData(pageid, pagesize);
//                adapter = new FilterBureauAdapter(recListActivity.this, dataList);
//                mRecyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
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
        getDataFirst(0, 0);
    }

    private void getDataFirst(int topid, int page) {
        infoDataList = DataSupport.where("topId=?", meunList.get(topid)).limit(8).offset(page).find(infoData.class);
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

    private void getData(int topid, int page) {
        infoDataList = DataSupport.where("topId=?", meunList.get(topid)).limit(8).offset(page).find(infoData.class);
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
        adapter = new FilterBureauAdapter(recListActivity.this, dataList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
