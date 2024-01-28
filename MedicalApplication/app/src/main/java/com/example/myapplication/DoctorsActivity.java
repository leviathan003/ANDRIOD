package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorsActivity extends AppCompatActivity implements SpecialistsRecViewInterface{

    private TextView docInstruct;
    private RecyclerView docListView;

    protected void go(){
        startActivity(new Intent(this,SetAppointmentActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        Intent intent = getIntent();
        docInstruct = findViewById(R.id.docInstruct);
        String title = intent.getStringExtra("title");
        String docInstructDisp = "Available "+title;
        docInstruct.setText(docInstructDisp);

        docListView = findViewById(R.id.docRecListView);

        specialistsLists list = new specialistsLists();

        SpecialistsRecViewAdapter adapter = new SpecialistsRecViewAdapter(this,this);

        if(title.compareTo("Surgeons") == 0){
            adapter.setSpecialists(list.getSpecialistsSG());
        } else if (title.compareTo("Dietitians")==0) {
            adapter.setSpecialists(list.getSpecialistsDT());
        } else if (title.compareTo("Physicians")==0) {
            adapter.setSpecialists(list.getSpecialistsPH());
        } else if (title.compareTo("Neurologists")==0) {
            adapter.setSpecialists(list.getSpecialistsNL());
        } else if (title.compareTo("Gynecologists")==0) {
            adapter.setSpecialists(list.getSpecialistsGN());
        } else if (title.compareTo("Dentists")==0) {
            adapter.setSpecialists(list.getSpecialistsDN());
        }

        docListView.setAdapter(adapter);
        docListView.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public void onItemClick(int position,String docName) {
        Intent intent = new Intent(DoctorsActivity.this,SetAppointmentActivity.class);
        intent.putExtra("docName",docName);
        startActivity(intent);
    }
}