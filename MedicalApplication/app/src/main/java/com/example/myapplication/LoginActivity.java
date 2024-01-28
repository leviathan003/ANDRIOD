package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private EditText nameEntry,passEntry;
    private Button loginBtn;
    private TextView registerTV;

    private int valid;

    private jdbConnec db;
    private int validator(){
        if(nameEntry.getText().toString().isEmpty() || passEntry.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
            valid = 1;
        }
        else{
            valid = 0;
        }
        return valid;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        nameEntry = findViewById(R.id.loginnameEntry);
        passEntry = findViewById(R.id.loginpassEntry);
        loginBtn = findViewById(R.id.loginBtn);
        registerTV = findViewById(R.id.registerHint);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid = validator();
                if(valid == 0){
                    db = new jdbConnec(getApplicationContext(),"Medical",null,1);
                    if(db.loginVerify(nameEntry.getText().toString(),passEntry.getText().toString())==1){
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",nameEntry.getText().toString());
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Username or Password Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }
}