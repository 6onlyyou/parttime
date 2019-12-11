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
import com.lx.lxyd.bean.infoData;
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
public class ColFragment extends Fragment {
    private RecyclerView mRecyclerView = null;
    List<colBean> colBeanList = null;
    private List<colBean> dataList = null;
    private Disposable subscribe= null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_col, container, false);
        initData();
        initFilterBureau(view);
        subscribe = RxBus.getDefault().toObservable(RxBusEntity.class).subscribe(new Consumer<RxBusEntity>() {
            @Override
            public void accept(RxBusEntity rxBusEntity) throws Exception {
                if(rxBusEntity.getMsg().equals("1")){
                    initData();
                    final FilterBureauAdapter adapter = new FilterBureauAdapter(getActivity(), dataList);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent i = new Intent(getActivity(), PlayerActivity.class);
                            i.putExtra("info",dataList.get(position) );
                            i.putExtra("flag","1");
                            startActivity(i);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });
        return view;
    }

    public void initFilterBureau(View view) {
        mRecyclerView = view.findViewById(R.id.cusom_swipe_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 5, Util.dip2px(getActivity(), 16), true));
        final FilterBureauAdapter adapter = new FilterBureauAdapter(getActivity(), dataList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), PlayerActivity.class);
                i.putExtra("info",dataList.get(position) );
                i.putExtra("flag","1");
                startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(adapter);

    }

    private void initData() {
        colBeanList = DataSupport.findAll(colBean.class);
        if (colBeanList == null) {
            return;
        }
        dataList = new ArrayList<>();
        for (int i = 0; i < colBeanList.size(); i++) {
            colBean colBean = new colBean();
            colBean.setTitle(colBeanList.get(i).getTitle());
            colBean.setThumbnail_Url(colBeanList.get(i).getThumbnail_Url());
            dataList.add(colBean);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscribe.dispose();
    }
}