package com.lx.lxyd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lx.lxyd.R;
import com.lx.lxyd.adapter.FilterBureauAdapter;
import com.lx.lxyd.adapter.GridSpacingItemDecoration;
import com.lx.lxyd.bean.RxBusEntity;
import com.lx.lxyd.bean.colBean;
import com.lx.lxyd.bean.hisBean;
import com.lx.lxyd.constant.OnItemClickListener;
import com.lx.lxyd.mvp.list.PlayerActivity;
import com.lx.lxyd.utils.RxBus;
import com.lx.lxyd.utils.Util;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Data：2019/12/7-16:28
 * Author: fushuaige
 */
public class TimeFragment extends Fragment {
    private RecyclerView HRecyclerView = null;
    List<hisBean> hisMBeanList = null;
    private List<colBean> hisDataList = null;
    private Disposable subscribe = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        initData();
        initFilterBureau(view);
        subscribe = RxBus.getDefault().toObservable(RxBusEntity.class).subscribe(new Consumer<RxBusEntity>() {
            @Override
            public void accept(RxBusEntity rxBusEntity) throws Exception {
                if (rxBusEntity.getMsg().equals("1")) {
                    initData();
                    final FilterBureauAdapter adapter = new FilterBureauAdapter(getActivity(), hisDataList);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent i = new Intent(getActivity(), PlayerActivity.class);
                            i.putExtra("info", hisDataList.get(position));
                            i.putExtra("flag", "1");
                            startActivity(i);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    HRecyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }

            }
        });
        return view;
    }

    public void initFilterBureau(View view) {

        HRecyclerView = view.findViewById(R.id.todatacusom_swipe_view);
        if (hisDataList == null) {
            return;
        }
        HRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false));
        HRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 5, Util.dip2px(getActivity(), 16), true));
        final FilterBureauAdapter hisAdapter = new FilterBureauAdapter(getActivity(), hisDataList);
        hisAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), PlayerActivity.class);
                i.putExtra("info", hisDataList.get(position));
                i.putExtra("flag", "2");
                startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        HRecyclerView.setAdapter(hisAdapter);
    }

    private void initData() {
        hisMBeanList = DataSupport
                .order("create_time desc")//倒序字段
                .find(hisBean.class);//查询表
        if (hisMBeanList == null) {
            return;
        }
        hisDataList = new ArrayList<>();
        for (int i = 0; i < hisMBeanList.size(); i++) {
            colBean colBean = new colBean();
            colBean.setTitle(hisMBeanList.get(i).getTitle());
            colBean.setThumbnail_Url(hisMBeanList.get(i).getThumbnail_Url());
            hisDataList.add(colBean);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscribe.dispose();
    }
}