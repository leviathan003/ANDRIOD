package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppointmentsActivity extends AppCompatActivity {

    private ListView appointList;
    private TextView noOrders;
    private jdbConnec db;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        db = new jdbConnec(this,"Medical",null,1);
        appointments = db.getAppointments();
        noOrders = findViewById(R.id.emptyAppts);
        appointList = findViewById(R.id.appointList);

        if(appointments.size()==0){
            appointList.setVisibility(View.INVISIBLE);
        }
        else{
            noOrders.setVisibility(View.INVISIBLE);
            AppointmentListViewAdapter adapter = new AppointmentListViewAdapter(this,R.layout.appointment_list_item,appointments);
            appointList.setAdapter(adapter);
        }

    }
}