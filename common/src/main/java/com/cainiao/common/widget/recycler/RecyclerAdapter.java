package com.cainiao.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wuyinlei on 2017/8/6.
 *
 * @function 封装的adapter
 */

public abstract class RecyclerAdapter<T> extends
        RecyclerView.Adapter<RecyclerAdapter.AdapterViewHolder> implements AdapterDataCallback<T> {

    private final List<T> mDatas = new ArrayList<>();

    @Override
    public RecyclerAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //把xml的id为viewType的转换为一个root view
        View root = inflater.inflate(viewType, parent, false);
        AdapterViewHolder<T> holder = onCreateViewHolder(root, viewType);
        root.setTag(R.id.view_tag, holder);
        holder.mCallBack = this;
        holder.mUnbinder = ButterKnife.bind(holder, root);

        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 就是xml的id
     * @return ViewHolder
     */
    protected abstract AdapterViewHolder<T> onCreateViewHolder(View root, int viewType);


    @Override
    public void onBindViewHolder(RecyclerAdapter.AdapterViewHolder holder, int position) {
        T data = mDatas.get(position);//得到需要绑定的数据
        holder.bindData(data);   ////绑定方法 要不然没有数据进行触发
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position, mDatas.get(position));
    }


    //得到布局的类型  并且返回改布局需要显示的数据
    protected abstract int getItemType(int position, T t);


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 获取到当前的数据
     *
     * @return List<T></>
     */
    public List<T> getItems() {
        return mDatas;
    }

    /**
     * 插入一条数据并通知刷新
     *
     * @param data data
     */
    public void add(T data) {
        mDatas.add(data);
        notifyItemInserted(mDatas.size() - 1);
    }

    /**
     * 插入一堆数据 并通知这段数据刷新
     *
     * @param dataes datas
     */
    @SafeVarargs
    public final void add(T... dataes) {
        if (dataes != null && dataes.length > 0) {
            int startPos = mDatas.size();
            Collections.addAll(mDatas, dataes);
            notifyItemRangeChanged(startPos, dataes.length);
        }
    }


    @Override
    public void update(T data, AdapterViewHolder<T> holder) {
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            mDatas.remove(pos);
            mDatas.add(pos, data);
            notifyItemChanged(pos);
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了情况
     *
     * @param datas 一个新的集合
     */
    public void replace(Collection<T> datas) {
        mDatas.clear();
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    public static abstract class AdapterViewHolder<T> extends RecyclerView.ViewHolder {

        private Unbinder mUnbinder;

        protected T data;

        private AdapterDataCallback<T> mCallBack;


        public AdapterViewHolder(View itemView) {
            super(itemView);
        }


        public abstract void bindData(T data);

        /**
         * 更新 自己主动刷新
         *
         * @param data 数据
         */
        public void updateData(T data) {
            if (this.mCallBack != null) {
                mCallBack.update(data, this);
            }
        }


    }


}
