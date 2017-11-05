package com.example.caoduyminh.calendar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView mDisplayDate;
    private TextView mDisplayDOW;
    private TextView mDisplayYear;
    private TextView mDisplayDay;
    private TextView mDisplayTime;
    private TextView mDisplaydDat;
    private TextView mDisplayMonth;
    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDisplayDOW = (TextView) findViewById(R.id.showDOW);
        mDisplayDay = (TextView) findViewById(R.id.showDay);
        mDisplayYear = (TextView) findViewById(R.id.showYear);
        mDisplayMonth = (TextView) findViewById(R.id.showMonth);
        mDisplayDate = (TextView) findViewById(R.id.Curenttimedate);
        mDisplayTime = (TextView) findViewById(R.id.cTime);
        mDisplaydDat = (TextView) findViewById(R.id.date);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("     EEE dd / MM / yyyy");
        String dateString = sdf.format(date);
        mDisplayDate.setText(dateString.toString());

        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("    h:mm:ss a");
                                String dateString = sdf.format(date);
                                mDisplayTime.setText(dateString.toString());
                            }
                        });
                    }
                } catch (InterruptedException e){

                }
            }
        };
        t.start();

        mDisplaydDat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"onDateSet: dd/mm/yy: "+ day +"/" + month + "/" + year);
                int month1 = month;
                String date_day = "" + day;
                String date_month = "Tháng " + month;
                String date_year = "" + year;


                mDisplayMonth.setText(date_month);
                mDisplayYear.setText(date_year);
                mDisplayDay.setText(date_day);
                String d = "";
                if (date_day.length() < 2) date_day = "0" + date_day;
                if (month1 > 10) {d = day + "/" + month1 + "/" + year;}
                else {d = day + "/0" + month1 + "/" + year;}
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                Date dt1 = null;
                try {
                    dt1 = format1.parse(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format2 = new SimpleDateFormat("EEEE");
                String finalDay = format2.format(dt1);

                mDisplayDOW.setText(finalDay);
            }


        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toCalendar: {
                return true;
            }
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
