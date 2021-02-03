package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.ListFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainPageParent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_parent);

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
