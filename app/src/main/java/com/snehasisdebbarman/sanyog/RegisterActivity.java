package com.snehasisdebbarman.sanyog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmailEt,mPassEt,mNameEt;
    Button mRegisterDoctorBtn,mRegisterPatientBtn;
    ProgressDialog mProgressDialog;
    TextView Have_account;
    private FirebaseAuth mAuth;
    RadioGroup radioGroup;
    String Ausers="Doctors";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //action
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        mEmailEt=findViewById(R.id.emailET);
        mNameEt=findViewById(R.id.nameET);
        mPassEt=findViewById(R.id.passET);
        mRegisterDoctorBtn=findViewById(R.id.registerBtn);
        mRegisterPatientBtn=findViewById(R.id.registerBtnPatient);
        Have_account=findViewById(R.id.have_Account);
        radioGroup=findViewById(R.id.radio);




        mProgressDialog =new ProgressDialog(this);
        mProgressDialog.setMessage("registering user......");


        /*// radio
        radioGroup  = findViewById(R.id.radio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.doctorClicked:
                        Ausers ="Doctors";
                        // do operations specific to this selection
                        break;
                    case R.id.patientClicked:
                        Ausers ="Patients";
                        // do operations specific to this selection
                        break;

                }
            }
        });*/
        // radio end

        mRegisterDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mNameEt.getText().toString().trim();
                String email=mEmailEt.getText().toString().trim();
                String password=mPassEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);

                }
                else if (password.length()<6){
                    mPassEt.setError("Invalid Password");
                    mPassEt.setFocusable(true);

                }
                else{
                    registerDoctor(email,password,name);
                }
            }
        });

        mRegisterPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mNameEt.getText().toString().trim();
                String email=mEmailEt.getText().toString().trim();
                String password=mPassEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);

                }
                else if (password.length()<6){
                    mPassEt.setError("Invalid Password");
                    mPassEt.setFocusable(true);

                }
                else{
                    registerPatient(email,password,name);
                }
            }
        });
        //handle account
        Have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });


    }

    private void registerDoctor(String email, String password,String name) {
        mProgressDialog.show();
        final String nn=name;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success , dis,iss dialog start  register activity
                            mProgressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email= user.getEmail();
                            String uid =user.getUid();
                            HashMap<Object,String> hashMap =new HashMap<>();

                                hashMap.put("email",email);
                                hashMap.put("doc_uid",uid);
                                hashMap.put("name","");// will add later one edit profile
                                hashMap.put("Phone","");// will add later one edit profile
                                hashMap.put("image","");
                                hashMap.put("qualification","");
                                hashMap.put("speciality","");
                                hashMap.put("location","");// will add later one edit profile// will add later one edit profile

                                //firebase database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                // path to store in 'Users'
                                DatabaseReference reference = database.getReference("Doctors");
                                // put data in hasmap
                                reference.child(uid).setValue(hashMap);

                                Toast.makeText(RegisterActivity.this,"Doctor Registered.....\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, DashboardActivity.class);
                                i.putExtra("auser", Ausers);
                                startActivity(i);
                               // startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                           mProgressDialog.dismiss();
                           Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void registerPatient(String email, String password,String name) {
        mProgressDialog.show();
        final String nn=name;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success , dis,iss dialog start  register activity
                            mProgressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email= user.getEmail();
                            String uid =user.getUid();
                            HashMap<Object,String> hashMap =new HashMap<>();

                            hashMap.put("email",email);
                            hashMap.put("doc_uid",uid);
                            hashMap.put("name",nn);// will add later one edit profile
                            hashMap.put("Phone","");// will add later one edit profile
                            hashMap.put("image","");
                            hashMap.put("qualification","");
                            hashMap.put("speciality","");
                            hashMap.put("location","");// will add later one edit profile// will add later one edit profile

                            //firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            // path to store in 'Users'
                            DatabaseReference reference = database.getReference("Patients");
                            // put data in hasmap
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(RegisterActivity.this,"Patients Registered.....\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, PatientDashboardActivity.class);
                            i.putExtra("auser", Ausers);
                            startActivity(i);
                            // startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            mProgressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    public boolean checkRegisterStatus(){
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();// go prev
        return super.onSupportNavigateUp();
    }


}
