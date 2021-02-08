package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BabysitterAdapter extends RecyclerView.Adapter<BabysitterAdapter.AdapterViewHolder> {
    
    
    public Context a;
    public ArrayList<AppointmentInfo> arrayList;
    
    
    
    public BabysitterAdapter(Context c, ArrayList<AppointmentInfo> arrayList){
        this.a=c;
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_baby_sitter,parent,false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        AppointmentInfo appointmentInfo = arrayList.get(position);
        holder.tname.setText(appointmentInfo.getParentName());
        holder.tage.setText(appointmentInfo.getParentChildAge());
        holder.tgender.setText(appointmentInfo.getChildGender());
        holder.temail.setText(appointmentInfo.getParentEmail());
        holder.tphone.setText(appointmentInfo.getBabysitterPhone());
        holder.tadd.setText(appointmentInfo.getParentAdd());
        holder.treq.setText(appointmentInfo.getParentReq());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    
    @Override
    public long getItemId(int position){
        return position;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tname;
        public TextView tage;
        public TextView tgender;
        public TextView temail;
        public TextView tphone;
        public TextView tadd;
        public TextView treq;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tname = (TextView) itemView.findViewById(R.id.text1_baby);
            tage = (TextView) itemView.findViewById(R.id.childage_baby);
            tgender = (TextView) itemView.findViewById(R.id.childgender_baby);
            temail=(TextView)itemView.findViewById(R.id.email_baby);
            tphone = (TextView) itemView.findViewById(R.id.phone_baby);
            tadd = (TextView) itemView.findViewById(R.id.add_baby);
            treq = (TextView) itemView.findViewById(R.id.textRequirement);


        }

        @Override
        public void onClick(View v) {

        }
    }

}

