package com.example.recyclerviewtest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtest.adapters.MoreTypeAdapter;
import com.example.recyclerviewtest.beans.MoreTypeBean;
import com.example.recyclerviewtest.utils.Datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者 : 刘宇航
 * 邮箱 : 1716413010@qq.com
 * 日期  : 2020/5/12 18:28
 * 内容   :多种条目类型
 * 版本: 1.0
 */
public class MoreTypeActivity extends AppCompatActivity {

    private static final String TAG = "MoreTypeActivity";
    private RecyclerView mRecyclerView;
    private List<MoreTypeBean> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_type_activity);

        //find the view here
        mRecyclerView = (RecyclerView) this.findViewById(R.id.more_type_list);

        //准备数据

        mData = new ArrayList<>();

        initDatas();

        //创建和设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //创建适配器
        MoreTypeAdapter adapter = new MoreTypeAdapter(mData);

        //设置适配器
        mRecyclerView.setAdapter(adapter);


    }

    private void initDatas() {
        Random random = new Random();

        for (int i = 0; i < Datas.Icons.length; i++) {
            MoreTypeBean data = new MoreTypeBean();
            data.pic = Datas.Icons[i];
            data.type = random.nextInt(3);
            Log.d(TAG, "type == " + data.type);
            mData.add(data);
        }
    }
}
