package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEntry,emailEntry,passEntry,passConfirmEntry;
    private Button registerBtn;

    private TextView loginHintTV;

    private jdbConnec db;

    private boolean passcheck(){
        int v1=0,v2=0,v3=0;
        String password = passEntry.getText().toString();
        if(password.length() < 8){
            return false;
        }
        else{
            for(int i=0;i<password.length();i++){
                if(Character.isLetter(password.charAt(i))){
                    v1=1;
                }
            }
            for(int i=0;i<password.length();i++){
                if(Character.isDigit(password.charAt(i))){
                    v2=1;
                }
            }
            for(int i=0;i<password.length();i++){
                char c = password.charAt(i);
                if(c>=33 && c<=46 || c==64){
                    v3=1;
                }
            }
            if(v1==1 && v2==1 && v3==1){
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        nameEntry = findViewById(R.id.registernameEntry);
        emailEntry = findViewById(R.id.registeremailEntry);
        passEntry = findViewById(R.id.registerpassEntry);
        passConfirmEntry = findViewById(R.id.registerpassConfirmEntry);

        registerBtn = findViewById(R.id.registerBtn);

        loginHintTV = findViewById(R.id.loginHint);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEntry.getText().toString().isEmpty() || emailEntry.getText().toString().isEmpty() || passEntry.getText().toString().isEmpty() ||
                passConfirmEntry.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!passEntry.getText().toString().equals(passConfirmEntry.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean check = false;
                        check = passcheck();
                        if(check==false){
                            Toast.makeText(RegisterActivity.this, "Password must to be 8 characters long, with digits, letters and " +
                                    "symbols", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            db = new jdbConnec(getApplicationContext(),"Medical",null,1);
                            db.register(nameEntry.getText().toString(),emailEntry.getText().toString(),passEntry.getText().toString());
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                    }
                }
            }
        });

        loginHintTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}