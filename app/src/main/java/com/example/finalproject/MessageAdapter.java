package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.AdapterViewHolder> {

    private Context mContext;
    private ArrayList<AppointmentInfo> arrayList;


    FirebaseUser fuser;

    public MessageAdapter(Context mContext, ArrayList<AppointmentInfo> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MessageAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_layout, parent, false);
            return new MessageAdapter.AdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.AdapterViewHolder holder, int position) {
        AppointmentInfo appointmentInfo=arrayList.get(position);
        holder.show_message.setText(appointmentInfo.getMessage());
        holder.show_name.setText(appointmentInfo.getSenderName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public long getItemId(int position){
        return position;
    }



    public  class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView show_message;
        public TextView show_name;



        public AdapterViewHolder(View itemView){
            super(itemView);
            show_message=itemView.findViewById(R.id.sender_message_text);
            show_name=itemView.findViewById(R.id.show_name);

        }

        @Override
        public void onClick(View v) {

        }
    }
}