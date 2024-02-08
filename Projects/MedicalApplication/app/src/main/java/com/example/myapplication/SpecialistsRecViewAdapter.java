package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SpecialistsRecViewAdapter extends RecyclerView.Adapter<SpecialistsRecViewAdapter.ViewHolder> {
    private SpecialistsRecViewInterface specialistsRecViewInterface;
    private ArrayList<Specialists> specialists = new ArrayList<>();
    private Context context;
    public SpecialistsRecViewAdapter(Context context,SpecialistsRecViewInterface specialistsRecViewInterface) {
        this.context = context;
        this.specialistsRecViewInterface = specialistsRecViewInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.spNameDisp.setText(specialists.get(position).getName());
        holder.spExpDisp.setText("Exp: "+specialists.get(position).getExperience());
        holder.spHospDisp.setText("Hospital: "+specialists.get(position).getHospital());
        holder.spPhoneDisp.setText("Phone: "+specialists.get(position).getPhone());
        holder.spFeeDisp.setText("Fee: "+specialists.get(position).getFee());
        Glide.with(context).asBitmap().load(specialists.get(position).getImg_url()).into(holder.spProfileImg);
    }

    @Override
    public int getItemCount() {
        return specialists.size();
    }

    public void setSpecialists(ArrayList<Specialists> specialists) {
        this.specialists = specialists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView spNameDisp,spExpDisp,spHospDisp,spPhoneDisp,spFeeDisp;
        private ImageView spProfileImg;
        private  Button appointBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            spNameDisp = itemView.findViewById(R.id.spNameDisp);
            spExpDisp = itemView.findViewById(R.id.spExpDisp);
            spHospDisp = itemView.findViewById(R.id.spHospDisp);
            spPhoneDisp = itemView.findViewById(R.id.spPhoneDisp);
            spFeeDisp = itemView.findViewById(R.id.spFeeDisp);
            spProfileImg = itemView.findViewById(R.id.spProfileImg);
            appointBtn = itemView.findViewById(R.id.appointmentBtn);

            appointBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(specialistsRecViewInterface!=null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            specialistsRecViewInterface.onItemClick(pos,specialists.get(pos).getName());
                        }
                    }
                }
            });
        }
    }

}
