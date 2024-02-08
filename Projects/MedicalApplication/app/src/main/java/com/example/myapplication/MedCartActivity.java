package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MedCartActivity extends AppCompatActivity {

    private ArrayList<Medicine> medList = new ArrayList<>();
    private Button checkoutBtn;
    private TableLayout bill;
    private TextView totalDisp,invoiceDisp;
    private jdbConnec db;
    private long invoice;
    private long invoiceGenerator(){
        Random random = new Random();
        long inv = random.nextInt(10000);
        return inv;
    }
    private int totalCalc(){
        int total=0;
        for(int i=0;i<medList.size();i++){
            total = total+(medList.get(i).getMedCost()*medList.get(i).getMedQty());
        }
        return total;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_cart);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        medList = (ArrayList<Medicine>) getIntent().getSerializableExtra("med_order_list");
        db = new jdbConnec(getApplicationContext(),"Medical",null,1);
        totalDisp = findViewById(R.id.totDisp);
        totalDisp.setText(String.valueOf(totalCalc()));
        invoiceDisp = findViewById(R.id.invoiceNumber);
        invoice = invoiceGenerator();
        if(db.invoiceCheck(String.valueOf(invoice))){
            while(!db.invoiceCheck(String.valueOf(invoice))){
                invoice = invoiceGenerator();
            }
        }
        invoiceDisp.setText(String.valueOf(invoice));
        checkoutBtn = findViewById(R.id.checkoutBtn);
        bill = (TableLayout) findViewById(R.id.billTable);
        for(int i=0;i<medList.size();i++){
            if(medList.get(i).getMedQty()!=0){
                TableRow row = new TableRow(this);
                TextView name = new TextView(this);
                name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                name.setText(medList.get(i).getMedName());
                name.setTextColor(ContextCompat.getColor(this, R.color.black));
                name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(name);
                TextView price = new TextView(this);
                price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                price.setText(String.valueOf(medList.get(i).getMedCost()));
                price.setTextColor(ContextCompat.getColor(this, R.color.black));
                price.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(price);
                TextView qty = new TextView(this);
                qty.setText(String.valueOf(medList.get(i).getMedQty()));
                qty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                qty.setTextColor(ContextCompat.getColor(this, R.color.black));
                qty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(qty);
                TextView total = new TextView(this);
                total.setText(String.valueOf(medList.get(i).getMedCost()*medList.get(i).getMedQty()));
                total.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                total.setTextColor(ContextCompat.getColor(this, R.color.black));
                total.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(total);
                bill.addView(row);
            }

            checkoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<medList.size();i++){
                        if(medList.get(i).getMedQty()!=0){
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String date = sdf.format(new Date());
                            db.inputDataInCart(String.valueOf(invoice),date,medList.get(i).getMedName(),String.valueOf(medList.get(i).getMedCost()),String.valueOf(medList.get(i).getMedQty()));
                        }
                    }
                    Toast.makeText(MedCartActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }
}