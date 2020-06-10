package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText mEmailEt,mPassEt;
    Button mLoginDoctorBtn,mLoginPatientBtn,selectPatient,selectDoctor;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    TextView have_account,forget_pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar actionBar =getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("LOGIN");
        actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();


        mEmailEt=findViewById(R.id.emailET);
        mPassEt=findViewById(R.id.passET);
        mLoginDoctorBtn=findViewById(R.id.loginDoctorBtn);
        mLoginPatientBtn=findViewById(R.id.loginPatientBtn);
        have_account=findViewById(R.id.have_Account);
        forget_pass=findViewById(R.id.forget_password);
        selectPatient=findViewById(R.id.loginPatientSelect);
        selectDoctor=findViewById(R.id.loginDoctorSelect);


        selectPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginDoctorBtn.setVisibility(View.GONE);
                mLoginPatientBtn.setVisibility(View.VISIBLE);
                selectPatient.setVisibility(View.GONE);
                selectDoctor.setVisibility(View.VISIBLE);
            }
        });
        selectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginDoctorBtn.setVisibility(View.VISIBLE);
                mLoginPatientBtn.setVisibility(View.GONE);
                selectDoctor.setVisibility(View.GONE);
                selectPatient.setVisibility(View.VISIBLE);
            }
        });


        mProgressDialog =new ProgressDialog(this);
        mProgressDialog.setMessage("Logging......");


        mLoginDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmailEt.getText().toString();
                String password=mPassEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);

                }
                else{
                    loginDoctor(email,password);

                }
            }
        });

        mLoginPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email=mEmailEt.getText().toString();
                    String password=mPassEt.getText().toString().trim();
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                        mEmailEt.setError("Invalid Email");
                        mEmailEt.setFocusable(true);

                    }
                    else{
                      loginPatient(email,password);

                    }


            }
        });


        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");


        //layout
        LinearLayout linearLayout= new LinearLayout(this);
        TextInputLayout inputLayout =new TextInputLayout(this);
        final TextInputEditText emailET=  new TextInputEditText(this);

        emailET.setHint("Email");
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailET.setMinEms(16);

        inputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        inputLayout.setPadding(10,10,10,10);
        inputLayout.addView(emailET);

        linearLayout.addView(inputLayout);
        linearLayout.setElevation(0);


        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String email= Objects.requireNonNull(emailET.getText()).toString().trim();

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        emailET.setError("Invalid Email");
                        emailET.setFocusable(true);
                        Toast.makeText(LoginActivity.this, "Enter a valid Email Address", Toast.LENGTH_SHORT).show();

                    }else{
                        beginRecovery(email);
                    }

                }catch (NullPointerException e){
                    Toast.makeText(LoginActivity.this, "Enter a valid Email Address", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();

    }

    private void beginRecovery(String email) {
        mProgressDialog.setMessage("Sending......");
        mProgressDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mProgressDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Email sent",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        try {
                            Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loginDoctor(String email, String password) {
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                final  String uid=user.getUid();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            if( ds.child("uid").getValue().toString().equals(uid)){
                                                if( ds.child("isDoctor").getValue().toString().equals("true")){
                                                    Toast.makeText(LoginActivity.this,"Doctors logged in.....\n",Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        else {
                                mProgressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loginPatient(String email, String password) {
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            final  String uid=user.getUid();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                                       if( ds.child("patient_uid").getValue().toString().equals(uid) ){
                                           if(ds.child("isPatient").getValue().toString().equals("true")){
                                               Toast.makeText(LoginActivity.this,"patient log in.....\n",Toast.LENGTH_SHORT).show();
                                               Intent i = new Intent(LoginActivity.this, PatientDashboardActivity.class);
                                               startActivity(i);
                                               finish();
                                               break;
                                           }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else {
                            mProgressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }



}
