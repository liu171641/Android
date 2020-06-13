package com.example.recyclerviewtest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.recyclerviewtest.adapters.GridViewAdapter;
import com.example.recyclerviewtest.adapters.ListViewAdapter;
import com.example.recyclerviewtest.adapters.RecyclerViewBaseAdapter;
import com.example.recyclerviewtest.adapters.StaggerAdapter;
import com.example.recyclerviewtest.beans.ItemBean;
import com.example.recyclerviewtest.utils.Datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.text.DateFormat.DEFAULT;


/**
 * 总结：
 * 1.首先我们要有控件，这个RecyclerView它呢是在Containers里面
 * 2.找到控件
 * 3.准备好数据
 * 4.设置布局管理器
 * 5.创建适配器
 * 6.设置适配器
 * 7.数据就可以实现出来了
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mList;
    private List<ItemBean> mData;
    private RecyclerViewBaseAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        mList = (RecyclerView) this.findViewById(R.id.recycle_view);
        mRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.refresh_layout);
        //准备数据
        /**
         * 准备数据,一般来说,我们在现实开发中,我们的数据是从网络中获取的,这里面只是演示。
         * 所以我们只是模拟数据,在现实开发中也是要模拟数据的,比如说,后台没有准备好的时候。
         */
        initData();
        //设置默认的显示样式为ListView的效果
        showList(true, false);

        //处理下啦刷新
        handlerDownPullUpdate();
    }

    private void handlerDownPullUpdate() {
        mRefreshLayout.setEnabled(true);
        //设置颜色,一秒显示一个颜色
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        //设置进度View样式的大小，只有两个值DEFAULT和LARGE，表示默认和较大
        mRefreshLayout.setSize(DEFAULT);
        //设置触发下拉刷新的距离
        mRefreshLayout.setDistanceToTriggerSync(300);
        //设置动画样式下拉的起始点和结束点，scale 是指设置是否需要放大或者缩小动画。
        mRefreshLayout.setProgressViewOffset(true, 0, 200);
        //设置动画样式下拉的结束点，scale 是指设置是否需要放大或者缩小动画
        mRefreshLayout.setProgressViewEndTarget(true, 200);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里面去执行刷新数据的操作
                /**
                 * 当我们在顶部,下拉的时候 ,这个方法就会被出发
                 * 但是,这个方法是MainThread是主线程,不可以执行耗时操作。
                 * 一般来说,我们去请求数据在开一个线程去获取
                 * //这里面演示的话,我直接添加一条数据
                 */
                //添加数据
                ItemBean data = new ItemBean();
                data.title = "我是新添加的数据...";
                data.icon = R.mipmap.pic_09;
                mData.add(0, data);
                //更新UI界面
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                        //让适配器更新列表
                        mAdapter.notifyDataSetChanged();
                        //取消刷新动画
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 3800);
            }
        });

    }

    private void initListener() {

        mAdapter.setOnItemClickListenner(new RecyclerViewBaseAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(int position) {
                //这里处理条目的点击事件,该干嘛就干嘛,跳转的就跳转...
                Toast.makeText(MainActivity.this, "您点击的是第" + position + "个条目", Toast.LENGTH_SHORT).show();
            }
        });

        //这里面去处理上拉加载更多
        //instanceof 运算符用于检测mAdapter的构造函数的 prototype 属性是否出现在ListAdapter的原型链上
        //instanceof是Java的一个二元操作符，例如==，>，<等操作符。
        //instanceof是Java的保留关键字。它的作用是测试它左边的对象是否是它右边的类的实例，返回boolean的数据类型。
        if (mAdapter instanceof ListViewAdapter) {
            ((ListViewAdapter) mAdapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    //这里面去加载更多的数据,同样,需要在子线程中完成,这里仅作演示

                    //更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Random random = new Random();

                            if (random.nextInt() % 2 == 0) {
                                ItemBean data = new ItemBean();
                                data.title = "我是新添加的数据...";
                                data.icon = R.mipmap.pic_09;
                                mData.add(data);

                                //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表

                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_RELOAD);
                            }

                        }
                    }, 3000);
                }
            });
        }
    }

    /**
     * 这个方法用于模拟数据
     */
    private void initData() {
        //List<DataBea>---->Adapter------>setAdapter------>显示数据。
        //创建数据集合
        mData = new ArrayList<>();

        //创建模拟数据
        for (int i = 0; i < Datas.Icons.length; i++) {
            //创建数据对象
            ItemBean data = new ItemBean();
            data.icon = Datas.Icons[i];
            data.title = "我是第 " + i + " 个条目";
            //添加到集合里头
            mData.add(data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            //ListView部分
            case R.id.list_view_vertical_stander:
                //垂直标准
                showList(true, false);
                break;
            case R.id.list_view_vertical_reverse:
                //垂直反向
                showList(true, true);
                break;
            case R.id.list_view_horizontal_stander:
                //水平标准
                showList(false, false);
                break;
            case R.id.list_view_horizontal_reverse:
                //水平反向
                showList(false, true);
                break;

            //GridView部分
            case R.id.grid_view_vertical_stander:
                //垂直标准
                showGrid(true, false);
                break;
            case R.id.grid_view_vertical_reverse:
                //垂直反向
                showGrid(true, true);
                break;
            case R.id.grid_view_horizontal_stander:
                //水平标准
                showGrid(false, false);
                break;
            case R.id.grid_view_horizontal_reverse:
                //水平反向
                showGrid(false, true);
                break;

            //瀑布流部分
            case R.id.stagger_view_vertical_stander:
                //垂直标准
                showStagger(true, false);
                break;
            case R.id.stagger_view_vertical_reverse:
                //垂直反向
                showStagger(true, true);
                break;
            case R.id.stagger_view_horizontal_stander:
                //水平标准
                showStagger(false, false);
                break;
            case R.id.stagger_view_horizontal_reverse:
                //水平反向
                showStagger(false, true);
                break;

            //多种条目类型被点击了
            case R.id.more_type:
                //跳转到一个新的Activity里去实现这个功能
                Intent intent = new Intent(MainActivity.this, MoreTypeActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 这个方法用于实现瀑布流的效果
     */
    private void showStagger(boolean isVertical, boolean isReverse) {
        //准备布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        //设置布局管理器的方向
        layoutManager.setReverseLayout(isReverse);

        //设置布局管理器到RecyclerView里
        mList.setLayoutManager(layoutManager);

        //创建适配器
        mAdapter = new StaggerAdapter(mData);

        //设置适配器
        mList.setAdapter(mAdapter);

        //创建监听事件
        initListener();

    }

    /**
     * 这个方法用于实现和GridView一样的效果
     */
    private void showGrid(boolean isVertical, boolean isReverse) {

        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        //设置标准(正向)还是反向的
        layoutManager.setReverseLayout(isReverse);

        //设置布局管理器
        mList.setLayoutManager(layoutManager);

        //创建适配器
        mAdapter = new GridViewAdapter(mData);

        //设置适配器
        mList.setAdapter(mAdapter);

        //创建监听事件
        initListener();
    }

    /**
     * 这个方法用于现实ListView一样的效果
     */
    private void showList(boolean isVertical, boolean isReverse) {
        //RecyclerView需要设置样式,其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器来控制
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置标准(正向)还是反向的
        layoutManager.setReverseLayout(isReverse);

        mList.setLayoutManager(layoutManager);
        //创建适配器
        mAdapter = new ListViewAdapter(mData);
        //设置到RecyclerView里头
        mList.setAdapter(mAdapter);

        //创建监听事件
        initListener();
    }
}
