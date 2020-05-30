package com.example.shan.onlineorderapp;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Shopping_cart extends AppCompatActivity {


    private static final String LOG_TAG = Shopping_cart.class.getSimpleName();
    private int mOkay;
    public SQLiteDatabase db;
    public RequestDBHelper mDbHelper;
    private ProgressDialog progressDialog;
    public Order1Adpter adapter;
    public String path;
    public TextView GrandTotal;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         toolbar.setTitle(R.string.title_shopping);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        GrandTotal = (TextView)findViewById(R.id.totaltextView) ;
        Refresh();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.cmdRefresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (packageCartItemsIntoJSON() != null){
                  String fakadi = packageCartItemsIntoJSON();
                    new PostDataToServer().execute(fakadi);
                }else {
                    Toast.makeText(getBaseContext(), "The cart has no items", Toast.LENGTH_LONG).show();
                }

              //  GrandTotal.setText("Your Grand Total is: ");

               // Toast.makeText(getBaseContext(), "Submit Order", Toast.LENGTH_LONG).show();

            }
        });

    }

    void Refresh() {
       getOrderFromCart();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        adapter.notifyDataSetChanged();

    }


    public void getOrderFromCart() {

        String selectQuery = "SELECT  * FROM " + RequestDBHelper.PURCHASED_PRODUCT;
        mDbHelper = new RequestDBHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            Log.d(LOG_TAG, "The Fakadiz kasa hazi data");
            adapter = new Order1Adpter(c);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            Log.d(LOG_TAG, "The Fakadiz kasa hazi not data");

        }


    }

    public String packageCartItemsIntoJSON() {

        String selectQuery = "SELECT  * FROM " + RequestDBHelper.PURCHASED_PRODUCT;
        mDbHelper = new RequestDBHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            JSONArray orderarray = new JSONArray();
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToPosition(i);
                try{
                    JSONObject orderjobj = new JSONObject();
                    orderjobj.put("code", cursor.getString(1));
                    orderjobj.put ("name", cursor.getString(2));
                    orderjobj.put("quantity", cursor.getString(3));
                    orderjobj.put("description", cursor.getString(4));
                    orderjobj.put("amount", cursor.getString(5));

                    orderarray.put(orderjobj);

                }catch (JSONException jex){
                    Log.d(LOG_TAG, "Some prob: " + jex.getMessage().toString() );
                }

            }




            Log.d(LOG_TAG, "Wow Fakadi has a json array into a string: " + orderarray.toString() );
            return orderarray.toString();
        }else {
            return  null;
        }

    }

 /*   public String DeleteItem() {
        Log.v(LOG_TAG, "Now in delete method");

        String selectQuery = "DELETE FROM " + RequestDBHelper.PURCHASED_PRODUCT;
        mDbHelper = new RequestDBHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();
        db.rawQuery(selectQuery, null);

        return "Deletion Completed";
    }*/

    private class PostDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Shopping_cart.this);
            progressDialog.setMessage("Submitting Order.\nPlease wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            path = getApplicationContext().getResources().getString(R.string.main_url) + "order.php";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String eventsJsonStr = null;
            try {
                // Construct the URL for the places query
                Log.v(LOG_TAG, "Now in the try catch of the post data Sync");
                final String USER_FETCH_BASE_URL = path;
                final String SUBMITORDER = "order";


                Uri buildUri = Uri.parse(USER_FETCH_BASE_URL).buildUpon()
                        .appendQueryParameter(SUBMITORDER, strings[0])

                        .build();
                //URL to be used
                URL url = new URL(buildUri.toString());
                //Print it to verbose log
                Log.v(LOG_TAG, "Built oder  URL " + buildUri.toString());

                // Create the request, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(30000);
                urlConnection.connect();
                //Get some header info.
                mOkay = urlConnection.getResponseCode();
                Log.d(LOG_TAG, "The response code is: " + mOkay);
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    eventsJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    Log.d(LOG_TAG, "The placeJsonStr is null!");
                    eventsJsonStr = null;
                    // return null;
                }
                //Assign value to the variable to hold the returned data
                eventsJsonStr = buffer.toString();
                //Log.d(LOG_TAG, "Fetched Places Data: " + eventsJsonStr);

            } catch (java.net.SocketTimeoutException jnste) {
                Log.d(LOG_TAG, "The connection has timed out after 30sec");
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attemping
                eventsJsonStr = null;
                // return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            Log.d(LOG_TAG, "The response is " + eventsJsonStr);


            String success = "Invalid email or password";
            if (mOkay == 200) {
                Log.d(LOG_TAG, "Calling getResponseDataFromJson now ");
                return getResponseDataFromJson(eventsJsonStr);
            } else {
                Log.d(LOG_TAG, "There was a problem with the server");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

                if (response == null) {

                    Toast.makeText(Shopping_cart.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }

            }

        }

        private String getResponseDataFromJson(String json) {
            Log.d(LOG_TAG, "In getResponseDataFromJson...");
            try {
                JSONObject responseJO = new JSONObject(json);
                int responseValue = responseJO.getInt("success");
                Log.d(LOG_TAG, "The value for success is: " + responseValue);
                if (responseValue == 1) {
                    Log.d(LOG_TAG, "The value for success is: " + responseValue);
                    //delete data
                    RequestDBHelper DB = new RequestDBHelper(getApplicationContext());
                    DB.DeleteItem();

                    return "Data inserted";
                } else if (responseValue == 0) {

                    Log.d(LOG_TAG, "The value for success is: " + responseValue);
                    return "Data not inserted";
                } else if (json.equalsIgnoreCase("exception") || json.equalsIgnoreCase("null")) {

                    Toast.makeText(Shopping_cart.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException jex) {
                Log.d(LOG_TAG, "Error " + jex.getMessage());
                Toast.makeText(Shopping_cart.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }


            return "OOPs! Something went wrong. Connection Problem.";


        }

    }

}


