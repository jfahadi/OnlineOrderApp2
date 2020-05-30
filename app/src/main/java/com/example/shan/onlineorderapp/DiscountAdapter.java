package com.example.shan.onlineorderapp;

import android.content.Context;
import android.support.design.widget.Snackbar;
import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shan on 15-Feb-16.
 */
public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    public String[] mDataset;
    //Context
    private static Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView title;
        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            view = v;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar.make(view, "Search Products", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });
            title = (TextView) v.findViewById(R.id.card_title);
        }
    }

    public DiscountAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_discount, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText("Product Title " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
