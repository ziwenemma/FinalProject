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
    public static final int MSG_TYPE_RIGHT=1;
    public static final int MSG_TYPE_LEFT=0;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, ArrayList<AppointmentInfo> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MessageAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.AdapterViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.AdapterViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.AdapterViewHolder holder, int position) {
        AppointmentInfo appointmentInfo=arrayList.get(position);
        holder.show_message.setText(appointmentInfo.getMessage());
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


        public AdapterViewHolder(View itemView){
            super(itemView);
            show_message=itemView.findViewById(R.id.show_meg);

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (arrayList.get(position).getParent_id().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}