package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<Items> items;

    public MyAdapter(final List<Items> items) {
        this.items = items;
    }

    @Override public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.my_text_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String itemText = items.get(position).getContent();
        holder.tvItem.setText(itemText);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public List<Items> getItems() {
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
