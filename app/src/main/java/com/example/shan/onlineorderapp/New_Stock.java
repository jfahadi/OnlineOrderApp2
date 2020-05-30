package com.example.shan.onlineorderapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentActivity;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class New_Stock extends Fragment {
    private ProgressDialog progressDialog;
    String path;
    private static final String LOG_TAG = New_Stock.class.getSimpleName();
    private int  mOkay;
    SQLiteDatabase db;
    RequestDBHelper mDbHelper;
    public New_Stock() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.new__stock,container,false);





        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.my_recycler_view_new);



        rv.setLayoutManager(llm);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true); // to improve performance
        LatestAdapter adapter = new LatestAdapter(new String[8]);
        rv.setAdapter(adapter);

        return v;
    }

    private class FectchProducts extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("syncing products...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            path = getActivity().getResources().getString(R.string.main_url) + "send_product.php";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String eventsJsonStr = null;
            try {
                // Construct the URL for the places query
                Log.v(LOG_TAG, "Now in the try catch of the post data Sync");
                final String USER_FETCH_BASE_URL = path;

                Uri buildUri = Uri.parse(USER_FETCH_BASE_URL).buildUpon()
                        .build();
                //URL to be used
                URL url = new URL(buildUri.toString());
                //Print it to verbose log
                Log.v(LOG_TAG, "Built fetch product  URL " + buildUri.toString());

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


            if (mOkay == 200) {
                Log.d(LOG_TAG, "Calling getResponseDataFromJson now ");
                return getResponseDataFromJson(eventsJsonStr);
            } else {
                Log.d(LOG_TAG, "There was a problem with the server");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            }
        }

        private String getResponseDataFromJson(String json){
            Log.d(LOG_TAG, "In getResponseDataFromJson...");
            final String PRODUCT_CODE = "productcode";
            final String PRODUCT_NAME = "productname";
            final String PRODUCT_QUANTITY = "productquantity";
            final String PRODUCT_DESCRIPTION = "productdescription";
            final String PRODUCT_PRICE = "productprice";
            final String PRODUCT_IMAGE = "productimage";
            try {
                JSONArray productsJSONArray = new JSONArray(json);
                mDbHelper = new RequestDBHelper(getActivity());
                db = mDbHelper.getWritableDatabase();

                for(int counter = 0; counter < productsJSONArray.length(); counter++){

                    JSONObject productJSONObjet = productsJSONArray.getJSONObject(counter);

                    String code = productJSONObjet.getString(PRODUCT_CODE);
                    String name = productJSONObjet.getString(PRODUCT_NAME);
                    String quantity = productJSONObjet.getString(PRODUCT_QUANTITY);
                    String description = productJSONObjet.getString(PRODUCT_DESCRIPTION);
                    String price = productJSONObjet.getString(PRODUCT_PRICE);
                    String image = productJSONObjet.getString(PRODUCT_IMAGE);

                    ContentValues cv = new ContentValues();
                    cv.put("ItemCode",code);
                    cv.put("ItemName",name);
                    cv.put("Quantity",quantity);
                    cv.put("Description",description);
                    cv.put("SellingPrice",price);
                    cv.put("Image_Path", image);

                    db.insertWithOnConflict(RequestDBHelper.PRODUCT_TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                    Log.d(LOG_TAG, "Data inserted for " + counter);



                }
                db.close();
            }catch (JSONException jex){
                Log.d(LOG_TAG, "Error " + jex.getMessage());
            }


            return "Data not inserted";

        }

    }



}

