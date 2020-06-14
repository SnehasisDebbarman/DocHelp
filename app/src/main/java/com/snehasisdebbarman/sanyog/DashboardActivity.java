package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    CardView profileCV,prescriptionCV,patientCV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //action bar
        actionBar= getSupportActionBar();
        actionBar.setTitle("Dashboard");
        actionBar.setElevation(0);
        firebaseAuth = FirebaseAuth.getInstance();

        profileCV =findViewById(R.id.cardView1);
        prescriptionCV =findViewById(R.id.cardView2);
        patientCV =findViewById(R.id.cardView3);

        profileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DoctorProfile.class));
            }
        });
        prescriptionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, PrescriptionPatientInfo.class));
            }
        });
        patientCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this, PatientsList.class);
                startActivity(intent);
            }
        });


    }

    private void checkUserStatus(){
        FirebaseUser user =firebaseAuth.getCurrentUser();
        if(user!=null){
            final String id=user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        if( ds.child("uid").getValue().toString().equals(id) ){
                            if(ds.child("isDoctor").getValue().toString().equals("true")){

                            }
                            else{
                                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                                Toast.makeText(DashboardActivity.this, "error in dashboard", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //stay here
        }
        else{
            //no user found go to main
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        }
    }

   @Override
    protected void onStart() {

        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.action_menu){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
