package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BuyMedsActivity extends AppCompatActivity{
    private ArrayList<Medicine> medicineList = new ArrayList<>();
    private ListView medList;
    private TextView totalCost;
    private Button cartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_meds);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        medList = findViewById(R.id.cartItemView);
        medsList meds = new medsList();
        medicineList = meds.getMedicineArrayList();
        medList.setAdapter(new MedicineListAdapter(this,R.layout.medicine_list_item,medicineList));
        cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flg=0;
                for(int i=0;i<medicineList.size();i++){
                    if(medicineList.get(i).getMedQty()!=0){
                        flg=1;
                        break;
                    }
                }
                if(flg==1){
                    Intent intent = new Intent(BuyMedsActivity.this,MedCartActivity.class);
                    intent.putExtra("med_order_list",medicineList);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(BuyMedsActivity.this, "Please Choose atleast one item\nto proceed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}