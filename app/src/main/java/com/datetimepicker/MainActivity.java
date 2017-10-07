package com.datetimepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.datetimepicker.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private ActivityMainBinding binding;

   private  String date_time = "";
    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);
    }

    public void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
//                        tiemPicker();

                        timePicker();
                    }
                },mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void timePicker(){
        // Get Current Time
        final Calendar mcurrentTime = Calendar.getInstance();
        mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinute = mcurrentTime.get(Calendar.MINUTE);

        final TimePickerDialog mTimePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                        mHour = hourOfDay;
//                        mMinute = minute;

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, minute);
                        if(!checkDate(date_time)){
                            int hour = hourOfDay % 12;
                            binding.tvDateTime.setText(date_time+" "+String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                    minute, hourOfDay < 12 ? "am" : "pm"));
                        }else{
                            if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                                //it's after current
                                int hour = hourOfDay % 12;
                                binding.tvDateTime.setText(date_time+" "+String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                        minute, hourOfDay < 12 ? "am" : "pm"));
                            } else {
                                //it's before current'
                                Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                            }
                        }


//                        binding.tvDateTime.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                },mHour, mMinute, false);

        mTimePicker.show();
    }


    private boolean checkDate(String my_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(my_date);
            if (System.currentTimeMillis() > strDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void testUpload(){
        System.out.println("Init commit");
    }


}
