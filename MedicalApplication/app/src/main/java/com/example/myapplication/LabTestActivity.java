package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LabTestActivity extends AppCompatActivity {

    private CardView bloodTestCard,urineTestCard,semenTestCard,fecalTestCard,bloodpressCheckCard,bloodsugarCheckCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        bloodTestCard = findViewById(R.id.bloodTestCard);
        urineTestCard = findViewById(R.id.urineTestCard);
        semenTestCard = findViewById(R.id.semenTestCard);
        fecalTestCard = findViewById(R.id.fecalTestCard);
        bloodpressCheckCard = findViewById(R.id.bloodpressCheckCard);
        bloodsugarCheckCard = findViewById(R.id.bloodsugarCheckCard);

        bloodTestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestActivity.this,Test_CheckActivity.class);
                intent.putExtra("title","Blood Test");
                startActivity(intent);
            }
        });
        urineTestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestActivity.this,Test_CheckActivity.class);
                intent.putExtra("title","Urine Test");
                startActivity(intent);
            }
        });
        semenTestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestActivity.this,Test_CheckActivity.class);
                intent.putExtra("title","Semen Test");
                startActivity(intent);
            }
        });
        fecalTestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestActivity.this,Test_CheckActivity.class);
                intent.putExtra("title","Fecal Test");
                startActivity(intent);
            }
        });
        bloodpressCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestActivity.this,Test_CheckActivity.class);
                intent.putExtra("title","Blood Pressure Check");
                startActivity(intent);
            }
        });
        bloodsugarCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabTestActivity.this,Test_CheckActivity.class);
                intent.putExtra("title","Blood Sugar Check");
                startActivity(intent);
            }
        });
    }
}