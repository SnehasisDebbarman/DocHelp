package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientMedicineListForDoctor extends AppCompatActivity {
    RecyclerView medicine_list_RV;
    AdapterMedicine adapterMedicine;

    List<ModelMedicine> medicineList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medicine_list_for_doctor);
        Intent i=getIntent();
        String PatientUid=i.getStringExtra("PatientUid");

        try {
            medicine_list_RV=findViewById(R.id.medicine_list_RV);
            medicine_list_RV.setHasFixedSize(true);
            medicine_list_RV.setLayoutManager(new LinearLayoutManager(this));
            medicineList =new ArrayList<>();
            getAllMedicines(PatientUid);
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    private void getAllMedicines(String PatientUid) {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        Query query =FirebaseDatabase.getInstance().getReference("medicine")
                .orderByChild("patient_uid")
                .equalTo(PatientUid);

       // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("medicine");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ModelMedicine modelMedicine=dataSnapshot1.getValue(ModelMedicine.class);
                    try {
                        medicineList.add(modelMedicine);
                        adapterMedicine =new AdapterMedicine(PatientMedicineListForDoctor.this,medicineList);
                        medicine_list_RV.setAdapter(adapterMedicine);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
