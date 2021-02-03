package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class ParentInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView Gender,ParentName,ChildName,ChildAge,ChildNum,EmailAdd,Phone,Add,Requirement;
    ImageView ImageView;
    Button btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    DatabaseReference   mDatabase;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_information);


        btn=findViewById(R.id.ChangeParentInfo);


        Gender=findViewById(R.id.Childgender);
        ParentName = findViewById(R.id.ParentName);
        ChildName=findViewById(R.id.ChildName);
        ChildNum=findViewById(R.id.pChildnum);
        ChildAge = findViewById(R.id.ChildAge);
        EmailAdd =findViewById(R.id.EmailAddress);
        Phone=findViewById(R.id.PhoneNo);
        Add=findViewById(R.id.Address);
        Requirement=findViewById(R.id.Requirement);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        ImageView=findViewById(R.id.ImageView);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        StorageReference profileRef = storageReference.child("parentuser/"+fAuth.getCurrentUser().getUid()+"/profileimage.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(ImageView);
            }
        });

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();




        DocumentReference documentReference = fStore.collection("parentuser").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    ParentName.setText(documentSnapshot.getString("ParentName"));
                    ChildName.setText(documentSnapshot.getString("ChildName"));
                    ChildAge.setText(documentSnapshot.getString("ChildAge"));
                    ChildNum.setText(documentSnapshot.getString("ChildNum"));
                    Phone.setText(documentSnapshot.getString("ParentPhone"));
                    EmailAdd.setText(documentSnapshot.getString("email"));
                    Add.setText(documentSnapshot.getString("Address"));
                    Requirement.setText(documentSnapshot.getString("Requirement"));
                    Gender.setText(documentSnapshot.getString("Gender"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        //realtime database
        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final String sId = sharedPreferences.getString("id", "");
        if (!TextUtils.isEmpty(sId)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ParentInfo");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Information information = dataSnapshot.getValue(Information.class);

                        if (TextUtils.equals(information.getId(), sId)) {
                            ParentName.setText(information.getParentName());
                            ChildName.setText(information.getChildName());
                            ChildAge.setText(information.getChildAge());
                            ChildNum.setText(information.getChildNum());
                            EmailAdd.setText(information.getEmail());
                            Add.setText(information.getAddress());
                            Phone.setText(information.getPhone());
                            Requirement.setText(information.getRequirement());
                            Gender.setText(information.getGender());
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        String Id = mDatabase.push().getKey();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),PostInfoParent.class);
                i.putExtra("ParentName",ParentName.getText().toString());
                i.putExtra("ChildName",ChildName.getText().toString());
                i.putExtra("ChildAge",ChildAge.getText().toString());
                i.putExtra("ChildNum",ChildNum.getText().toString());
                i.putExtra("ParentPhone",Phone.getText().toString());
                i.putExtra("email",EmailAdd.getText().toString());
                i.putExtra("Address",Add.getText().toString());
                i.putExtra("Requirement",Requirement.getText().toString());
                i.putExtra("Gender",Gender.getText().toString());




                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                String Id = mDatabase.push().getKey();
                Information user = new Information(Id, ParentName.getText().toString(), ChildName.getText().toString(), ChildAge.getText().toString(),
                        ChildNum.getText().toString(),Phone.getText().toString(),EmailAdd.getText().toString(),Add.getText().toString(),Requirement.getText().toString()
                        ,Gender.getText().toString());
                mDatabase.child("Parent Post").child(fAuth.getCurrentUser().getUid()).child(Objects.requireNonNull(Id)).setValue(user);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", Id);
                editor.apply();

                startActivity(i);

            }
        });


    }
    public void updateProfile(View view) {
        Intent intent=new Intent(this, PostInfoParent.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1,
                               int position,
                               long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

