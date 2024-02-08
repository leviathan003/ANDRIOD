package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Test_CheckActivity extends AppCompatActivity {

    private TextView testInstruct,type,collectionMode,basicFee,extraFee,totalFee;
    private RadioGroup timeSlot,collectMode;
    private CalendarView dateEntry;
    private String date;
    private RadioButton timeDisp,collectModeDisp;
    private Button checkoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_check);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        Intent intent = getIntent();
        testInstruct = findViewById(R.id.testCheckInstruct);
        testInstruct.setText(intent.getStringExtra("title"));

        type = findViewById(R.id.testTypeResult);
        collectionMode = findViewById(R.id.collecModeResult);
        basicFee = findViewById(R.id.baseFeeResult);
        extraFee = findViewById(R.id.homeFeeResult);
        dateEntry = findViewById(R.id.dateEntry);
        timeSlot = findViewById(R.id.timeslotRG);
        collectMode = findViewById(R.id.collecModeRG);
        totalFee = findViewById(R.id.totalResult);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        type.setText(intent.getStringExtra("title"));
        collectionMode.setText("Walk-in/At Home");
        basicFee.setText("200");
        extraFee.setText("40");
        totalFee.setText(basicFee.getText());

        collectMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.atHomeRadio){
                    totalFee.setText(String.valueOf(Integer.parseInt(basicFee.getText().toString())+Integer.parseInt(extraFee.getText().toString())));
                }
                else if(checkedId==R.id.walkinRadio){
                    if(Integer.parseInt(totalFee.getText().toString()) == (Integer.parseInt(basicFee.getText().toString())+Integer.parseInt(extraFee.getText().toString()))){
                        totalFee.setText(basicFee.getText().toString());
                    }
                }
            }
        });
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateEntry.getDate()==0 || timeSlot.getCheckedRadioButtonId()==-1 || collectMode.getCheckedRadioButtonId()==-1){
                    Toast.makeText(Test_CheckActivity.this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    dateEntry.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                            date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        }
                    });
                    if (date == null) {
                        Toast.makeText(Test_CheckActivity.this, "Please Select A Date", Toast.LENGTH_SHORT).show();
                    } else {
                        timeDisp = findViewById(timeSlot.getCheckedRadioButtonId());
                        Snackbar.make(Test_CheckActivity.this, v, "Test/Check scheduled on Date: " + date + " Time: " + timeDisp.getText(), Snackbar.LENGTH_INDEFINITE).setAction("Okay", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).setActionTextColor(ContextCompat.getColor(Test_CheckActivity.this, R.color.btnBG)).show();
                    }
                }
            }
        });
    }
}