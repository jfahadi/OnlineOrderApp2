package com.example.shan.onlineorderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Online_Registration extends AppCompatActivity {

    EditText First_Name ;
    EditText Second_Name;
    EditText OtherName;
    EditText DateOfBirth;
    Spinner SEX;
    Button SIGN_UP;
    TextView SignUp_Quesition ;
    private ProgressDialog progressDialog;
    private JSONObject json;
    private String path ;
    private String fname = "", sname = "", othername = "", date = "",  gender_ = "";

    private static final String LOG_TAG = Online_Registration.class.getSimpleName();
    private int mOkay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_registration);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


         First_Name = (EditText) findViewById(R.id.Nameregister);
         Second_Name = (EditText) findViewById(R.id.Nameregistertwo);
         OtherName = (EditText) findViewById(R.id.Nameregisterother);
         DateOfBirth = (EditText) findViewById(R.id.Dateofbirth);
         SEX =(Spinner)findViewById(R.id.sexspinner);
         SIGN_UP = (Button)findViewById(R.id.signupbutton);
         SignUp_Quesition = (TextView) findViewById(R.id.signup_question);


        ArrayAdapter<CharSequence> Genderadapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        Genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        SEX.setAdapter(Genderadapter);



    }//oncreate


    public void OnsignUp(View view){
        fname = First_Name.getText().toString();
        sname = Second_Name.getText().toString();
        othername = OtherName.getText().toString();
        gender_ = SEX.getSelectedItem().toString();
        date = DateOfBirth.getText().toString();



        new PostDataToServer().execute(fname, sname, othername,gender_,date);


    }
    public void loginpage(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity1.class);
        startActivity(intent);
    }

    private class PostDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Online_Registration.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            path = getApplicationContext().getResources().getString(R.string.main_url) + "signup.php";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String eventsJsonStr = null;
            try {
                // Construct the URL for the places query
                Log.v(LOG_TAG, "Now in the try catch of the post data Sync");
                final String USER_FETCH_BASE_URL = path;
                final String FIRST_NAME = "fname";
                final String SECOND_NAME = "sname";
                final String OTHER_NAME = "othername";
                final String GENDER = "gender_";
                final String DATE_OF_BIRTH = "date";

                Uri buildUri = Uri.parse(USER_FETCH_BASE_URL).buildUpon()
                        .appendQueryParameter(FIRST_NAME, strings[0])
                        .appendQueryParameter(SECOND_NAME, strings[1])
                        .appendQueryParameter(OTHER_NAME, strings[2])
                        .appendQueryParameter(GENDER, strings[3])
                        .appendQueryParameter(DATE_OF_BIRTH, strings[4])

                        .build();
                //URL to be used
                URL url = new URL(buildUri.toString());
                //Print it to verbose log
                Log.v(LOG_TAG, "Built singup  URL " + buildUri.toString());

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

                if(result==null){

                    Toast.makeText(Online_Registration.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }

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
                    Intent mainIntent = new Intent(Online_Registration.this,Register.class);
                    Online_Registration.this.startActivity(mainIntent);

                    return "Data inserted";
                }else if(responseValue == 0) {

                    Log.d(LOG_TAG, "The value for success is: " + responseValue);
                    return "Data not inserted";
                }else if (json.equalsIgnoreCase("exception") || json.equalsIgnoreCase("null")){

                    Toast.makeText(Online_Registration.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            }catch (JSONException jex){
                Log.d(LOG_TAG, "Error " + jex.getMessage());
                Toast.makeText(Online_Registration.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                 }


            return "OOPs! Something went wrong. Connection Problem.";


        }

    }
}
