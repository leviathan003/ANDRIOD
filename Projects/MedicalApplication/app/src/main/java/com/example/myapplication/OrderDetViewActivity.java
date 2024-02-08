package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderDetViewActivity extends AppCompatActivity {

    private TextView invoiceVal,totalDisp,orderDateDisp;
    private TableLayout bill;
    private jdbConnec db;
    private ArrayList<Order> orders=new ArrayList<>();
    private Button cancelBtn;

    private int totalCalc(){
        int total=0;
        for(int i=0;i<orders.size();i++){
            total = total+(Integer.parseInt(orders.get(i).getPrice())*Integer.parseInt(orders.get(i).getQty_ordered()));
        }
        return total;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_det_view);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        db = new jdbConnec(this,"Medical",null,1);
        invoiceVal = findViewById(R.id.invoiceNumber);
        bill = findViewById(R.id.billTable);
        orders = db.getOrderDetails(getIntent().getStringExtra("invoiceVal"));
        totalDisp = findViewById(R.id.totDisp);
        orderDateDisp = findViewById(R.id.orderDateVal);
        orderDateDisp.setText(orders.get(0).getOrder_date());
        cancelBtn = findViewById(R.id.cancelBtn);

        invoiceVal.setText(getIntent().getStringExtra("invoiceVal"));
        for(int i=0;i<orders.size();i++){
            TableRow row = new TableRow(this);
            TextView name = new TextView(this);
            name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
            name.setText(orders.get(i).getProd_name());
            name.setTextColor(ContextCompat.getColor(this, R.color.black));
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(name);
            TextView price = new TextView(this);
            price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
            price.setTextColor(ContextCompat.getColor(this, R.color.black));
            price.setText(String.valueOf(orders.get(i).getPrice()));
            price.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(price);
            TextView qty = new TextView(this);
            qty.setTextColor(ContextCompat.getColor(this, R.color.black));
            qty.setText(String.valueOf(orders.get(i).getQty_ordered()));
            qty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
            qty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(qty);
            TextView total = new TextView(this);
            total.setText(String.valueOf(Integer.parseInt(orders.get(i).getPrice())*Integer.parseInt(orders.get(i).getQty_ordered())));
            total.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
            total.setTextColor(ContextCompat.getColor(this, R.color.black));
            total.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(total);
            bill.addView(row);
        }
        totalDisp.setText(String.valueOf(totalCalc()));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delOrder(getIntent().getStringExtra("invoiceVal"));
                Toast.makeText(OrderDetViewActivity.this, "Order Cancelled", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrderDetViewActivity.this,OrderDetailsActivity.class));
                finish();
            }
        });
    }
}