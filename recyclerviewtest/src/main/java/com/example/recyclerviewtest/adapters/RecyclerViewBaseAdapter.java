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
 * 日期  : 2020/5/12 15:22
 * 内容   :RecyclerView适配器
 * 版本: 1.0
 */
public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //设置为protacted类型，不然子类用不到
    protected final List<ItemBean> mData;
    private OnItemClickListenr mOnItemClickListenr;

    public RecyclerViewBaseAdapter(List<ItemBean> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 这个方法是用于绑定holder的，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //在这里设置数据
        ((InnerHolder)holder).setData(mData.get(position), position);

    }

    /**
     * 返回条目的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListenner(OnItemClickListenr listenner) {
        //设置一个监听,其实,就是要设置一个接口,一个回调的接口
        this.mOnItemClickListenr = listenner;

    }

    /**
     * 编写回调的步骤
     * 1.创建这个接口
     * 2.定义接口内部的方法
     * 3.提供设置的方法(其实外部实现)
     * 4.接口方法的调用
     */
    public interface OnItemClickListenr {
        void onItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mTitle;
        private int mPosition;

        public InnerHolder(View itemView) {
            super(itemView);
            //找到条目的控件
            mIcon = itemView.findViewById(R.id.main_icon);
            mTitle = itemView.findViewById(R.id.main_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListenr != null) {
                        mOnItemClickListenr.onItemClick(mPosition);
                    }
                }
            });
        }

        /**
         * 这个方法用于设置数据
         */
        public void setData(ItemBean itemBean, int position) {

            this.mPosition = position;
            //开始设置数据
            mIcon.setImageResource(itemBean.icon);
            mTitle.setText(itemBean.title);

        }
    }
}
