package com.example.shan.onlineorderapp;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DevExpress on 3/26/2016.
 */
public class mobileConfig {
    public static String footer=Today();
    public static String about="The Online Customer Order Mobile App is built to support customers in purchasing of Products from large retail stores in a flexible, " +
            "convenient and easy way.\n\n" +
            "It supports data(order) to be captured in text, pictures and enables sending to a central server for compilation" +
            " and processing.\n\n " +
            "Developed By Mugigayi.F & Nakisa.J\n[+256700697404 | +256700140286]";

    public static String Today() {
        String thisYear;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy");
        return  "© "+simpleDateFormat.format(calendar.getTime())+" Bam Shopping Center";
    }

   /* public static String CurrUser(Context curContext)
    {
      //  RequestDBHelper DB = new RequestDBHelper(curContext);
      //  return DB.GetCurrentUser();
    }*/

}
