package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText name_Entry, email_Entry, pass_Entry, pass_reEntry;
    private RadioGroup genderChoiceRG;
    private Spinner countryChoice;
    private CheckBox agreementBox;
    private Button profileImgBtn, submitBtn;

    private NestedScrollView parent;
    private String name_Val, email_Val, pass_Val, pass_reVal;
    private ArrayList<String> emailValidator, countryList;

    private int check1, check2, result;

    private int validator(String name, String email, String password, String re_password) {
        int flag = 0;
        if (email.isEmpty() || password.isEmpty() || re_password.isEmpty() || name.isEmpty()) {
            return 1;
        }
        if (!email.isEmpty()) {
            for (String e : emailValidator) {
                if (email.contains(e)) {
                    flag = 0;
                    break;
                } else {
                    flag = 3;
                }
            }
        }
        if (flag == 3) {
            return 3;
        }
        if (!password.equals(re_password)) {
            return 2;
        }
        return flag;
    }

    private int choiceCheck() {
        check2 = 0;
        final int[] flag = {0};
        if (genderChoiceRG.getCheckedRadioButtonId() == -1) {
            return 4;
        }
        countryChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(countryList.get(position) + " Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                flag[0] = 5;
            }
        });
        if (flag[0] == 5) {
            return 5;
        }
        if (!agreementBox.isChecked()) {
            return 6;
        }
        return check2;
    }

    private void showMessages(int check) {
        switch (check) {
            case 1:
                Toast.makeText(MainActivity.this, "All Fields are Compulsory", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(MainActivity.this, "Wrong Email Entry", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(MainActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                System.out.println("message for slider here!");
                break;
            case 6:
                Toast.makeText(MainActivity.this, "Please Agree to Terms and Conditions", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.mainLayout);

        profileImgBtn = findViewById(R.id.profileImageBtn);
        name_Entry = findViewById(R.id.nameEntry);
        email_Entry = findViewById(R.id.emailEntry);
        pass_Entry = findViewById(R.id.passwordEntry);
        pass_reEntry = findViewById(R.id.passwordreEntry);

        genderChoiceRG = findViewById(R.id.genderChoiceRG);

        countryChoice = findViewById(R.id.countryChoiceBox);
        countryList = new ArrayList<>();
        countryList.add("USA");
        countryList.add("Germany");
        countryList.add("India");
        countryList.add("Japan");
        countryList.add("China");
        countryList.add("Azerbaijan");
        countryList.add("Russia");
        countryList.add("South Africa");

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList);
        countryChoice.setAdapter(countryAdapter);

        agreementBox = findViewById(R.id.agreementBox);

        submitBtn = findViewById(R.id.submitBtn);

        emailValidator = new ArrayList<>();
        emailValidator.add("@gmail.com");
        emailValidator.add("@yahoo.com");

        profileImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Profile Picture Set", Toast.LENGTH_SHORT).show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_Val = name_Entry.getText().toString();
                email_Val = email_Entry.getText().toString();
                pass_Val = pass_Entry.getText().toString();
                pass_reVal = pass_reEntry.getText().toString();

                check1 = validator(name_Val, email_Val, pass_Val, pass_reVal);
                check2 = choiceCheck();
                if (check1 != 0) {
                    showMessages(check1);
                } else if (check1 == 0 && check2 != 0) {
                    System.out.println(check2);
                    showMessages(check2);
                } else if (check1 == 0 && check2 == 0) {
                    Snackbar.make(parent, "Registration/Login Successful", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Okay", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            }
        });
    }
}