package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.buttonToParent);
        button = (Button)findViewById(R.id.buttonToSitter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenuActivity(v);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity(v);
            }
        });
    }

    public void startMenuActivity(View view){
        Intent intent= new Intent(this,LoginActivityParent.class);
        startActivity(intent);

    }


    public  void startNextActivity(View view){
        Intent Next = new Intent(this, LoginActivitySitter.class);
        startActivity(Next);

    }
}

