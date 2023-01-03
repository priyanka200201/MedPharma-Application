package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Appointment extends AppCompatActivity {

    Toolbar toolbar;
    Button book_btn;
    EditText nameedittext, phoneedittext, dateedittext, timeedittext;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameedittext = findViewById(R.id.nameEditText);
        phoneedittext = findViewById(R.id.phoneEditText);
        dateedittext = findViewById(R.id.dateEditText);
        timeedittext = findViewById(R.id.timeEditText);
        book_btn = findViewById(R.id.book_btn);

        dateedittext.setInputType(InputType.TYPE_NULL);
        timeedittext.setInputType(InputType.TYPE_NULL);
        dateedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(dateedittext);
            }
        });
        timeedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(timeedittext);
            }
        });

        dialog = new Dialog(Appointment.this);
        dialog.setContentView(R.layout.appointment_alert_dialog);
        dialog.setCancelable(false);

        Button okay = dialog.findViewById(R.id.okay);
        TextView nametext = dialog.findViewById(R.id.name_text);
        TextView phonetext = dialog.findViewById(R.id.phone_text);
        TextView datetext = dialog.findViewById(R.id.date_text);
        TextView timetext = dialog.findViewById(R.id.time_text);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String strname = nameedittext.getText().toString();
                nametext.setText("Name: "+strname);
                String strphone = phoneedittext.getText().toString();
                phonetext.setText("Phone no: "+strphone);
                String strdate = dateedittext.getText().toString();
                datetext.setText("Date: "+strdate);
                String strtime = timeedittext.getText().toString();
                timetext.setText("Time: "+strtime);
            }
        });
    }

    private void showTimeDialog(EditText time_in) {
        Calendar calendar=Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(Appointment.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }

    private void showDateDialog(EditText date_in) {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, date);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(Appointment.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        else if(id == R.id.main_cart_icon){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}