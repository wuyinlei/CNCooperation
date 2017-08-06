package com.cainiao.common.widget.recycler;

/**
 * Created by wuyinlei on 2017/8/6.
 *
 * @function 刷新数据的方法
 */

public interface AdapterDataCallback<T> {


    /**
     * 进行更新操作
     *
     * @param data   当前数据类型
     * @param holder 当前RecyclerView的ViewHolder
     */
    void update(T data, RecyclerAdapter.AdapterViewHolder<T> holder);

}
