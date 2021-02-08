package com.example.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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

public class MainPageParent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    RecyclerView recyclerView;
    EditText editText;
    ArrayList<InformationSitter> arrayList;
    private boolean mProcessLike=false;
    FirebaseAuth fAuth;

    Query query1;
    private DatabaseReference mdatabasereference;
    FirebaseRecyclerAdapter<InformationSitter, BlogViewHolder> firebaseRecyclerAdapter;
    LinearLayoutManager mLayoutManager;
    AdapterParent adapterParent;
    List<InformationSitter> babysitterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_parent);

        arrayList = new ArrayList<>();
        fAuth = FirebaseAuth.getInstance();
        mdatabasereference = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("BabySitterPost");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGirdView);

        BottomNavigationView navigationView1 = (BottomNavigationView) findViewById(R.id.bottom_menu);
        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Post_info_sitter:
                        Intent intent = new Intent(MainPageParent.this, MainPageParent.class);
                        startActivity(intent);
                        break;
                    case R.id.Appointment:
                        Intent i = new Intent(MainPageParent.this, AppointmentActivity.class);
                        startActivity(i);
                        item.setIcon(R.drawable.appoint);
                        break;
                }
                return false;
            }
        });


        /*------Hooks-----*/
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_parent);

        /*----Toolbar----*/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);


        /*----Navigation Drawer Menu----*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view_parent);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        query1 = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference().child("BabySitterPost");
        FirebaseRecyclerOptions<InformationSitter> options =
                new FirebaseRecyclerOptions.Builder<InformationSitter>()
                        .setQuery(query1, InformationSitter.class)
                        .build();

        Log.d("Options"," data : "+options);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<InformationSitter, BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final BlogViewHolder blogViewHolder, final int i, @NonNull final InformationSitter informationSitter) {
                final String post_key = getRef(i).getKey();

                blogViewHolder.setName(informationSitter.getSitterName());
                blogViewHolder.setCity(informationSitter.getSitterCity());
                blogViewHolder.setDesc(informationSitter.getSitterDesc());
                blogViewHolder.setRate(informationSitter.getSitterRate());

                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final String babysitterid = getRef(i).getKey();
                        Log.d("babysitter_id", " data : " + babysitterid);
                        Intent singleItemIntent = new Intent(MainPageParent.this,BabysitterDetail.class);
                        singleItemIntent.putExtra("babysitter_id",post_key);
                        startActivity(singleItemIntent);



                    }
                });

            }


            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cardview_parent, parent, false);
                return new BlogViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        View mView;
        FirebaseAuth fAuth;

        public BlogViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
            fAuth=FirebaseAuth.getInstance();
        }


        public void setName(String name)
        {
            TextView bname=(TextView)mView.findViewById(R.id.text1);
            bname.setText(name);

        }

        public void setCity(String city)
        {
            TextView bcity=(TextView)mView.findViewById(R.id.text5);
            bcity.setText(city);

        }

        public void setDesc(String desc)
        {
            TextView bdesc=(TextView)mView.findViewById(R.id.text3);
            bdesc.setText(desc);

        }

        public void setRate(String rate)
        {
            TextView brate=(TextView)mView.findViewById(R.id.text2);
            brate.setText(rate);

        }

        @Override
        public void onClick(View v) {

        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id== R.id.nav_home){
            startActivity(new Intent(getApplicationContext(), MainPageParent.class));
        }else if(id==R.id.nav_contact) {
            startActivity(new Intent(getApplicationContext(), ContactUs.class));
        }
        else if (id==R.id.nav_about){
            startActivity(new Intent(getApplicationContext(),AboutUs.class));
        }
        else if (id==R.id.nav_post){
            startActivity(new Intent(getApplicationContext(),ParentInformation.class));
        }

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return  true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            Intent myintent = new Intent(MainPageParent.this, ProfileActivity.class);
            startActivity(myintent);
        }

        return super.onOptionsItemSelected(item);
    }
}
