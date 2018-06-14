package com.example.rupali.thalassaemiaapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThalassaemicsReg extends AppCompatActivity {
    EditText name;
    EditText phoneNo;
    EditText email;
    EditText dob;
    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;
    String[] month;
    String selectedDay;
    String selectedMonth;
    String selectedYear;
    ArrayList<String> years;
    ArrayList<String> days;
    ArrayAdapter<String> dayAdapter;
    ArrayAdapter<String> monthAdapter;
    ArrayAdapter<String> yearAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thalassaemics_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        daySpinner=findViewById(R.id.day);
        monthSpinner=findViewById(R.id.month);
        yearSpinner=findViewById(R.id.year);
        dob=findViewById(R.id.patient_dob);
        email=findViewById(R.id.patient_email);
        phoneNo=findViewById(R.id.patient_phone);
        days=new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        month=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};

        years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        dayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,days);
        daySpinner.setAdapter(dayAdapter);
        monthAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,month);
        monthSpinner.setAdapter(monthAdapter);
        yearAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,years);
        yearSpinner.setAdapter(yearAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDay=daySpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedDay=adapterView.getSelectedItem().toString();
            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos=adapterView.getSelectedItemPosition()+1;
                DecimalFormat formatter = new DecimalFormat("00");
                selectedMonth=formatter.format(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedMonth=adapterView.getSelectedItemPosition()+"";

            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYear=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedYear=adapterView.getSelectedItem().toString();
            }
        });
//        dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerFragment newFragment = new DatePickerFragment();
//                newFragment.show(getSupportFragmentManager(),"datePicker");
//            }
//        });

    }

}
