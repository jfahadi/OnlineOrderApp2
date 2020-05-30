package com.example.shan.onlineorderapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        String[] myStringArray={"Electronics","Fashion","Beauty","Mobile Phones And Tablets","Furniture","Kitchen Ware"};


        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray);
        ListView myList=
                (ListView) findViewById(R.id.categorylistView);
        myList.setAdapter(myAdapter);

    }//oncreate



}
