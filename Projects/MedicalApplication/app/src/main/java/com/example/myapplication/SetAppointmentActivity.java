package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SetAppointmentActivity extends AppCompatActivity {

    private CalendarView dateEntry;
    private Button confirmBtn;
    private String docName;
    private RadioButton timeDisp;
    private RadioGroup timeEntryRG;
    private String date;

    private jdbConnec db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        dateEntry = findViewById(R.id.dateEntry);
        confirmBtn = findViewById(R.id.confirmBtn);
        timeEntryRG = findViewById(R.id.timeslotRG);
        db = new jdbConnec(getApplicationContext(),"Medical",null,1);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateEntry.getDate()==0 || timeEntryRG.getCheckedRadioButtonId()==-1){
                    Toast.makeText(SetAppointmentActivity.this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    dateEntry.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                            date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        }
                    });
                    if (date == null) {
                        Toast.makeText(SetAppointmentActivity.this, "Please Select A Date", Toast.LENGTH_SHORT).show();
                    } else {
                        timeDisp = findViewById(timeEntryRG.getCheckedRadioButtonId());
                        db.inputAppointments(getIntent().getStringExtra("docName"),date,timeDisp.getText().toString());
                        Snackbar.make(SetAppointmentActivity.this, v, "Appointment set for\nDate: " + date + " Time: " + timeDisp.getText(), Snackbar.LENGTH_INDEFINITE).setAction("Okay", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).setActionTextColor(ContextCompat.getColor(SetAppointmentActivity.this, R.color.btnBG)).show();
                    }
                }
            }
        });

    }
}