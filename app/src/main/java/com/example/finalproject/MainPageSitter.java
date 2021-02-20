package com.example.finalproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.ListFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class MainPageSitter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView SitterGender,SitterName,SitterAge,SitterEmail,SitterPhone,SitterCity,SitterDesc,SitterRate;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btn;
    ImageView SitterImageView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser sitteruser;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_sitter);
        btn=findViewById(R.id.sitterupdateto);


        SitterGender=findViewById(R.id.babysitter_gender);
        SitterName = findViewById(R.id.sitter_name);
        SitterAge = findViewById(R.id.babysitte_age);
        SitterCity =findViewById(R.id.babysitte_city);
        SitterEmail=findViewById(R.id.babysitter_email);
        SitterPhone=findViewById(R.id.babysitter_ph);
        SitterDesc=findViewById(R.id.babysitte_des);
        SitterRate=findViewById(R.id.babysitter_rate);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        SitterImageView=findViewById(R.id.sitter_image);


        StorageReference profileRef = storageReference.child("babysitterusers/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(SitterImageView);
            }
        });

        userId = fAuth.getCurrentUser().getUid();
        sitteruser = fAuth.getCurrentUser();



        BottomNavigationView navigationView1 = (BottomNavigationView) findViewById(R.id.bottom_menusitter);
        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Post_sitter:
                        Intent intent = new Intent(MainPageSitter.this, MainPageSitter.class);
                        startActivity(intent);
                        break;
                    case R.id.Request:
                        Intent i = new Intent(MainPageSitter.this, AppointmentBabySitter.class);
                        startActivity(i);
                        item.setIcon(R.drawable.appoint);
                        break;
                }
                return false;
            }
        });


        DocumentReference documentReference = fStore.collection("babysitterusers").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    SitterName.setText(documentSnapshot.getString("BabySitterName"));
                    SitterAge.setText(documentSnapshot.getString("BabySitterAge"));
                    SitterPhone.setText(documentSnapshot.getString("BabySitterPhone"));
                    SitterEmail.setText(documentSnapshot.getString("BabySitterEmail"));
                    SitterCity.setText(documentSnapshot.getString("BabySitterCity"));
                    SitterGender.setText(documentSnapshot.getString("BabySitterGender"));
                    SitterDesc.setText(documentSnapshot.getString("BabySitterDesc"));
                    SitterRate.setText(documentSnapshot.getString("BabySitterRate"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),post_my_profile.class);
                i.putExtra("BabySitterName",SitterName.getText().toString());
                i.putExtra("BabySitterAge",SitterAge.getText().toString());
                i.putExtra("BabySitterCity",SitterCity.getText().toString());
                i.putExtra("BabySitterRate",SitterRate.getText().toString());
                i.putExtra("BabySitterDesc",SitterDesc.getText().toString());
                i.putExtra("BabySitterGender",SitterGender.getText().toString());
                i.putExtra("BabySitterEmail",SitterEmail.getText().toString());
                i.putExtra("BabySitterPhone",SitterPhone.getText().toString());
                startActivity(i);

            }
        });

        /*------Hooks-----*/
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_sitter);

        /*----Toolbar----*/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*----Navigation Drawer Menu----*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view_sitter);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public void changeProfile(View view) {
        Intent intent=new Intent(this, post_my_profile.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id== R.id.nav_home){
            startActivity(new Intent(getApplicationContext(), MainPageSitter.class));
        }else if(id==R.id.nav_contact) {
            startActivity(new Intent(getApplicationContext(), ContactUs.class));
        }
        else if (id==R.id.nav_about){
            startActivity(new Intent(getApplicationContext(),AboutUs.class));
        }
        else if (id==R.id.nav_add_my_profile){
            startActivity(new Intent(getApplicationContext(),MainPageSitter
                    .class));
        }
        else if (id==R.id.nav_profile){
            startActivity(new Intent(getApplicationContext(),ProfileActivitySitter.class));
        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return  true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sitter, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Intent myintent = new Intent(MainPageSitter.this, ProfileActivitySitter.class);
            startActivity(myintent);
        }

        return super.onOptionsItemSelected(item);
    }

}
