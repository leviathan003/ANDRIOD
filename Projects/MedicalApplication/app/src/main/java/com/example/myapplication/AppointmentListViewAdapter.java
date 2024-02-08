package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AppointmentListViewAdapter extends ArrayAdapter<Appointment> {

    private Context context;
    private int layout;

    private ArrayList<Appointment> appointments = new ArrayList<>();
    public AppointmentListViewAdapter(@NonNull Context context, int resource, ArrayList<Appointment> appointments) {
        super(context, resource, appointments);
        this.context = context;
        this.layout = resource;
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        notifyDataSetChanged();
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent, false);
            holder = new AppointmentListViewAdapter.ViewHolder();
            holder.docName = (TextView) convertView.findViewById(R.id.docName);
            holder.apptDate = (TextView) convertView.findViewById(R.id.apptDateVal);
            holder.apptTime = (TextView) convertView.findViewById(R.id.apptTimeVal);
            holder.remindBtn = (Button) convertView.findViewById(R.id.remindBtn);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.docName.setText(appointments.get(position).getDocName());
        holder.apptDate.setText(appointments.get(position).getApptDate());
        holder.apptTime.setText(appointments.get(position).getApptTime());
        holder.remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.remindBtn.getText().toString().equals("Reminder Set")){
                    holder.remindBtn.setText("Remind Me");
                }else{
                    holder.remindBtn.setText("Reminder Set");
                }
            }
        });
        return convertView;
    }

    public class ViewHolder{
        TextView docName,apptDate,apptTime;
        Button remindBtn;
    }
}
