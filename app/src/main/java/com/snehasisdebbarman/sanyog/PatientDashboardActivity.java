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

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientDashboardActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    CardView profileCV,prescriptionCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();

       /* Intent intent=getIntent();
        String patient_uid=intent.getStringExtra("patient_uid");
     */
        //action bar
        actionBar= getSupportActionBar();
        actionBar.setTitle("Dashboard");

        profileCV =findViewById(R.id.cardView1);
        prescriptionCV =findViewById(R.id.cardView2);

        profileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, PatientProfile.class));
            }
        });
        prescriptionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, PrescriptionViewForPatient.class));
            }
        });



    }

    private void checkUserStatus1(){

        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null){



     //       startActivity(new Intent(PatientDashboardActivity.this, PatientDashboardActivity.class));stay here
        }
        else{
            //no user found go to main
           startActivity(new Intent(PatientDashboardActivity.this, MainActivity.class));
           finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus1();
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
            checkUserStatus1();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
