package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity {
    private String key = null;
    RecyclerView recyclerView;
    ArrayList<AppointmentInfo> arrayList;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth fAuth;

    Query query;
    private DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<AppointmentInfo, BlogViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        fAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerViewAppoint);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment");


        BottomNavigationView navigationView1 = (BottomNavigationView) findViewById(R.id.bottom_menu);
        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Post_info_sitter:
                        Intent intent = new Intent(AppointmentActivity.this, MainPageParent.class);
                        startActivity(intent);
                        break;
                    case R.id.Appointment:
                        Intent i = new Intent(AppointmentActivity.this, AppointmentActivity.class);
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
        final DatabaseReference applist = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment").child("ParentUser");
        FirebaseRecyclerOptions<AppointmentInfo> options =
                new FirebaseRecyclerOptions.Builder<AppointmentInfo>()
                        .setQuery(applist.child(fAuth.getCurrentUser().getUid()), AppointmentInfo.class)
                        .build();

        Log.d("Options", " data : " + options);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AppointmentInfo, BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final BlogViewHolder blogViewHolder, final int i, @NonNull final AppointmentInfo appointmentInfo) {
                final String post_key = getRef(i).getKey();

                blogViewHolder.setName(appointmentInfo.getBabysitterName());
                blogViewHolder.setPhone(appointmentInfo.getBabysitterPhone());
                blogViewHolder.setEmail(appointmentInfo.getBabysitterEmail());
                blogViewHolder.setRate(appointmentInfo.getBabysitterRate());
                blogViewHolder.setStatus(appointmentInfo.getStatus());
                blogViewHolder.setimage(appointmentInfo.getBabySitterImage());

                String pid=appointmentInfo.getBabysitter_id();
                String id=appointmentInfo.getParent_id();




                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    final DatabaseReference applistchat = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("Appointment").child("ParentUser").child(fAuth.getCurrentUser().getUid()).child(pid);

                    @Override
                    public void onClick(View v) {

                        CharSequence options[] = new CharSequence[]{
                                "Chat"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
                        builder.setTitle("AppointmentList:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    applistchat.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            final String babysitterid = pid;
                                            Log.d("babysitter_id", " data : " + pid);
                                            Intent singleItemIntent = new Intent(AppointmentActivity.this,ChatActivity.class);
                                            singleItemIntent.putExtra("babysitter_id",pid);
                                            startActivity(singleItemIntent);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

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
                        .inflate(R.layout.appointment_card, parent, false);
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
            TextView bname = (TextView) mView.findViewById(R.id.textSitter);
            bname.setText(name);

        }

        public void setPhone(String phone) {
            TextView aphone = (TextView) mView.findViewById(R.id.textPhoneApp);
            aphone.setText(phone);

        }

        public void setEmail(String emai) {
            TextView aemail = (TextView) mView.findViewById(R.id.textEmailApp);
            aemail.setText(emai);

        }

        public void setRate(String rate) {
            TextView arate = (TextView) mView.findViewById(R.id.text2);
            arate.setText(rate);

        }

        public void setStatus(String status) {
            TextView astatus = (TextView) mView.findViewById(R.id.textstatus);
            astatus.setText(status);

        }

        public String setimage(String url){
            ImageView image=(ImageView)mView.findViewById(R.id.productimage);
            Picasso.get().load(url).into(image);
            return url;
        }


        @Override
        public void onClick(View v) {


        }
    }


}
