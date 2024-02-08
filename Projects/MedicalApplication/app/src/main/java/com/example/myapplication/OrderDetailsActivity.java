package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetRVAdaptInterface{

    private ArrayList<OrderThumbnail> orderThumbnails=new ArrayList<>();
    private RecyclerView orderRV;
    private jdbConnec db;
    private TextView emptyOrders;

    @Override
    public void onClick(int position, String invoice) {
        Intent intent = new Intent(OrderDetailsActivity.this,OrderDetViewActivity.class);
        intent.putExtra("invoiceVal",invoice);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        orderRV = findViewById(R.id.orderRecView);
        db = new jdbConnec(getApplicationContext(),"Medical",null,1);
        orderThumbnails = db.getInvoiceDate();
        emptyOrders = findViewById(R.id.emptyOrders);

        if(orderThumbnails.size()==0){
            orderRV.setVisibility(View.INVISIBLE);
        }
        else{
            emptyOrders.setVisibility(View.INVISIBLE);
            OrderDetRecViewAdapter adapter = new OrderDetRecViewAdapter(this,orderThumbnails,this);
            orderRV.setAdapter(adapter);
            orderRV.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}