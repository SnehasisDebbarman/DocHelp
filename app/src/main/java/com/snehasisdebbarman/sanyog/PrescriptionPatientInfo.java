package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PrescriptionPatientInfo extends AppCompatActivity {
    EditText patientNameET,patientAgeET,patientBloodGroupET,patientEmailET,patientPhoneET,patientBloodPressureET,patientWeightET,patientBodyTempET,patientMedicalCondition;
    FloatingActionButton fab_save_info;
    FirebaseAuth mAuth,mAuth2;
    DatabaseReference databaseReference;
    ProgressDialog mProgressDialog;
    CardView medicalInformationLL,patientInformation,showPatientinfo;
    TextView pname,pemail,pphone,medicalInformationTV;


    TextView maleTV,femaleTV;
    String gender;
    String doctor_uid;

    Button medicalInformationBtn;

    ActionBar actionBar;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_patient_info);
        actionBar=getSupportActionBar();
        actionBar.hide();

        databaseReference=FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        user.getUid();

        patientNameET=findViewById(R.id.patientNameET);
        patientEmailET=findViewById(R.id.patientEmailET);
        patientAgeET=findViewById(R.id.patientAgeET);
        patientBloodGroupET=findViewById(R.id.patientBloodGroupET);
        patientPhoneET=findViewById(R.id.patientPhoneET);
        patientBloodPressureET=findViewById(R.id.patientBloodPressureET);
        patientWeightET=findViewById(R.id.patientWeightET);
        patientBodyTempET=findViewById(R.id.patientBodyTempET);
        patientMedicalCondition=findViewById(R.id.patientMedicalCondition);


        fab_save_info=findViewById(R.id.fab_save_Info);

        medicalInformationLL=findViewById(R.id.medicalInformationLL);
        medicalInformationBtn=findViewById(R.id.medicalInformationBtn);
        patientInformation=findViewById(R.id.patientInformation);
        medicalInformationTV=findViewById(R.id.medicalInformationBtnTV);

        showPatientinfo=findViewById(R.id.showPatientinfo);

        pname=findViewById(R.id.pname);
        pemail=findViewById(R.id.pemail);
        pphone=findViewById(R.id.pphone);
        maleTV=findViewById(R.id.maleTV);
        femaleTV=findViewById(R.id.femaleTV);

        maleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleTV.setBackground(getResources().getDrawable(R.drawable.button_enabled));
                femaleTV.setBackground(getResources().getDrawable(R.drawable.disable_button_rounded_main_gradient));
                femaleTV.setTextColor(getResources().getColor(R.color.black));
                maleTV.setTextColor(getResources().getColor(R.color.white));
                gender="male";
                setGender(gender);
            }
        });

        femaleTV.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                femaleTV.setBackground(getResources().getDrawable(R.drawable.button_enabled));
                maleTV.setBackground(getResources().getDrawable(R.drawable.disable_button_rounded_main_gradient));
                femaleTV.setTextColor(getResources().getColor(R.color.white));
                maleTV.setTextColor(getResources().getColor(R.color.black));
                gender="female";
                setGender(gender);
            }
        });

        medicalInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicalInformationBtn.setVisibility(View.GONE);
                patientInformation.setVisibility(View.GONE);

                medicalInformationLL.setVisibility(View.VISIBLE);
                medicalInformationTV.setVisibility(View.VISIBLE);


                 String email=patientEmailET.getText().toString().trim();
                 String phone=patientPhoneET.getText().toString().trim();
                 String name=patientNameET.getText().toString().trim();
                pname.setText(name);
                pemail.setText(email);
                pphone.setText(phone);

                showPatientinfo.setVisibility(View.VISIBLE);


            }
        });
        fab_save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog =new ProgressDialog(PrescriptionPatientInfo.this);
                mProgressDialog.setMessage("registering Patient......");

                String email=patientEmailET.getText().toString().trim();
                final String id=patientPhoneET.getText().toString().trim();
                final String name=patientNameET.getText().toString().trim();

                patientNameET.setText("");
                patientEmailET.setText("");
                patientPhoneET.setText("");

                //Start register another-----------------------------------------//
                mAuth = FirebaseAuth.getInstance();
               // FirebaseUser user = mAuth.getCurrentUser();

                FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                        .setDatabaseUrl("https://sanyog-f637a.firebaseio.com/")
                        .setApiKey("AIzaSyD2pRl9itH_8kEwOmGnLz6gB_nXWCkbLkM")
                        .setApplicationId("1:209676966705:android:ec83677e9ec0ddeb74ae10")
                        .build();
                mProgressDialog.show();

                try {
                    FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
                    mAuth2 = FirebaseAuth.getInstance(myApp);
                } catch (IllegalStateException e){
                    mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
                }
                try {
                    // createAccount(email,id,name,phone,bg,doct_uid);
                        mAuth2.createUserWithEmailAndPassword(email, id)
                                .addOnCompleteListener(PrescriptionPatientInfo.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (!task.isSuccessful()) {
                                            String ex = task.getException().toString();
                                            Toast.makeText(PrescriptionPatientInfo.this, "Registration Failed"+ex,
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            mProgressDialog.dismiss();
                                            Toast.makeText(PrescriptionPatientInfo.this, "Registration successful",
                                                    Toast.LENGTH_SHORT).show();
                                            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
                                            FirebaseUser user = mAuth2.getCurrentUser();
                                            String uid =user.getUid();
                                            String  email1 =user.getEmail();
                                            DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("Patients");
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                            HashMap<Object,String> hashMap =new HashMap<>();
                                            hashMap.put("id",uid);
                                            hashMap.put("patientEmail",email1);
                                            hashMap.put("patientName",name);// will add later one edit profile
                                            hashMap.put("patientAge",patientAgeET.getText().toString().trim());
                                            hashMap.put("patientBloodGroup",patientBloodGroupET.getText().toString().trim());
                                            hashMap.put("patientPhoneET",id);
                                            hashMap.put("patientBloodPressure",patientBloodPressureET.getText().toString().trim());
                                            hashMap.put("patientWeight",patientWeightET.getText().toString().trim());
                                            hashMap.put("patientBodyTemp",patientBodyTempET.getText().toString().trim());
                                            hashMap.put("patientMedicalCondition",patientMedicalCondition.getText().toString().trim());
                                            hashMap.put("patientGender",getGender());
                                            reference.child(uid).setValue(hashMap);


                                            HashMap<Object,String> hmap =new HashMap<>();
                                            hmap.put("patient_uid",uid);
                                            hmap.put("isPatient","true");
                                            hmap.put("uid","");
                                            hmap.put("isDoctor","false");
                                            // path to store in 'Users'

                                            // put data in hasmap
                                            ref.push().setValue(hmap);




                                            finish();
                                            mAuth2.signOut();

                                            //
                                            try {
                                                Intent intent =new Intent(PrescriptionPatientInfo.this,PrescriptionFinal.class);
                                                intent.putExtra("patient_uid", uid);
                                                intent.putExtra("patientName",name);
                                                intent.putExtra("patientAge",patientAgeET.getText().toString().trim());
                                                intent.putExtra("patientBloodGroup",patientBloodGroupET.getText().toString().trim());
                                                intent.putExtra("patientEmail",email1);
                                                intent.putExtra("patientPhoneET",id);
                                                intent.putExtra("patientBloodPressure",patientBloodPressureET.getText().toString().trim());
                                                intent.putExtra("patientWeight",patientWeightET.getText().toString().trim());
                                                intent.putExtra("patientBodyTemp",patientBodyTempET.getText().toString().trim());
                                                intent.putExtra("patientMedicalCondition",patientMedicalCondition.getText().toString().trim());
                                                startActivity(intent);

                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mProgressDialog.dismiss();
                                Toast.makeText(PrescriptionPatientInfo.this, "Some Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });



                }catch (Exception es){
                    es.printStackTrace();
                    mAuth2.signInWithEmailAndPassword(email,id)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                        mProgressDialog.dismiss();
                                        Toast.makeText(PrescriptionPatientInfo.this, "Registration successful",
                                                Toast.LENGTH_SHORT).show();
                                        mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
                                        FirebaseUser user = mAuth2.getCurrentUser();
                                        String uid =user.getUid();
                                        String  email1 =user.getEmail();
                                        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("Patients");
                                        HashMap<Object,String> hashMap =new HashMap<>();
                                        hashMap.put("id",uid);
                                        hashMap.put("patientEmail",email1);
                                        hashMap.put("patientName",patientNameET.getText().toString().trim());// will add later one edit profile
                                        hashMap.put("patientAge",patientAgeET.getText().toString().trim());
                                        hashMap.put("patientBloodGroup",patientBloodGroupET.getText().toString().trim());
                                        hashMap.put("patientPhoneET",patientPhoneET.getText().toString().trim());
                                        hashMap.put("patientBloodPressure",patientBloodPressureET.getText().toString().trim());
                                        hashMap.put("patientWeight",patientWeightET.getText().toString().trim());
                                        hashMap.put("patientBodyTemp",patientBodyTempET.getText().toString().trim());
                                        hashMap.put("patientMedicalCondition",patientMedicalCondition.getText().toString().trim());
                                        hashMap.put("patientGender",getGender());
                                        reference.child(uid).setValue(hashMap);
                                        finish();
                                        mAuth2.signOut();

                                        //
                                        try {
                                            Intent intent =new Intent(PrescriptionPatientInfo.this,PrescriptionFinal.class);
                                            intent.putExtra("patient_uid", uid);
                                            intent.putExtra("patientName",patientNameET.getText().toString().trim());
                                            intent.putExtra("patientAge",patientAgeET.getText().toString().trim());
                                            intent.putExtra("patientBloodGroup",patientBloodGroupET.getText().toString().trim());
                                            intent.putExtra("patientEmail",patientEmailET.getText().toString().trim());
                                            intent.putExtra("patientPhoneET",patientPhoneET.getText().toString().trim());
                                            intent.putExtra("patientBloodPressure",patientBloodPressureET.getText().toString().trim());
                                            intent.putExtra("patientWeight",patientWeightET.getText().toString().trim());
                                            intent.putExtra("patientBodyTemp",patientBodyTempET.getText().toString().trim());
                                            intent.putExtra("patientMedicalCondition",patientMedicalCondition.getText().toString().trim());
                                            startActivity(intent);

                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }



                                }
                            });




                }






            }
        });
    }



}
