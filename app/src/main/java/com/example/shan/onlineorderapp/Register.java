package com.example.shan.onlineorderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {
    private String path ;
    private String uname = "", pass = "",role ="";
    private ProgressDialog progressDialog;
    EditText UserName;
    EditText Password;
    Button CreateAccount;
    Spinner Role;

    private static final String LOG_TAG = Register.class.getSimpleName();
    private int mOkay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         UserName=(EditText)findViewById(R.id.username);
         Password=(EditText)findViewById(R.id.password);
         CreateAccount =(Button)findViewById(R.id.createAccount);
        Role=(Spinner)findViewById(R.id.role);
        ArrayAdapter<CharSequence> Roleadapter = ArrayAdapter.createFromResource(this,
                R.array.role_array, android.R.layout.simple_spinner_item);
        Roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        Role.setAdapter(Roleadapter);


    }
    public void Create(View view){
        uname = UserName.getText().toString();
        pass = Password.getText().toString();
        role = Role.getSelectedItem().toString();


        new PostDataToServer().execute(uname, pass,role);
    }

    private class PostDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            path = getApplicationContext().getResources().getString(R.string.main_url) + "register.php";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String eventsJsonStr = null;
            try {
                // Construct the URL for the places query
                Log.v(LOG_TAG, "Now in the try catch of the post data Sync");
                final String USER_FETCH_BASE_URL = path;
                final String UserName = "uname";
                final String Password = "pass";
                final String Role ="role";

                Uri buildUri = Uri.parse(USER_FETCH_BASE_URL).buildUpon()
                        .appendQueryParameter(UserName, strings[0])
                        .appendQueryParameter(Password, strings[1])
                        .appendQueryParameter(Role, strings[2])
                        .build();
                //URL to be used
                URL url = new URL(buildUri.toString());
                //Print it to verbose log
                Log.v(LOG_TAG, "Built register  URL " + buildUri.toString());

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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }
        }

        private String getResponseDataFromJson(String json){
            Log.d(LOG_TAG, "In getResponseDataFromJson...");
            try {
                JSONObject responseJO = new JSONObject(json);
                int responseValue = responseJO.getInt("success");
                Log.d(LOG_TAG, "The value for success is: " + responseValue);
                if (responseValue == 1 ){
                    Log.d(LOG_TAG, "The value for success is: " + responseValue);
                    Intent intent = new Intent(getApplicationContext(),LoginActivity1.class);
                    startActivity(intent);
                    return "Account Created";
                }else {

                    Log.d(LOG_TAG, "The value for success is: " + responseValue);
                    return "Data not inserted";
                }
            }catch (JSONException jex){
                Log.d(LOG_TAG, "Error " + jex.getMessage());
            }


            return "Data not inserted";

        }

    }

}
