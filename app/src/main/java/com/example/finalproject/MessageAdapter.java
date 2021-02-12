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

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;

    private List<AppointmentInfo> appointmentInfos;
    public static final int MSG_TYPE_RIGHT=1;
    public static final int MSG_TYPE_LEFT=0;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<AppointmentInfo> appointmentInfos) {
        this.appointmentInfos = appointmentInfos;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
    }
}
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        AppointmentInfo appointmentInfo=appointmentInfos.get(position);
        holder.show_message.setText(appointmentInfo.getMessage());
    }

    @Override
    public int getItemCount() {
        return appointmentInfos.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;

        public ViewHolder(View itemView){
            super(itemView);

            show_message=itemView.findViewById(R.id.show_meg);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (appointmentInfos.get(position).getParent_id().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
