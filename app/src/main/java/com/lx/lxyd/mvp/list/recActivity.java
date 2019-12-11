package com.lx.lxyd.mvp.list;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heixiu.errand.net.RetrofitFactory;
import com.lx.lxyd.R;
import com.lx.lxyd.bean.allListData;
import com.lx.lxyd.bean.homeData;
import com.lx.lxyd.bean.infoData;
import com.lx.lxyd.bean.maintainData;
import com.lx.lxyd.fragment.ColFragment;
import com.lx.lxyd.fragment.HomeFragment;
import com.lx.lxyd.fragment.TimeFragment;
import com.lx.lxyd.net.bean.ResponseBean;
import com.lx.lxyd.net.service.ApiService;
import com.lx.lxyd.utils.DataString;
import com.lx.lxyd.utils.SPUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String savePath;

    private String[] str = {"首页", "收藏", "记录"};
    private int[] ints = {R.mipmap.home_ico, R.mipmap.home_col, R.mipmap.home_tim};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        new TimeThread().start();
        tabLayout = findViewById(R.id.mtab);
        viewPager = findViewById(R.id.home_Vpage);
        home_Ttim = (TextView) findViewById(R.id.home_Ttim);
        home_Ttime = (TextView) findViewById(R.id.home_Ttime);
        home_Ttime.setText(DataString.StringData());
        if (SPUtil.getInt(recActivity.this, "first", 0) == 1) {
            initViewPage();
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
                            initViewPage();
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
    }

    private void initViewPage() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new Fragment();
                if (fragment != null) {
                    switch (position) {
                        case 0:
                            fragment = new HomeFragment();
                            break;
                        case 1:
                            fragment = new ColFragment();
                            break;
                        case 2:
                            fragment = new TimeFragment();
                            break;
                    }
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        for (int i = 0; i < 3; i++) {
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.home_Tico);
        ImageView iv = view.findViewById(R.id.home_Iico);
        switch (tab.getPosition()) {
            case 0:
                iv.setImageResource(R.mipmap.home_pico);
                viewPager.setCurrentItem(0);
                break;
            case 1:
                iv.setImageResource(R.mipmap.home_colun);
                viewPager.setCurrentItem(1);
                break;
            case 2:
                iv.setImageResource(R.mipmap.home_timun);
                viewPager.setCurrentItem(2);
                break;
            default:

        }
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(12);
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.home_Tico);
        ImageView iv = view.findViewById(R.id.home_Iico);
        switch (tab.getPosition()) {
            case 0:
                iv.setImageResource(R.mipmap.home_ico);
                break;
            case 1:
                iv.setImageResource(R.mipmap.home_col);
                break;
            case 2:
                iv.setImageResource(R.mipmap.home_tim);
                break;
            default:
        }
        textView.setTextColor(Color.parseColor("#9b9b9b"));
        textView.setTextSize(12);
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

    public View getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.layout_tab_item, null);
        ImageView iv = v.findViewById(R.id.home_Iico);
        TextView tv = v.findViewById(R.id.home_Tico);
        iv.setBackgroundResource(ints[position]);
        tv.setText(str[position]);
        if (position == 0) {
            iv.setImageResource(R.mipmap.home_pico);
            tv.setTextColor(v.getResources().getColor(R.color.white));
        }
        return v;
    }

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


