package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BabysitterDetail extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mdatabasereference;
    FirebaseAuth fAuth;

    private Button makeanAppointmentbtn;
    private TextView babysitterEmail;
    private  TextView babysitterName;
    private TextView babysitterAge;
    private TextView babysitterCity;
    private TextView babysitterDesc;
    private TextView babysitterRate;
    private TextView babysitterPhone;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_detail);

        makeanAppointmentbtn=(Button)findViewById(R.id.cartbtn);
        fAuth = FirebaseAuth.getInstance();

        mdatabasereference= FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("BabySitterPost");

        String post_key=getIntent().getStringExtra("parent_id");
         mPost_key=getIntent().getExtras().getString("babysitter_id");
         babysitterName=(TextView)findViewById(R.id.babysitter_name_de);
         babysitterAge=(TextView)findViewById(R.id.babysitter_age_de);
         babysitterCity=(TextView)findViewById(R.id.babysitte_city_de);
        babysitterDesc=(TextView)findViewById(R.id.babysitte_des_de);
        babysitterPhone=(TextView)findViewById(R.id.babysitter_ph);
        babysitterEmail=(TextView)findViewById(R.id.babysitter_email);
        babysitterRate=(TextView)findViewById(R.id.babysitter_rate_de);




        makeanAppointmentbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeanAppointment();
            }

            private void makeanAppointment() {
                String saveCurrentTime, saveCurrentDate;

                Calendar calForData = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate = currentDate.format(calForData.getTime());
                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForTime.getTime());



                final DatabaseReference makeAppointement = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment");
                final HashMap<String, Object> appointMap = new HashMap<>();
                appointMap.put("babysitter_id", mPost_key);
                appointMap.put("parentid", post_key);
                appointMap.put("date", saveCurrentDate);
                appointMap.put("time", saveCurrentTime);
                appointMap.put("BabysitterName",babysitterName.getText().toString());
                appointMap.put("BabysitterEmail", babysitterEmail.getText().toString());
                appointMap.put("BabysitterPhone", babysitterPhone.getText().toString());
                appointMap.put("BabysitterCity",babysitterCity.getText().toString());
                appointMap.put("BabysitterRate",babysitterRate.getText().toString());


                    makeAppointement.child("Appointment").child(fAuth.getCurrentUser().getUid())
                            .child(mPost_key).updateChildren(appointMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(BabysitterDetail.this, "Make an appointment successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(BabysitterDetail.this, MainPageParent.class);
                                        startActivity(intent);

                                    }
                                }
                            });

                }

        });

      String id=mdatabasereference.getKey();

        mdatabasereference.child(mPost_key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String post_name= (String) snapshot.child("sitterName").getValue();
                String post_desc=(String)snapshot.child("sitterDesc").getValue();
                String post_age=(String)snapshot.child("sitterAge").getValue();
                String post_city=(String)snapshot.child("sitterCity").getValue();
                String post_phone=(String)snapshot.child("sitterPhone").getValue();
                String post_rate=(String)snapshot.child("sitterRate").getValue();
                String post_email=(String)snapshot.child("sitterEmail").getValue();


                babysitterName.setText(post_name);
                babysitterDesc.setText(post_desc);
                babysitterAge.setText(post_age);
                babysitterCity.setText(post_city);
                babysitterPhone.setText(post_phone);
                babysitterRate.setText(post_rate);
                babysitterEmail.setText(post_email);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
