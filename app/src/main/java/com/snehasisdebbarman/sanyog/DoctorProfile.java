package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class DoctorProfile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1;
    StorageReference storageReference;
    //path to store profile images
    String storagePath="users_profile_imgs/";

    ImageView avatarIV;
    TextView nameTV, emailTV, phoneTV,qualificationTV,locationTV,specialityTV;
    MaterialButton fab;
    ProgressDialog pd;


    //permissions
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE =300;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE =400;

    //permission array
    String[] cameraPermissions;
    String[] storagePermissions;
    Uri image_uri;

    //string to choose
    String profilePhotoPic;
    String auser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();


        if(auser==null){
            auser="Doctors";
        }
        firebaseAuth =FirebaseAuth.getInstance();
        user =firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 =firebaseDatabase.getReference("Doctors");
        storageReference=getInstance().getReference();


        // init views
        phoneTV=findViewById(R.id.phoneTV);
        avatarIV =findViewById(R.id.avatarIV);
        nameTV =findViewById(R.id.nameTV);
        qualificationTV =findViewById(R.id.qualificationTV);
        emailTV =findViewById(R.id.emailTV);
        locationTV=findViewById(R.id.locationTV);
        specialityTV=findViewById(R.id.specialityTV);

        fab=findViewById(R.id.fab);

        pd =new ProgressDialog(this);

        //init array of permission
        cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        Query query =databaseReference1.orderByChild("email").equalTo(user.getEmail());
        pd.setMessage("Please Wait....");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String name =""+ds.child("name").getValue();
                    String email =""+ds.child("email").getValue();
                    String image =""+ds.child("image").getValue();
                    String phone =""+ds.child("Phone").getValue();
                    String qualification =""+ds.child("qualification").getValue();
                    String location =""+ds.child("location").getValue();
                    String speciality =""+ds.child("speciality").getValue();
                    String name1= "Dr. "+name;
                    nameTV.setText(name1);
                    emailTV.setText(email);
                    phoneTV.setText(phone);
                    qualificationTV.setText(qualification);
                    locationTV.setText(location);
                    specialityTV.setText(speciality);

                    try {
                        Picasso.get().load(image).into(avatarIV);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.ic_person).into(avatarIV);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });


    }

    private boolean checkStoragePermissions1(){
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private  void requestStoragePermission(){
        requestPermissions(storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private  void requestCameraPermission(){
        requestPermissions(cameraPermissions,CAMERA_REQUEST_CODE);
    }

    private void showEditProfileDialog() {

        String[] options ={"Edit Photo","Edit Name","Edit Phone No","Edit Qualification","Edit Location","Edit Speciality"};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle FAB clicks
                if(which==0){
                    //handle Edit Photo
                    pd.setMessage("uploading Image");
                    profilePhotoPic="image";
                    showImagePickDialog();

                }
                else if(which==1){
                    //handle edit name
                    pd.setMessage("uploading Name");
                    showDetailUpdateDialog("name");


                }
                else if(which==2){
                    //handle edit phone
                    pd.setMessage("uploading Phone");
                    showDetailUpdateDialog("Phone");



                }
                else if(which==3){
                    //Handle edit Qualification
                    pd.setMessage("uploading Qualification");
                    showDetailUpdateDialog("qualification");

                }
                else if(which==4){
                    //Handle edit Location
                    pd.setMessage("uploading Location");
                    showDetailUpdateDialog("location");

                }
                else if(which==5){
                    //Handle edit Location
                    pd.setMessage("uploading speciality");
                    showDetailUpdateDialog("speciality");

                }
            }
        });
        builder.create();
        builder.show();
    }

    private void showDetailUpdateDialog(final String keyName) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Update "+keyName);
        LinearLayout linearLayout =new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        TextInputLayout textInputLayout = new TextInputLayout(this);
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textInputLayout.setPadding(10,10,10,10);

        final TextInputEditText editText =new TextInputEditText(this);
        editText.setHint("Enter "+ keyName);
        textInputLayout.addView(editText);
        linearLayout.addView(textInputLayout);
        builder.setView(linearLayout);

        //button for update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value =editText.getText().toString();
                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String,Object> result =new HashMap<>();
                    result.put(keyName,value);
                    databaseReference1.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();

                                    Toast.makeText(DoctorProfile.this,keyName+" Updated...",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(DoctorProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else{
                    Toast.makeText(DoctorProfile.this,"Please enter "+keyName,Toast.LENGTH_SHORT).show();

                }



            }
        });
        //cancel button on dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.create().show();

    }

    private void showImagePickDialog() {

        String[] options ={"Camera","Gallery"};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle FAB clicks
                if(which==0){
                    //handle camera
                    if(!checkCameraPermissions()){
                        requestCameraPermission();
                    }
                    else{
                        pickFromCamera();
                    }
                }
                else if(which==1){
                    //handle gallery
                    if(!checkStoragePermissions()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create();
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode== RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_REQUEST_CODE){
                //image pick from gallery and get image uri
                image_uri =data.getData();
                uploadProfilePicture(image_uri);

            }
            if(requestCode==IMAGE_PICK_CAMERA_REQUEST_CODE){
                //image pick from camera and get image uri
                uploadProfilePicture(image_uri);


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfilePicture(Uri uri) {
        pd.show();

        // create a storage path for individual users
        String filePathAndName=storagePath+""+profilePhotoPic+"_"+ user.getUid();

        StorageReference storageReference2 = storageReference.child(filePathAndName);
        storageReference2.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Now Task(image Uploading) is complete , now we try to get image url and put that on database
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri= uriTask.getResult();
                        if (uriTask.isSuccessful()){
                            HashMap<String,Object> results= new HashMap<>();

                            results.put(profilePhotoPic,downloadUri.toString().trim());

                            databaseReference1.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pd.dismiss();
                                            Toast.makeText(DoctorProfile.this,"Image Updated....",Toast.LENGTH_SHORT);

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // dismiss pd and toast that error hapee
                                            pd.dismiss();
                                            Toast.makeText(DoctorProfile.this,"Failure!!Image not updated!!!",Toast.LENGTH_SHORT);


                                        }
                                    });

                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(DoctorProfile.this,"some error Happens",Toast.LENGTH_SHORT);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(DoctorProfile.this,e.getMessage(),Toast.LENGTH_SHORT);

                    }
                });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted= grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted= grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted &&writeStorageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(DoctorProfile.this,"Please Enable Camera Permission",Toast.LENGTH_SHORT);
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean writeStorageAccepted= grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(writeStorageAccepted){
                        pickFromGallery();
                    }
                    else{
                        Toast.makeText(DoctorProfile.this,"Please Enable Storage Permission",Toast.LENGTH_SHORT);
                    }
                }
            }
            break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void pickFromGallery() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_REQUEST_CODE);



    }

    private void pickFromCamera() {
        ContentValues contentValues =new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp Pic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp Description");

        image_uri=this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_REQUEST_CODE);
    }




    private void checkUserStatus(){
        FirebaseUser user =firebaseAuth.getCurrentUser();
        if(user!=null){
            //stay here
        }
        else{
            //no user found go to main
            startActivity(new Intent(DoctorProfile.this, LoginActivity.class));
            finish();
        }
    }

    private boolean checkStoragePermissions(){
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }




    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
