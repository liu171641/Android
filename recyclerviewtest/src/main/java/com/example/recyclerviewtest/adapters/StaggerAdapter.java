package com.example.recyclerviewtest.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.beans.ItemBean;

import java.util.List;

/**
 * 作者 : 刘宇航
 * 邮箱 : 1716413010@qq.com
 * 日期  : 2020/5/12 16:57
 * 内容   :
 * 版本: 1.0
 */
public class StaggerAdapter extends RecyclerViewBaseAdapter{
    public StaggerAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(), R.layout.item_stagger_view,null);
        return view;
    }
}
