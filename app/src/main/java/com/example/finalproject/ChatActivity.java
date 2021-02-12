package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ImageButton btn_send;
    EditText text_send;
    private String mPost_key = null;

    FirebaseAuth fAuth;

    MessageAdapter messageAdapter;
    List<AppointmentInfo>appointmentInfos;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mPost_key=getIntent().getExtras().getString("babysitter_id");

        btn_send=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);
        fAuth = FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManage = new LinearLayoutManager(getApplicationContext());
        linearLayoutManage.setStackFromEnd(true);


        btn_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String msg= text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(msg);
                }else {
                    Toast.makeText(ChatActivity.this,"You can't send empty message",Toast.LENGTH_LONG).show();
                }
               text_send.setText("");
            }
        });

        DatabaseReference reference=FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment")
                .child("ParentUser").child(fAuth.getCurrentUser().getUid()).child(mPost_key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessage(fAuth.getCurrentUser().getUid(),mPost_key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



 private void sendMessage(String message){
        DatabaseReference reference=FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment")
       .child("ParentUser").child(fAuth.getCurrentUser().getUid()).child(mPost_key);
     HashMap<String,Object> hashMap=new HashMap<>();
     hashMap.put("message",message);

     reference.child("Chats").push().setValue(hashMap);

 }

 private void readMessage(String parentid,String babysitterid){
        appointmentInfos=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment")
                .child("ParentUser").child(fAuth.getCurrentUser().getUid()).child(mPost_key).child("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AppointmentInfo appointmentInfo=dataSnapshot.getValue(AppointmentInfo.class);
                    if (appointmentInfo.getBabysitter_id().equals(fAuth.getCurrentUser().getUid()) && appointmentInfo.getParent_id().equals(mPost_key)
                    ||appointmentInfo.getBabysitter_id().equals(mPost_key)&&appointmentInfo.getParent_id().equals(fAuth.getCurrentUser().getUid())){
                        appointmentInfos.add(appointmentInfo);
                    }

                    messageAdapter= new MessageAdapter(ChatActivity.this,appointmentInfos);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


 }
}
