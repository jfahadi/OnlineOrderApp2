package com.example.shan.onlineorderapp;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

/**
 * Created by Shan on 11-Feb-20.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    public Cursor mDataset;

    Product product;
    //Context
    public static Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_code;
        public TextView title;
        public ImageView image;
        public TextView description;
        public TextView quantity;
        public  EditText Item_Qty;
        public ImageButton buy;
        public TextView price;
        public int item_price;
        public int User_Qty;
        public int code;
        public int Total_price;
        String productname,productdescription;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar.make(view, "Search Products", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });



            item_code = (TextView) v.findViewById(R.id.product_code);
            title = (TextView) v.findViewById(R.id.card_title);
            image = (ImageView) v.findViewById(R.id.ivFakadi);
            description = (TextView) v.findViewById(R.id.detail);
            quantity = (TextView) v.findViewById(R.id.qty_number);
            buy = (ImageButton) v.findViewById(R.id.buy);
            price = (TextView) v.findViewById(R.id.price);

            Item_Qty=(EditText)v.findViewById(R.id.qty_edit);


            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo, put your code to handle business logic here

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Purchase Product");
                    // Set up the input
                    //final EditText input = new EditText(context);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    //input.setInputType(InputType.TYPE_CLASS_NUMBER);
                   // input.setHint("Enter Quantity:");
                    //builder.setView(input);
                    //builder.setMessage("Add To Cart");
                    // Set up the buttons
                    builder.setPositiveButton("BUY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            code=Integer.parseInt(item_code.getText().toString());
                            item_price=Integer.parseInt(price.getText().toString()) ;
                            User_Qty= Integer.parseInt(Item_Qty.getText().toString()) ;
                            productname = title.getText().toString();
                            productdescription=description.getText().toString();
                            Total_price=User_Qty*item_price;

                           RequestDBHelper myDB = new RequestDBHelper(context);



                            //Toast.makeText(context,"Your Total Amount "+Total_price+"\nName "+ productname+"\nDescription "+productdescription+"\nQuantity Selected "+User_Qty, Toast.LENGTH_LONG).show();

                            Toast.makeText(context, myDB.AddData(code,productname,User_Qty,productdescription,Total_price), Toast.LENGTH_LONG).show();


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();


                }
            });
        }
    }

    public MyAdapter(Cursor mDataset) {
        this.mDataset = mDataset;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productcardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

         return new ViewHolder(productcardview);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        mDataset.moveToPosition(position);

        holder.item_code.setText(mDataset.getString(1));
        holder.title.setText(mDataset.getString(2));
        holder.quantity.setText(mDataset.getString(3));
       String path =  "http://192.168.43.48/oos/Pictures/" + mDataset.getString(6);
        // holder.image
        Picasso.with(context).load(path)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error_fallback)
                .into(holder.image);
        holder.description.setText(mDataset.getString(4));
        holder.price.setText(mDataset.getString(5));


}

    @Override
    public int getItemCount() {
        return mDataset.getCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

}
