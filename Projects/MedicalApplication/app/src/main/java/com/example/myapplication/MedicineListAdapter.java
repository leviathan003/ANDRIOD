package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class MedicineListAdapter extends ArrayAdapter<Medicine> {
    private Context context;
    private int layout,totCost;
    private ArrayList<Medicine> medslist = new ArrayList<>();
    public MedicineListAdapter(@NonNull Context context,int resource ,ArrayList<Medicine> medslist) {
        super(context,resource,medslist);
        this.context = context;
        layout = resource;
        this.medslist = medslist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.medName);
            holder.baseCost = (TextView) convertView.findViewById(R.id.medCost);
            holder.qtyOrder = (TextView) convertView.findViewById(R.id.medQtyInstruct);
            holder.addBtn = (Button) convertView.findViewById(R.id.addBtn);
            holder.subBtn = (Button) convertView.findViewById(R.id.subBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(medslist.get(position).getMedName());
        holder.baseCost.setText("Price: "+String.valueOf(medslist.get(position).getMedCost()));
        holder.qtyOrder.setText("Quatity Needed: "+String.valueOf(medslist.get(position).getMedQty()));
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.qtyOrder.setText("Quatity Needed: "+String.valueOf(medslist.get(position).getMedQty()+1));
                medslist.get(position).setMedQty(medslist.get(position).getMedQty()+1);
            }
        });
        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(medslist.get(position).getMedQty()!=0){
                    holder.qtyOrder.setText("Quatity Needed: "+String.valueOf(medslist.get(position).getMedQty()-1));
                    medslist.get(position).setMedQty(medslist.get(position).getMedQty()-1);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder{
        TextView name,baseCost,qtyOrder;
        Button addBtn,subBtn;
    }

}
