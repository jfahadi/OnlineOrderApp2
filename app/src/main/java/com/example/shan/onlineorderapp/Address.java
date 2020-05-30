package com.example.shan.onlineorderapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Address extends AppCompatActivity {
    EditText Customer_Name;
    EditText Phone_Number;
    EditText Address1;
    EditText Address2;
    Spinner Region;
    Spinner Country;
    Spinner District;
    Button Proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.add_title);

        Customer_Name=(EditText)findViewById(R.id.order_name);
        Phone_Number=(EditText)findViewById(R.id.address_phone_number);
        Address1=(EditText)findViewById(R.id.address1_id);
        Address2=(EditText)findViewById(R.id.address2_id);
        Proceed=(Button)findViewById(R.id.button);


         Region = (Spinner) findViewById(R.id.region_spinner);
        Country = (Spinner) findViewById(R.id.country_spinner);
        District = (Spinner) findViewById(R.id.district_spinner);

        ArrayAdapter<CharSequence> Districtadapter = ArrayAdapter.createFromResource(this,
                R.array.district_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        Districtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        District.setAdapter(Districtadapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        Country.setAdapter(adapter);

        ArrayAdapter<CharSequence> Regionadapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        Regionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        Region.setAdapter(Regionadapter);

    }
    public void onProceed(View view){


    }

}
