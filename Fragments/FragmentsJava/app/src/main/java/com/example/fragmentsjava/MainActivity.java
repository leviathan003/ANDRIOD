package com.example.fragmentsjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btnA);
        btn2 = findViewById(R.id.btnB);
        btn3 = findViewById(R.id.btnC);

        fragmentGet(new BFragment(),0);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentGet(new AFragment(),1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentGet(new BFragment(),1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentGet(new CFragment(),1);
            }
        });
    }
    private void fragmentGet(Fragment fr,int flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag==0){
            ft.add(R.id.fragmentContainer, fr);
        }else{
            ft.replace(R.id.fragmentContainer, fr);
        }
        ft.commit();
    }

}