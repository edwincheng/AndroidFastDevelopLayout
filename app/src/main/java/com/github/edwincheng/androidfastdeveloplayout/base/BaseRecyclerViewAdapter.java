package com.github.edwincheng.androidfastdeveloplayout.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {
    private BaseRecyclerViewAdapter.OnItemClickListener mItemClickListener;
    private ArrayList dataList;
    private Context mContext;

    public final BaseRecyclerViewAdapter.OnItemClickListener getMItemClickListener() {
        return this.mItemClickListener;
    }

    public final void setMItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener var1) {
        this.mItemClickListener = var1;
    }

    public final List getDataList() {
        return this.dataList;
    }

    public final void setData(ArrayList dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                if(BaseRecyclerViewAdapter.this.getMItemClickListener() != null) {
                    BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener = BaseRecyclerViewAdapter.this.getMItemClickListener();
                    onItemClickListener.onItemClick(BaseRecyclerViewAdapter.this.getDataList().get(position), position);
                }
            }
        });
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public void setOnItemClickListener(@NotNull BaseRecyclerViewAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public Context getMContext() {
        return this.mContext;
    }

    public final void setMContext(@NotNull Context var1) {
        this.mContext = var1;
    }

    public BaseRecyclerViewAdapter(@NotNull Context mContext) {
        super();
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(Object data, int position);
    }
}
