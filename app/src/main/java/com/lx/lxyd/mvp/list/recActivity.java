package com.lx.lxyd.mvp.list;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heixiu.errand.net.RetrofitFactory;
import com.lx.lxyd.R;
import com.lx.lxyd.adapter.NavMovieColAdapter;
import com.lx.lxyd.adapter.NavMovieHisAdapter;
import com.lx.lxyd.bean.allListData;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.colMBean;
import com.lx.lxyd.bean.hisBean;
import com.lx.lxyd.bean.hisMBean;
import com.lx.lxyd.bean.homeBean;
import com.lx.lxyd.bean.homeData;
import com.lx.lxyd.bean.infoData;
import com.lx.lxyd.bean.maintainData;
import com.lx.lxyd.net.bean.ResponseBean;
import com.lx.lxyd.net.service.ApiService;
import com.lx.lxyd.utils.DataString;
import com.lx.lxyd.utils.SPUtil;
import com.lx.lxyd.view.NavMovieAdapter;
import com.yan.tvprojectutils.FocusRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * Data：2019/12/4-16:47
 * Author: fushuaige
 */
public class recActivity extends AppCompatActivity {
    private TextView home_Ttime, home_Ttim;
    private Button button1, button2, button3;
    private NavMovieAdapter dataAdapter;
    private NavMovieColAdapter dataColAdapter;
    private NavMovieHisAdapter dataHisAdapter;
    LinearLayout homeLa, colLa, hisLa;
    private List<colBean> colBeanList = new ArrayList<colBean>();
    private List<hisBean> hisBeanList = new ArrayList<hisBean>();
    private FocusRecyclerView rvData, rv_datacol, rv_datahis;
    private List<maintainData> maintainDataList = new ArrayList<maintainData>();
    private List<homeBean> stringList = new ArrayList<>();
    private List<colMBean> colMBeanList = new ArrayList<colMBean>();
    private List<hisMBean> hisMBeanList = new ArrayList<hisMBean>();
    private String savePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        new TimeThread().start();
        home_Ttim = (TextView) findViewById(R.id.home_Ttim);
        home_Ttime = (TextView) findViewById(R.id.home_Ttime);
        rvData = (FocusRecyclerView) findViewById(R.id.rv_data);
        rv_datacol = (FocusRecyclerView) findViewById(R.id.rv_datacol);
        rv_datahis = (FocusRecyclerView) findViewById(R.id.rv_datahis);
        homeLa = findViewById(R.id.home_la);
        colLa = findViewById(R.id.col_la);
        hisLa = findViewById(R.id.his_la);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获得焦点
                    homeLa.setVisibility(View.VISIBLE);
                    colLa.setVisibility(View.GONE);
                    hisLa.setVisibility(View.GONE);
                } else {
                }
            }
        });
        button2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    //获得焦点
                    homeLa.setVisibility(View.GONE);
                    colLa.setVisibility(View.VISIBLE);
                    hisLa.setVisibility(View.GONE);
                } else {
                }
            }
        });
        button3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    //获得焦点
                    homeLa.setVisibility(View.GONE);
                    colLa.setVisibility(View.GONE);
                    hisLa.setVisibility(View.VISIBLE);
                } else {
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLa.setVisibility(View.VISIBLE);
                colLa.setVisibility(View.GONE);
                hisLa.setVisibility(View.GONE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLa.setVisibility(View.GONE);
                colLa.setVisibility(View.VISIBLE);
                hisLa.setVisibility(View.GONE);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLa.setVisibility(View.GONE);
                colLa.setVisibility(View.GONE);
                hisLa.setVisibility(View.VISIBLE);
            }
        });
        home_Ttime.setText(DataString.StringData());
        if (SPUtil.getInt(recActivity.this, "first", 0) == 1) {
            initData();
        } else {
            DataSupport.deleteAll(allListData.class);
            DataSupport.deleteAll(maintainData.class);
            DataSupport.deleteAll(infoData.class);
            RetrofitFactory.INSTANCE.getRetrofit(ApiService.class).getData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseBean<ArrayList<homeData>>>() {
                        @Override
                        public void accept(ResponseBean<ArrayList<homeData>> homeDataList) throws Exception {
                            savePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            ArrayList<maintainData> maintainDataList = new ArrayList();
                            ArrayList<allListData> allListDataList = new ArrayList();
                            ArrayList<infoData> infoDataList = new ArrayList();
                            for (int i = 0; i < homeDataList.getData().size(); i++) {
                                maintainData maintainData = new maintainData();
                                maintainData.setMainName(homeDataList.getData().get(i).getName());
                                maintainData.setMainCode(homeDataList.getData().get(i).getCode());
                                maintainData.setMainId(homeDataList.getData().get(i).getId());
                                maintainDataList.add(maintainData);
                                for (int k = 0; k < homeDataList.getData().get(i).getItems().get(0).getItems().size(); k++) {
                                    allListData allListData = new allListData();
                                    allListData.setCode(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getCode());
                                    allListData.setMainId(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getId());
                                    allListData.setName(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getName());
                                    allListData.setTopId(homeDataList.getData().get(i).getId());
                                    allListDataList.add(allListData);
                                    for (int y = 0; y < homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().size(); y++) {
                                        for (int z = 0; z < homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().size(); z++) {
                                            infoData infoData = new infoData();
                                            infoData.setAudio_url(savePath + homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getAudio_url());
                                            infoData.setCreate_time(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getCreate_time());
                                            infoData.setCreater(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getCreater());
                                            infoData.setFile_auffix(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getFile_auffix());
                                            infoData.setFile_name(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getFile_name());
                                            infoData.setFile_url(savePath + homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getFile_url());
                                            infoData.setIcon_url(savePath + homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getIcon_url());
                                            infoData.setIs_charge(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getIs_charge());
                                            infoData.setIs_hot(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getIs_hot());
                                            infoData.setRemark(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getRemark());
                                            infoData.setRes_code(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getRes_code());
                                            infoData.setRes_type(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getRes_type());
                                            infoData.setStatus(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getStatus());
                                            infoData.setTag(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getTag());
                                            infoData.setThumbnail_Url(savePath + homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getThumbnail_Url());
                                            infoData.setTitle(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getItems().get(y).getItems().get(z).getTitle());
                                            infoData.setTopId(homeDataList.getData().get(i).getItems().get(0).getItems().get(k).getId());
                                            infoDataList.add(infoData);
                                            Log.d("infoDataList", "33");
                                        }

                                    }
                                    Log.d("infoDataList", infoDataList.size() + "kkk");
                                    DataSupport.saveAll(infoDataList);
                                }
                                Log.d("infoDataList", allListDataList.size() + "hhh" + allListDataList.get(0).getName() + allListDataList.get(1).getName());
                                DataSupport.saveAll(allListDataList);
                            }
                            DataSupport.saveAll(maintainDataList);
                            SPUtil.putInt(recActivity.this, "first", 1);
//                            initViewPage();
                            initData();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d("getData", throwable.getMessage().toString());
                        }
                    });
        }
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

        GridLayoutManager focusGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rvData.setLayoutManager(focusGridLayoutManager);
        rvData.setAdapter(dataAdapter = new NavMovieAdapter(this, stringList));
        rvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (rvData.isRecyclerViewToBottom()) {
                    }
                }
            }
        });
        int tempSize = stringList.size();
        maintainDataList = DataSupport.findAll(maintainData.class);
        for (int i = 0; i < maintainDataList.size(); i++) {
            homeBean mhomeBean = new homeBean();
            mhomeBean.setMainCode(maintainDataList.get(i).getMainCode());
            mhomeBean.setMainId(maintainDataList.get(i).getMainId());
            mhomeBean.setMainName(maintainDataList.get(i).getMainName());
            stringList.add(mhomeBean);
        }
        dataAdapter.notifyItemRangeInserted(tempSize, stringList.size() - tempSize);


        GridLayoutManager focusGridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 5);
        rv_datacol.setLayoutManager(focusGridLayoutManager1);
        rv_datacol.setAdapter(dataColAdapter = new NavMovieColAdapter(this, colMBeanList));
        rv_datacol.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (rv_datacol.isRecyclerViewToBottom()) {
                    }
                }
            }
        });
        int tempSize1 = colBeanList.size();
        colBeanList = DataSupport.findAll(colBean.class);
        for (int i = 0; i < colBeanList.size(); i++) {
            colMBean mhomeBean = new colMBean();
            mhomeBean.setAudio_url(colBeanList.get(i).getAudio_url());
            mhomeBean.setTitle(colBeanList.get(i).getTitle());
            mhomeBean.setThumbnail_Url(colBeanList.get(i).getThumbnail_Url());
            mhomeBean.setTopId(colBeanList.get(i).getTopId());
            colMBeanList.add(mhomeBean);
        }
        dataColAdapter.notifyItemRangeInserted(tempSize1, colBeanList.size() - tempSize);


        GridLayoutManager focusGridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 5);
        rv_datahis.setLayoutManager(focusGridLayoutManager2);
        rv_datahis.setAdapter(dataHisAdapter = new NavMovieHisAdapter(this, hisMBeanList));
        rv_datahis.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (rv_datahis.isRecyclerViewToBottom()) {
                    }
                }
            }
        });
        int tempSize2 = hisBeanList.size();
        hisBeanList = DataSupport
                .order("create_time desc")//倒序字段
                .find(hisBean.class);//查询表
        for (int i = 0; i < hisBeanList.size(); i++) {
            hisMBean mhomeBean = new hisMBean();
            mhomeBean.setAudio_url(hisBeanList.get(i).getAudio_url());
            mhomeBean.setTitle(hisBeanList.get(i).getTitle());
            mhomeBean.setThumbnail_Url(hisBeanList.get(i).getThumbnail_Url());
            mhomeBean.setTopId(hisBeanList.get(i).getTopId());
            mhomeBean.setCreate_time(hisBeanList.get(i).getCreate_time());
            hisMBeanList.add(mhomeBean);
        }
        dataHisAdapter.notifyItemRangeInserted(tempSize2, hisMBeanList.size() - tempSize);

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


