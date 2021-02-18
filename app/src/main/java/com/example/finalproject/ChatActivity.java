package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    ImageButton btn_send;
    EditText text_send;
    private String mPost_key = null;
    FirebaseAuth fAuth;
    Query query;
    String reid=null;
    String senid=null;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<AppointmentInfo>arrayList;

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<AppointmentInfo, BlogViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        fAuth = FirebaseAuth.getInstance();
        mPost_key=getIntent().getExtras().getString("babysitter_id");
        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManage = new LinearLayoutManager(getApplicationContext());
        linearLayoutManage.setStackFromEnd(true);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(msg, fAuth.getCurrentUser().getUid(), mPost_key);
                } else {
                    Toast.makeText(ChatActivity.this, "You can't send empty message", Toast.LENGTH_LONG).show();
                }
                text_send.setText("");
            }
        });
    }


    private void sendMessage(String message, String sender, String receiver) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("message", message);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("SenderName", "ParentUser:");

        reference.child("Message").child(fAuth.getCurrentUser().getUid()).child(mPost_key).push().setValue(hashMap);
        reference.child("Message").child(mPost_key).child(fAuth.getCurrentUser().getUid()).push().setValue(hashMap);
    }


    public void onStart() {
        super.onStart();
        query = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Message").child(fAuth.getCurrentUser().getUid())
                .child(mPost_key);
        FirebaseRecyclerOptions<AppointmentInfo> options =
                new FirebaseRecyclerOptions.Builder<AppointmentInfo>()
                        .setQuery(query, AppointmentInfo.class)
                        .build();

        Log.d("Options", " data : " + options);
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AppointmentInfo,BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder blogViewHolder, int i, @NonNull AppointmentInfo appointmentInfo) {
                blogViewHolder.setMessage(appointmentInfo.getMessage());
                blogViewHolder.setName(appointmentInfo.getSenderName());
                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference myreference = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Message");
                        myreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                AppointmentInfo appointmentInfo1=snapshot.getValue(AppointmentInfo.class);
                                String id= appointmentInfo1.getSender();
                                String receiveid=appointmentInfo1.getReceiver();
                                senid=id;
                                reid=receiveid;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });

            }
            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
                    return new BlogViewHolder (view);

            }
        };

        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

}


class BlogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    FirebaseAuth fAuth;


    public BlogViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        fAuth = FirebaseAuth.getInstance();
    }


    public void setMessage(String message) {
        TextView bmessage = (TextView) mView.findViewById(R.id.sender_message_text);
        bmessage.setText(message);
    }
    public void setName(String SenderName) {
        TextView aname = (TextView) mView.findViewById(R.id.show_name);
        aname.setText(SenderName);
    }

    @Override
    public void onClick(View v) {

    }
}

