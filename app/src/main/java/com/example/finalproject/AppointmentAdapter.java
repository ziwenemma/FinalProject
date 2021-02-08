package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AdapterViewHolder> {

    public Context c;
    public ArrayList<AppointmentInfo> arrayList;
    int num = 0;


    public AppointmentAdapter(Context c, ArrayList<AppointmentInfo> arrayList) {
        this.c = c;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_appointment, parent, false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder holder, int position) {
        AppointmentInfo appointmentInfo = arrayList.get(position);
        holder.t1.setText(appointmentInfo.getBabysitterName());
        holder.t2.setText(appointmentInfo.getBabysitterEmail());
        holder.t3.setText(appointmentInfo.getBabysitterPhone());
        holder.t4.setText(appointmentInfo.getBabysitterRate());

    }

    /**
     * text view for the items displayed
     */

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView t1;
        public TextView t2;
        public TextView t3;
        public TextView t4;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.textSitter);
            t2 = (TextView) itemView.findViewById(R.id.textEmailApp);
            t3 = (TextView) itemView.findViewById(R.id.textPhoneApp);
            t4=(TextView)itemView.findViewById(R.id.text2);


        }

        @Override
        public void onClick(View v) {

        }
    }

}
