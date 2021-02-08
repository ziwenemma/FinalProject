package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BabysitterDetail extends AppCompatActivity {

    public static final String TAG = "TAG";

    private String mPost_key = null;
    private DatabaseReference mdatabasereference;
    private DatabaseReference databaseReference;
    FirebaseAuth fAuth;

    private String parentName;
    private String parentEmail=null;
    private String parentPhone=null;
    private String parentChildage=null;
    private String parentAdd=null;
    private String parentReq=null;
    private String childGender;

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
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        mdatabasereference= FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("BabySitterPost");
        databaseReference= FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("ParentPost");



        mPost_key=getIntent().getExtras().getString("babysitter_id");

         parentName=getIntent().getExtras().getString("ParentName");
         parentEmail=getIntent().getExtras().getString("email");
        parentPhone=getIntent().getExtras().getString("ParentPhone");
        parentReq=getIntent().getExtras().getString("Requirement");
       parentChildage=getIntent().getExtras().getString("ChildAge");
        parentAdd=getIntent().getExtras().getString("Address");


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
                appointMap.put("parent_id", uid);
                appointMap.put("date", saveCurrentDate);
                appointMap.put("time", saveCurrentTime);
                appointMap.put("BabysitterName",babysitterName.getText().toString());
                appointMap.put("BabysitterEmail", babysitterEmail.getText().toString());
                appointMap.put("BabysitterPhone", babysitterPhone.getText().toString());
                appointMap.put("BabysitterCity",babysitterCity.getText().toString());
                appointMap.put("BabysitterRate",babysitterRate.getText().toString());

                appointMap.put("ParentName", parentName);
                appointMap.put("ParentEmail",parentEmail);
                appointMap.put("ParentChildAge",parentChildage);
                appointMap.put("ParentReq",parentReq);
                appointMap.put("ParentPhone",parentPhone);
                appointMap.put("ParentAdd",parentAdd);
                appointMap.put("ChildGender",childGender);



                makeAppointement.child("ParentUser").child(fAuth.getCurrentUser().getUid()).child(mPost_key)
                            .updateChildren(appointMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(BabysitterDetail.this, "Make an appointment successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(BabysitterDetail.this, AppointmentActivity.class);
                                        startActivity(intent);


                                    }
                                }
                            });


                makeAppointement.child("BabySitterUser").child(mPost_key).child(fAuth.getCurrentUser().getUid())
                        .updateChildren(appointMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                }
                            }
                        });

            }

        });

      String id=mdatabasereference.getKey();

        databaseReference.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String my_name= (String) snapshot.child("ParentName").getValue();
                String my_phone=(String)snapshot.child("phone").getValue();
                String my_email=(String)snapshot.child("email").getValue();
                String my_childage=(String)snapshot.child("childAge").getValue();
                String my_gender=(String)snapshot.child("gender").getValue();
                String my_address=(String)snapshot.child("address").getValue();
                String my_requirement=(String)snapshot.child("requirement").getValue();

                parentName=my_name;
                parentPhone=my_phone;
                parentEmail=my_childage;
                parentChildage=my_childage;
                parentAdd=my_address;
                parentReq=my_requirement;
               childGender  =my_gender;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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
