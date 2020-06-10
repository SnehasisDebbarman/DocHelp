package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionViewForDoctor extends AppCompatActivity {
    RecyclerView prescription_list_RV;
    AdapterPrescription adapterPrescription;
    List<ModelPrescription> prescriptionList;
    List<ModelPatient> modelPatients;
    TextView patientName,patientBloodGroup,patientEmail,patientPhoneET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescrition_view_for_doctor);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        prescription_list_RV=findViewById(R.id.prescription_list_RV);
        patientName=findViewById(R.id.patientName);
        patientBloodGroup=findViewById(R.id.patientBloodGroup);
        patientEmail=findViewById(R.id.patientEmail);
        patientPhoneET=findViewById(R.id.patientPhoneET);

        prescription_list_RV.setHasFixedSize(true);
        prescription_list_RV.setLayoutManager(new LinearLayoutManager(this));
        String PatientUid =getIntent().getStringExtra("PatientUid");

        Query query = FirebaseDatabase.getInstance().getReference("Patients")
                .orderByChild("id")
                .equalTo(PatientUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    ModelPatient modelPatient=ds.getValue(ModelPatient.class);
                    String pn="Name: "+modelPatient.getPatientName();
                    String pbg="Blood Group: "+modelPatient.getPatientBloodGroup();
                    String pe="Email: : "+modelPatient.getPatientEmail();
                    String pp="Phone: "+modelPatient.getPatientPhoneET();
                    patientName.setText(pn);
                    patientBloodGroup.setText(pbg);
                    patientEmail.setText(pe);
                    patientPhoneET.setText(pp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        prescriptionList=new ArrayList<>();
        getAllPrescription(PatientUid);
    }

    private void getAllPrescription(String PatientUid) {

        Query query = FirebaseDatabase.getInstance().getReference("prescription_list")
                .orderByChild("patient_uid")
                .equalTo(PatientUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prescriptionList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ModelPrescription modelPrescription=dataSnapshot1.getValue(ModelPrescription.class);
                    prescriptionList.add(modelPrescription);
                    adapterPrescription =new AdapterPrescription(PrescriptionViewForDoctor.this,prescriptionList);
                    prescription_list_RV.setAdapter(adapterPrescription);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
