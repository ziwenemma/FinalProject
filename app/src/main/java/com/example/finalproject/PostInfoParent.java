package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PostInfoParent extends AppCompatActivity {
    Button btn;

    public static final String TAG = "TAG";
    EditText changeGender,changeParentName,changeChildName,changeChildAge,changeChildNum,changeEmailAdd,changePhone,changeAdd,changeRequirement;
    ImageView changeImageView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info_parent);


        Intent data =getIntent();
        String ParentName =data.getStringExtra("ParentName");
        String ChildName=data.getStringExtra("ChildName");
        String ChildAge = data.getStringExtra("ChildAge");
        String ChildNum =data.getStringExtra("ChildNum");
        String EmailAdd=data.getStringExtra("email");
        String  Phone= data.getStringExtra("ParentPhone");
        String Add=data.getStringExtra("Address");
        String  Requirement= data.getStringExtra("Requirement");
        String  gender= data.getStringExtra("Gender");


        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user= fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        changeImageView=findViewById(R.id.changeImageView);


        changeGender=findViewById(R.id.changeChildgender);
        changeParentName = findViewById(R.id.changeParentName);
        changeChildName=findViewById(R.id.changeChildName);
        changeChildNum=findViewById(R.id.changeChildnum);
        changeChildAge = findViewById(R.id.changeChildAge);
        changeEmailAdd =findViewById(R.id.changeEmailAddress);
        changePhone=findViewById(R.id.changePhoneNo);
        changeAdd=findViewById(R.id.changeAddress);
        changeRequirement=findViewById(R.id.changeRequirement);

        btn=findViewById(R.id.UpdateParentInfo);



        StorageReference profileRef = storageReference.child("parentuser/"+fAuth.getCurrentUser().getUid()+"/image.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(changeImageView);
            }
        });

        changeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeParentName.getText().toString().isEmpty()|| changeChildName.getText().toString().isEmpty()||changeChildAge.getText().toString().isEmpty()
                        ||changeChildNum.getText().toString().isEmpty()|| changeEmailAdd.getText().toString().isEmpty()||changeAdd.getText().toString().isEmpty()
                        ||changePhone.getText().toString().isEmpty()|| changeAdd.getText().toString().isEmpty()||changeRequirement.getText().toString().isEmpty()||
                        changeGender.getText().toString().isEmpty()){
                    Toast.makeText(PostInfoParent.this, "One or many Fields are empty", Toast.LENGTH_SHORT);
                    return;
                }

                String email= changeEmailAdd.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef=fStore.collection("parentuser").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("ParentName",changeParentName.getText().toString());
                        edited.put("ChildName",changeChildName.getText().toString());
                        edited.put("ChildAge",changeChildAge.getText().toString());
                        edited.put("ChildNum",changeChildNum.getText().toString());
                        edited.put("ParentPhone",changePhone.getText().toString());
                        edited.put("Address",changeAdd.getText().toString());
                        edited.put("Requirement",changeRequirement.getText().toString());
                        edited.put("Gender",changeGender.getText().toString());

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PostInfoParent.this,"Information Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),ParentInformation.class));
                                finish();
                            }
                        });


                        Toast.makeText(PostInfoParent.this,"Changed",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostInfoParent.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        changeParentName.setText(ParentName);
        changeChildAge.setText(ChildAge);
        changeChildName.setText(ChildName);
        changeChildNum.setText(ChildNum);
        changeEmailAdd.setText(EmailAdd);
        changePhone.setText(Phone);
        changeAdd.setText(Add);
        changeRequirement.setText(Requirement);
        changeGender.setText(gender);


        Log.d(TAG, "onCreate" + ParentName + " "  + ChildAge + " " + ChildName+" "+ChildNum+" "+EmailAdd
                +" "+Phone+" "+Add+" "+Requirement+" "+gender);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri=data.getData();
                changeImageView.setImageURI(imageUri);



            }
        }
    }


}
