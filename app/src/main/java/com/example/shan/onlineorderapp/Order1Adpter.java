package com.example.shan.onlineorderapp;

/**
 * Created by Shan on 27-Apr-16.
 */import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.Snackbar;

import androidx.appcompat.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Order1Adpter extends RecyclerView.Adapter<Order1Adpter.ViewHolder> {
    static String name,descri;
    static int code,totalprice,qty;


    public Cursor mDataset;
    Cursor c;


    //Context
    public static Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public View view;

        public TextView lbl_ProductName;
        public TextView lbl_Description;
        public TextView lbl_Amount;
        public TextView lbl_Quatity;
        public TextView lbl_Productcode;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar.make(view, "Refresh Products", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });


            lbl_Productcode=(TextView)v.findViewById(R.id.lbl_Productcode);
            lbl_ProductName = (TextView) v.findViewById(R.id.lbl_ProductName);
            lbl_Description = (TextView) v.findViewById(R.id.lbl_Description);
            lbl_Quatity = (TextView) v.findViewById(R.id.lbl_Quatity);
            lbl_Amount= (TextView) v.findViewById(R.id.lbl_Amount);


        }
    }

    public Order1Adpter(Cursor mDataset) {

        Log.d("Log fakadi", "" + grandtotal(mDataset));
        //grandtotal(mDataset);

        this.mDataset = mDataset;

    }

    @Override
    public Order1Adpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);

        return new ViewHolder(cardview);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        mDataset.moveToPosition(position);

        holder.lbl_Productcode.setText(mDataset.getString(1));
        holder.lbl_ProductName.setText(mDataset.getString(2));
        holder.lbl_Amount.setText(mDataset.getString(3));
        holder.lbl_Description.setText(mDataset.getString(4));
        holder.lbl_Quatity.setText(mDataset.getString(5));


    /*
       //to capture the position before the context menu is loaded:
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // int position;


                PurchaseObject currentitem= new PurchaseObject();

                     c.getPosition();
                code=currentitem.code;
                name=currentitem.productname;
                qty=currentitem.User_Qty;
                descri=currentitem.productdescription;
                totalprice=currentitem.Total_price;

                return false;
            }
        });
        //to capture the position before the context menu is loaded:
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseObject currentitem= new PurchaseObject();

                        c.getPostion();
                qty=currentitem.User_Qty;

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mDataset.getCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int grandtotal(Cursor c){
        Log.d("Log fakadi", "we are in grandtotal method");
        int total = 0;

        //int counter = c.getCount();
        if(c != null ){
            for (int i = 0; i < c.getCount(); i++){
                c.moveToPosition(i);
                Log.d("Log fakadi", "we are looping in grandtotal method");

                total = total + Integer.parseInt(c.getString(5));
            }
        }


        Log.d("Log fakadi", "The total is: " + total);
        return  total;

    }

}

