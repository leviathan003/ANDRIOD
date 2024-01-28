package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FindDocsActivity extends AppCompatActivity {

    private CardView surgeonCard,dietitianCard,physicianCard,neuroCard,gyneaCard,dentistCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_docs);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        surgeonCard = findViewById(R.id.surgeonCard);
        dietitianCard = findViewById(R.id.dietecianCard);
        physicianCard = findViewById(R.id.physicianCard);
        neuroCard = findViewById(R.id.neurologistCard);
        gyneaCard = findViewById(R.id.gyneacologistCard);
        dentistCard = findViewById(R.id.dentistCard);

        surgeonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDocsActivity.this,DoctorsActivity.class);
                intent.putExtra("title","Surgeons");
                startActivity(intent);
            }
        });
        dietitianCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDocsActivity.this,DoctorsActivity.class);
                intent.putExtra("title","Dietitians");
                startActivity(intent);
            }
        });
        physicianCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDocsActivity.this,DoctorsActivity.class);
                intent.putExtra("title","Physicians");
                startActivity(intent);
            }
        });
        neuroCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDocsActivity.this,DoctorsActivity.class);
                intent.putExtra("title","Neurologists");
                startActivity(intent);
            }
        });
        gyneaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDocsActivity.this,DoctorsActivity.class);
                intent.putExtra("title","Gynecologists");
                startActivity(intent);
            }
        });
        dentistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDocsActivity.this,DoctorsActivity.class);
                intent.putExtra("title","Dentists");
                startActivity(intent);
            }
        });
    }
}