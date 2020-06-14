package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientsList extends AppCompatActivity {
    RecyclerView patients_list_RV;

    AdapterPatient adapterPatient;
    List<ModelPatient> patientList;

    public String getPatient_uid() {
        return patient_uid;
    }

    public void setPatient_uid(String patient_uid) {
        this.patient_uid = patient_uid;
    }

    String patient_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        patients_list_RV=findViewById(R.id.patients_list_RV);
        patients_list_RV.setHasFixedSize(true);
        patients_list_RV.setLayoutManager(new LinearLayoutManager(this));

        patientList =new ArrayList<>();
        getAllPatients();

    }

    private void getAllPatients() {


        Query query =FirebaseDatabase.getInstance().getReference("prescription_list");
              /*  .orderByChild("doctor_uid")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
*/


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientList.clear();
                for (DataSnapshot ds1:dataSnapshot.getChildren()) {
                    setPatient_uid(ds1.getKey());
                    Query query1 =FirebaseDatabase.getInstance().getReference("Patients")
                            .orderByChild("id")
                            .equalTo(getPatient_uid());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds:dataSnapshot.getChildren()){
                                ModelPatient modelPatient =ds.getValue(ModelPatient.class);
                                patientList.add(modelPatient);
                                adapterPatient=new AdapterPatient(PatientsList.this,patientList);
                                patients_list_RV.setAdapter(adapterPatient);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                 /*   DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Patients");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            patientList.clear();
                            for (DataSnapshot ds:dataSnapshot.getChildren()){
                                ModelPatient modelPatient =ds.getValue(ModelPatient.class);
                                if(modelPatient.getId().equals(getPatient_uid())){
                                    patientList.add(modelPatient);
                                    adapterPatient=new AdapterPatient(PatientsList.this,patientList);
                                    patients_list_RV.setAdapter(adapterPatient);

                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
