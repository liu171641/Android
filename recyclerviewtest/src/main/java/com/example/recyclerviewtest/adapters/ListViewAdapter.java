package com.example.recyclerviewtest.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.beans.ItemBean;

import java.util.List;

/**
 * 作者 : 刘宇航
 * 邮箱 : 1716413010@qq.com
 * 日期  : 2020/5/12 12:10
 * 内容   :ListView适配器
 * 版本: 1.0
 */
public class ListViewAdapter extends RecyclerViewBaseAdapter {

    //普通条目类型
    public static final int TYPE_NORMAL = 0;
    //加载类型
    public static final int TYPE_LOADER_MORE = 1;
    private OnRefreshListener mUpPullRefreshListener;

    public ListViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        //根据viewType类型返回来返回holder
        if (viewType == TYPE_NORMAL) {
            return new InnerHolder(view);
        } else {
            return new LoaderMoreHolder(view);
        }
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view;
        //根据类型来创建view
        if (viewType == TYPE_NORMAL) {
            //普通类型
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        } else {
            //加载更多的
            view = View.inflate(parent.getContext(), R.layout.item_list_loder_more, null);
        }
        return view;
    }

    /**
     * 这个方法是用于绑定holder的，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof InnerHolder) {
            //在这里设置数据
            ((InnerHolder) holder).setData(mData.get(position), position);
        } else if (getItemViewType(position) == TYPE_LOADER_MORE && holder instanceof LoaderMoreHolder) {
            ((LoaderMoreHolder) holder).update(LoaderMoreHolder.LOADER_STATE_LOADING);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //最后一个则返回加载更多
            return TYPE_LOADER_MORE;
        }
        return TYPE_NORMAL;
    }

    /**
     * 设置刷的监听的接口
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mUpPullRefreshListener = listener;
    }

    //定义接口
    public interface OnRefreshListener {
        void onUpPullRefresh(LoaderMoreHolder loaderMoreHolder);
    }

    public class LoaderMoreHolder extends RecyclerView.ViewHolder {

        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;

        private LinearLayout mLoading;
        private TextView mReload;

        public LoaderMoreHolder(@NonNull View itemView) {
            super(itemView);

            mLoading = itemView.findViewById(R.id.loading);
            mReload = itemView.findViewById(R.id.reload);

            mReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //这里触发加载数据
                    update(LOADER_STATE_LOADING);
                }
            });
        }

        public void update(int stale) {
            //重置控件的状态,设置visibility属性，属性有3个分别为visible 可见，invisible 不可见，gone隐藏
            mLoading.setVisibility(View.GONE);
            mReload.setVisibility(View.GONE);
            switch (stale) {
                case LOADER_STATE_LOADING:
                    mLoading.setVisibility(View.VISIBLE);
                    //触发加载数据
                    startLoaderMore();
                    break;
                case LOADER_STATE_RELOAD:
                    mReload.setVisibility(View.VISIBLE);
                    break;
                case LOADER_STATE_NORMAL:
                    mLoading.setVisibility(View.GONE);
                    mReload.setVisibility(View.GONE);
                    break;
            }
        }

        private void startLoaderMore() {
            if (mUpPullRefreshListener != null) {
                mUpPullRefreshListener.onUpPullRefresh(this);
            }
        }
    }
}
