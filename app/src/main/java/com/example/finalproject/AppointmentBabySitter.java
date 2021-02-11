package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AppointmentBabySitter extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AppointmentInfo> arrayList;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth fAuth;

    Button btn;

    Query query;
    private DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<AppointmentInfo,BlogViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_baby_sitter);

        fAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerViewRequest);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment");


        BottomNavigationView navigationView1 = (BottomNavigationView) findViewById(R.id.bottom_menusitter);
        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Post_sitter:
                        Intent intent = new Intent(AppointmentBabySitter.this, MainPageSitter.class);
                        startActivity(intent);
                        break;
                    case R.id.Request:
                        Intent i = new Intent(AppointmentBabySitter.this, AppointmentBabySitter.class);
                        startActivity(i);
                        item.setIcon(R.drawable.appoint);
                        break;

                }
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        query = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment").child(fAuth.getCurrentUser().getUid());
        final DatabaseReference applist = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment").child("BabySitterUser");
        final DatabaseReference applist1 = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment").child("ParentUser");

        FirebaseRecyclerOptions<AppointmentInfo> options =
                new FirebaseRecyclerOptions.Builder<AppointmentInfo>()
                        .setQuery(applist.child(fAuth.getCurrentUser().getUid()), AppointmentInfo.class)
                        .build();

        Log.d("Options", " data : " + options);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AppointmentInfo, BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final BlogViewHolder blogViewHolder, final int i, @NonNull final AppointmentInfo appointmentInfo) {
                blogViewHolder.setName(appointmentInfo.getParentName());
                blogViewHolder.setPhone(appointmentInfo.getParentPhone());
                blogViewHolder.setEmail(appointmentInfo.getParentEmail());
                blogViewHolder.setChildage(appointmentInfo.getParentChildAge());
                blogViewHolder.setGender(appointmentInfo.getChildGender());
                blogViewHolder.setAdd(appointmentInfo.getParentAdd());
                blogViewHolder.setReq(appointmentInfo.getParentReq());
                blogViewHolder.setStatus(appointmentInfo.getStatus());
                String pid=appointmentInfo.getParent_id();


                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] =new CharSequence[]{
                                "Cancel",
                                "Accept"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AppointmentBabySitter.this);
                        builder.setTitle("Appointment List");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    applist.child(fAuth.getCurrentUser().getUid()).child(pid).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });

                                    applist1.child(pid).child(fAuth.getCurrentUser().getUid()).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intent=new Intent(AppointmentBabySitter.this,AppointmentBabySitter.class);
                                                    startActivity(intent);
                                                }
                                            });

                                }
                                if (which==1){
                                    applist.child(fAuth.getCurrentUser().getUid()).child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snapshot.getRef().child("Status").setValue("Accepted");
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Log.d("Error", error.getMessage());

                                        }
                                    });
                                    applist1.child(pid).child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snapshot.getRef().child("Status").setValue("Accepted");
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Log.d("Error", error.getMessage());

                                        }
                                    });

                                }
                            }
                        });
                        builder.show();

                        }
                });

            }


            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_babysitter_app, parent, false);
                return new BlogViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);




    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mView;
        FirebaseAuth fAuth;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            fAuth = FirebaseAuth.getInstance();
        }



        public void setName(String name) {
            TextView bname = (TextView) mView.findViewById(R.id.text1_baby);
            bname.setText(name);

        }

        public void setPhone(String phone) {
            TextView aphone = (TextView) mView.findViewById(R.id.phone_baby);
            aphone.setText(phone);

        }

        public void setEmail(String emai) {
            TextView aemail = (TextView) mView.findViewById(R.id.email_baby);
            aemail.setText(emai);

        }

        public void setChildage(String age) {
            TextView cage = (TextView) mView.findViewById(R.id.childgender_baby);
            cage.setText(age);

        }

        public void setGender(String gender) {
            TextView agender = (TextView) mView.findViewById(R.id.childgender_baby);
            agender.setText(gender);

        }

        public void setReq(String req) {
            TextView areq = (TextView) mView.findViewById(R.id.textRequirement);
            areq.setText(req);

        }

        public void setAdd(String add) {
            TextView aadd = (TextView) mView.findViewById(R.id.add_baby);
            aadd.setText(add);

        }

        public void setStatus(String status) {
            TextView astatus = (TextView) mView.findViewById(R.id.status);
            astatus.setText(status);

        }

        @Override
        public void onClick(View v) {

        }
    }


}
