package com.example.recyclerviewtest.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.beans.ItemBean;

import java.util.List;

/**
 * 作者 : 刘宇航
 * 邮箱 : 1716413010@qq.com
 * 日期  : 2020/5/12 14:41
 * 内容   :
 * 版本: 1.0
 */
public class GridViewAdapter extends RecyclerViewBaseAdapter {

    public GridViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        //在这创建条目的UI
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        return view;
    }

}

