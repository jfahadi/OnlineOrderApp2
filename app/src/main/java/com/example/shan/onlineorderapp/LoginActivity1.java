package com.example.shan.onlineorderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.plus.Plus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity1 extends AppCompatActivity {
    GoogleApiClient mGoogleApiClient;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText Username;
    private EditText Password;
    private static final String LOG_TAG = LoginActivity1.class.getSimpleName();

    private String path ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       //setSupportActionBar(toolbar);

         Username = (EditText) findViewById(R.id.username_login);
         Password = (EditText) findViewById(R.id.password_login);
        Button SIGN_IN = (Button) findViewById(R.id.login_button);
        Button needAccount = (Button) findViewById(R.id.need_account_button);
        needAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loadsignup = new Intent(LoginActivity1.this,Online_Registration.class);
                LoginActivity1.this.startActivity(loadsignup);
            }
        });



}
    public void OnLogin(View view) {

        int fahad = view.getId();
        switch (fahad){
            case R.id.login_button:

                // Get text from username and password field
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();

                // Initialize  AsyncLogin() class with username and password
                new AsyncLogin().execute(username,password);

                break;

            case R.id.need_account_button:
                Intent loadsignup = new Intent(LoginActivity1.this,Online_Registration.class);
                LoginActivity1.this.startActivity(loadsignup);


        }



    }

    private class AsyncLogin extends AsyncTask<String, Void, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity1.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLogging...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.v(LOG_TAG, "querying url");


                // Enter URL address where your php file resides
                path = getApplicationContext().getResources().getString(R.string.main_url) + "signin.php";
                url = new URL(path);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {

                Log.v(LOG_TAG, "Now in the try catch of the post data Sync");

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                Log.v(LOG_TAG, "Built signin URL " + builder.toString());

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{
                    Log.d(LOG_TAG, "The connection has failed");

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
           // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent loadmainActivity = new Intent(LoginActivity1.this, Navigation_Drawer.class);
                LoginActivity1.this.startActivity(loadmainActivity);

                Toast.makeText(LoginActivity1.this,"Signing in...",Toast.LENGTH_LONG).show();


            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(LoginActivity1.this, "Invalid username or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(LoginActivity1.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
}
