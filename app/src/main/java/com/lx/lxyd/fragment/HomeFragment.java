package com.lx.lxyd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.lxyd.R;
import com.lx.lxyd.bean.maintainData;
import com.lx.lxyd.mvp.list.recListActivity;
import com.lx.lxyd.view.PageIndicatorView;
import com.lx.lxyd.view.PageRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2019/12/7-16:28
 * Author: fushuaige
 */
public class HomeFragment extends Fragment {
    private PageRecyclerView mRecyclerView = null;
    private List<String> dataList = null;
    private List<maintainData> maintainDataList = null;
    private PageRecyclerView.PageAdapter myAdapter = null;
    public int selectedPosition = -5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (PageRecyclerView) view.findViewById(R.id.cusom_swipe_view);
        // 设置指示器
        mRecyclerView.setIndicator((PageIndicatorView) view.findViewById(R.id.indicator));
        // 设置行数和列数
        mRecyclerView.setPageSize(3, 3);
        // 设置页间距
        mRecyclerView.setPageMargin(30);
        // 设置数据
        mRecyclerView.setAdapter(myAdapter = mRecyclerView.new PageAdapter(dataList, new PageRecyclerView.CallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.pageitem, parent, false);
                return new MyHolder(view);
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
                ((MyHolder) holder).tv.setText(dataList.get(position));
                ((MyHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("topid", maintainDataList.get(position).getMainId());
                        intent.setClass(getActivity(),recListActivity.class);
                        getActivity().startActivity(intent);
                    }
                });

            }

            @Override
            public void onItemClickListener(View view, int position) {

            }

            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        }));


    }

    private void initData() {
        maintainDataList = DataSupport.findAll(maintainData.class);
        dataList = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            dataList.add(i + "");
//        }
        for (int i = 0; i < maintainDataList.size(); i++) {
            dataList.add(maintainDataList.get(i).getMainName());
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView tv = null;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.text);
        }
    }
}