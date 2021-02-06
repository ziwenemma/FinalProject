package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class  post_my_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Toolbar mtoolbar;

    public static final String TAG = "TAG";
    EditText changeSitterGender,changeSitterName,changeSitterAge,changeSitterEmail,changeSitterPhone,changeSitterCity,changeSitterRate,changeSitterDesc;
    ImageView changeImageSitter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser sitteruser;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_my_profile);


        Intent data =getIntent();
        String BabySitterName =data.getStringExtra("BabySitterName");
        String BabySitterAge = data.getStringExtra("BabySitterAge");
        String BabySitterEmail=data.getStringExtra("BabySitterEmail");
        String  BabySitterPhone= data.getStringExtra("BabySitterPhone");
        String BabySitterCity=data.getStringExtra("BabySitterCity");
        String  BabySitterRate= data.getStringExtra("BabySitterRate");
        String  BabySitterDesc= data.getStringExtra("BabySitterDesc");
        String  BabySitterGender= data.getStringExtra("BabySitterGender");




        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        sitteruser= fAuth.getCurrentUser();



        String userID=sitteruser.getUid();
        storageReference= FirebaseStorage.getInstance().getReference();
        changeImageSitter=findViewById(R.id.imagesitter_ed);
        changeSitterName=findViewById(R.id.changeSitterName);
        changeSitterAge= findViewById(R.id.changeSitterAge);
        changeSitterEmail = findViewById(R.id.sitteremail);
        changeSitterPhone =findViewById(R.id.sitterphone);
        changeSitterCity=findViewById(R.id.sitter_city_ed);
        changeSitterRate=findViewById(R.id.sitter_rate_ed);
        changeSitterDesc=findViewById(R.id.editTextTextMultiLine);
        changeSitterGender=findViewById(R.id.gendersitter);
        btn=findViewById(R.id.button2);




        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final String sId = sharedPreferences.getString("id", "");
        if (!TextUtils.isEmpty(sId)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        InformationSitter informationSitter = dataSnapshot.getValue(InformationSitter.class);
                        if (TextUtils.equals(informationSitter.getId(), sId)) {
                            changeSitterName.setText(informationSitter.getSitterName());
                            changeSitterAge.setText(informationSitter.getSitterAge());
                            changeSitterEmail.setText(informationSitter.getSitterEmail());
                            changeSitterPhone.setText(informationSitter.getSitterPhone());
                            changeSitterCity.setText(informationSitter.getSitterCity());
                            changeSitterRate.setText(informationSitter.getSitterRate());
                            changeSitterDesc.setText(informationSitter.getSitterDesc());
                            changeSitterGender.setText(informationSitter.getSitterGender());
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }



        StorageReference profileRef = storageReference.child("babysitterusers/"+fAuth.getCurrentUser().getUid()+"/image.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(changeImageSitter);
            }
        });

        changeImageSitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeSitterName.getText().toString().isEmpty()|| changeSitterAge.getText().toString().isEmpty()
                        ||changeSitterCity.getText().toString().isEmpty()||changeSitterEmail.getText().toString().isEmpty()
                        ||changeSitterPhone.getText().toString().isEmpty()||changeSitterRate.getText().toString().isEmpty()||
                        changeSitterDesc.getText().toString().isEmpty()||changeSitterGender.getText().toString().isEmpty()){
                    Toast.makeText(post_my_profile.this, "One or many Fields are empty", Toast.LENGTH_SHORT);
                    return;
                }



                String email= changeSitterEmail.getText().toString();
                sitteruser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        DocumentReference docRef=fStore.collection("babysitterusers").document(sitteruser.getUid());
                        Map<String,Object> changed = new HashMap<>();
                        changed.put("email",email);
                        changed.put("BabySitterName",changeSitterName.getText().toString());
                        changed.put("BabySitterAge",changeSitterAge.getText().toString());
                        changed.put("BabySitterEmail",changeSitterEmail.getText().toString());
                        changed.put("BabySitterPhone",changeSitterPhone.getText().toString());
                        changed.put("BabySitterCity",changeSitterCity.getText().toString());
                        changed.put("BabySitterRate",changeSitterRate.getText().toString());
                        changed.put("BabySitterDesc",changeSitterDesc.getText().toString());
                        changed.put("BabySitterGender",changeSitterGender.getText().toString());



                        docRef.update(changed).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(post_my_profile.this,"BabySitter Information Updated", Toast.LENGTH_SHORT).show();


                                DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://finalproject-10b66-default-rtdb.firebaseio.com/").getReference();
                                String Id = mDatabase.push().getKey();



                                InformationSitter informationSitter = new InformationSitter(Id,changeSitterName.getText().toString(),
                                        changeSitterAge.getText().toString(),changeSitterCity.getText().toString(),changeSitterRate.getText().toString(),
                                        changeSitterDesc.getText().toString(),email,changeSitterPhone.getText().toString(),
                                        changeSitterGender.getText().toString());
                                mDatabase.child("BabySitterPost").child(fAuth.getCurrentUser().getUid()).setValue(informationSitter);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", Id);
                                editor.apply();

                                startActivity(new Intent(getApplicationContext(),MainPageSitter.class));
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(post_my_profile.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        changeSitterName.setText(BabySitterName);
        changeSitterAge.setText(BabySitterAge);
        changeSitterCity.setText(BabySitterCity);
        changeSitterRate.setText(BabySitterRate);
        changeSitterEmail.setText(BabySitterEmail);
        changeSitterPhone.setText(BabySitterPhone);
        changeSitterDesc.setText(BabySitterDesc);
        changeSitterGender.setText(BabySitterGender);


        Log.d(TAG, "onCreate" + BabySitterName + " "  + BabySitterAge + " " +BabySitterCity
                +" "+BabySitterRate+" "+BabySitterEmail+" "+BabySitterPhone+" "+BabySitterDesc+" "+BabySitterGender);



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                changeImageSitter.setImageURI(imageUri);


            }
        }
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




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id ==  android.R.id.home)
        {
            SendUserToMainPageSitter();
        }


        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMainPageSitter() {
        Intent mainIntent= new Intent(post_my_profile.this, MainPageSitter.class);
        startActivity(mainIntent);
    }
}