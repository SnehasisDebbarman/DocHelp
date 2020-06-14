package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PatientMedicineListForDoctor extends AppCompatActivity {
    RecyclerView medicine_list_RV;
    AdapterMedicine adapterMedicine;
    String PatientUid,PrescriptionID;
   // LineChart tempLineChart;

    List<ModelPrescription> prescriptionList;

    List<ModelMedicine> medicineList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medicine_list_for_doctor);


        Intent i=getIntent();
        PatientUid=i.getStringExtra("PatientUid");
        PrescriptionID=i.getStringExtra("PrescriptionID");

        try {
            medicine_list_RV=findViewById(R.id.medicine_list_RV);
            medicine_list_RV.setHasFixedSize(true);
            medicine_list_RV.setLayoutManager(new LinearLayoutManager(this));
            medicineList =new ArrayList<>();
            getAllMedicines(PatientUid,PrescriptionID);
        }
        catch (Exception e){
            e.printStackTrace();
        }
       // getTempGraph();



    }

   /* private void getTempGraph() {
        Query query = FirebaseDatabase.getInstance().getReference("prescription_list/"+PatientUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> xAxisList =new ArrayList<>();
                List<Entry> dataSet=new ArrayList<>();
                int i=0;
                float bt;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    final ModelPrescription modelPrescription = ds.getValue(ModelPrescription.class);
                    String bodyTemp= modelPrescription.getPatientBodyTemp();
                    String ts=modelPrescription.getTimestamp();
                    try {
                        bt=Float.parseFloat(bodyTemp);
                    }catch (Exception e){
                        bt=0.0f;
                    }
                    dataSet.add(new Entry(i,bt));
                    xAxisList.add(ts);
                    i++;
                }
                LineDataSet lineDataSet=new LineDataSet(dataSet,"Body Temperature");
                lineDataSet.setDrawFilled(true);
                lineDataSet.setMode(LineDataSet.Mode.LINEAR);

                LineData lineData=new LineData(lineDataSet);

                tempLineChart.setData(lineData);

                tempLineChart.setDrawGridBackground(false);
                tempLineChart.setExtraLeftOffset(15);
                tempLineChart.setExtraRightOffset(15);
                //to hide background lines
                tempLineChart.getXAxis().setDrawGridLines(false);
                tempLineChart.getAxisLeft().setDrawGridLines(false);
                tempLineChart.getAxisRight().setDrawGridLines(false);
                tempLineChart.getDescription().setEnabled(false);

                //to hide right Y and top X border
                YAxis rightYAxis = tempLineChart.getAxisRight();
                rightYAxis.setEnabled(false);
                YAxis leftYAxis = tempLineChart.getAxisLeft();
                leftYAxis.setEnabled(true);
                leftYAxis.setDrawLabels(false);

                XAxis topXAxis = tempLineChart.getXAxis();
                topXAxis.setEnabled(false);


                XAxis xAxis = tempLineChart.getXAxis();
                xAxis.setGranularity(1f);
                // xAxis.setCenterAxisLabels(true);
                xAxis.setEnabled(true);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                tempLineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisList));

                tempLineChart.invalidate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void getAllMedicines(String PatientUid, String PrescriptionID) {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


      /*  Query query =FirebaseDatabase.getInstance().getReference("medicine")
                .orderByChild("patient_uid")
                .equalTo(PatientUid);*/
        Query query1=FirebaseDatabase.getInstance().getReference("medicine")
                .orderByChild("prescription_id")
                .equalTo(PrescriptionID);

       // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("medicine");
        query1.addValueEventListener(new ValueEventListener() {
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
