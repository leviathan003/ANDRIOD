package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetRecViewAdapter extends RecyclerView.Adapter<OrderDetRecViewAdapter.ViewHolder> {

    private ArrayList<OrderThumbnail> orderThumbnails= new ArrayList<>();
    private OrderDetRVAdaptInterface orderDetRVAdaptInterface;
    private Context context;
    public OrderDetRecViewAdapter(Context context,ArrayList<OrderThumbnail> orderThumbnails,OrderDetRVAdaptInterface orderDetRVAdaptInterface) {
        this.context = context;
        this.orderThumbnails = orderThumbnails;
        this.orderDetRVAdaptInterface = orderDetRVAdaptInterface;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersitemlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.invoiceNum.setText(orderThumbnails.get(position).getInvoice());
        holder.dateDisp.setText(orderThumbnails.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return orderThumbnails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView invoiceNum,dateDisp;
        private Button viewDetButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            invoiceNum = itemView.findViewById(R.id.invoiceNumber);
            dateDisp = itemView.findViewById(R.id.orderDateVal);
            viewDetButton = itemView.findViewById(R.id.viewDetBtn);

            viewDetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(orderDetRVAdaptInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            orderDetRVAdaptInterface.onClick(pos,invoiceNum.getText().toString());
                        }
                    }
                }
            });
        }
    }
}
