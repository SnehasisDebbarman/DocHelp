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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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
        final FirebaseUser user=firebaseAuth.getCurrentUser();

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
