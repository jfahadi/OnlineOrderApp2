package com.example.shan.onlineorderapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Send_MessageActivity extends AppCompatActivity {
    Button send;
    EditText phone, sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_message);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        send = (Button) findViewById(R.id.buttonSend);
        phone = (EditText) findViewById(R.id.textPhone);
        sms = (EditText) findViewById(R.id.textSms);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                String message = sms.getText().toString();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber,null,message,null,null);
                    Toast.makeText(getApplicationContext(),"Sms message sent successfully",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Sms message failed!,please try again later",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }
}
