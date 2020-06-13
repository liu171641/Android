package com.example.recyclerviewtest.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.beans.MoreTypeBean;

import java.util.List;

/**
 * 作者 : 刘宇航
 * 邮箱 : 1716413010@qq.com
 * 日期  : 2020/5/12 18:55
 * 内容   :
 * 版本: 1.0
 */
public class MoreTypeAdapter extends RecyclerView.Adapter {
    //定义三个常量标识，因为我们有三种类型
    private static final int TYPE_FULL_IMAGE = 0;
    private static final int TYPE_RIGHT_IMAGE = 1;
    private static final int TYPE_THREE_IMAGES = 2;
    private final List<MoreTypeBean> mDta;

    public MoreTypeAdapter(List<MoreTypeBean> data) {
        this.mDta = data;
    }

    /**
     * 根据viewType来创建条目,这样条目就可以不一样了
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //这里去创建ViewHolder
        if (viewType == TYPE_FULL_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_type_full_image, null);
            return new FullImageHolder(view);
        } else if (viewType == TYPE_RIGHT_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_type_left_title_right_image, null);
            return new RightImageHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_type_three_image, null);
            return new ThreeImagesHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //TODO:这里就不设置数据了

    }

    @Override
    public int getItemCount() {
        if (mDta != null) {
            return mDta.size();
        }
        return 0;
    }

    //我们要复写一个方法，这个方法是根据条件来返回条目类型的
    @Override
    public int getItemViewType(int position) {
        MoreTypeBean moreTypeBean = mDta.get(position);
        if (moreTypeBean.type == 0) {
            return TYPE_FULL_IMAGE;
        } else if (moreTypeBean.type == 1) {
            return TYPE_RIGHT_IMAGE;
        } else {
            return TYPE_THREE_IMAGES;
        }
    }

    private class FullImageHolder extends RecyclerView.ViewHolder {

        public FullImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class ThreeImagesHolder extends RecyclerView.ViewHolder {

        public ThreeImagesHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder {

        public RightImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
