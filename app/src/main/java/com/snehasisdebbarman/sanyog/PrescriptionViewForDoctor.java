package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

public class PrescriptionViewForDoctor extends AppCompatActivity {
    RecyclerView prescription_list_RV;
    AdapterPrescription adapterPrescription;
    List<ModelPrescription> prescriptionList;
    List<ModelPatient> modelPatients;
    TextView patientName,patientBloodGroup,patientEmail,patientPhoneET;
    FloatingActionButton fab_add_prescription;
    LineChart tempLineChart,weightLineChart,sugarLineChart;
    LineChart pressureLineChart;

    String PatientUid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view_for_doctor);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        prescription_list_RV=findViewById(R.id.prescription_list_RV);
        patientName=findViewById(R.id.patientName);

        patientBloodGroup=findViewById(R.id.patientBloodGroup);
        patientEmail=findViewById(R.id.patientEmail);
        patientPhoneET=findViewById(R.id.patientPhoneET);

        tempLineChart=findViewById(R.id.tempLineChart);
        weightLineChart=findViewById(R.id.weightLineChart);
        sugarLineChart=findViewById(R.id.sugarLineChart);
        pressureLineChart=findViewById(R.id.pressureLineChart);


        fab_add_prescription=findViewById(R.id.fab_add_prescription);

        prescription_list_RV.setHasFixedSize(true);
        prescription_list_RV.setLayoutManager(new LinearLayoutManager(this));

        final String PatientUid =getIntent().getStringExtra("PatientUid");
        PatientUid1=getIntent().getStringExtra("PatientUid");

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

        try {
            getTempGraph(PatientUid);
            getWeightGraph(PatientUid);
            getSugarGraph(PatientUid);
            getPressureGraph(PatientUid);


        }catch (Exception e){
            e.printStackTrace();
        }


        //add prescription=-----
        fab_add_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Query query = FirebaseDatabase.getInstance().getReference("Patients")
                        .orderByChild("id")
                        .equalTo(PatientUid);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            final ModelPatient modelPatient=ds.getValue(ModelPatient.class);
                            String pn="Name: "+modelPatient.getPatientName();
                            final String pbg="Blood Group: "+modelPatient.getPatientBloodGroup();
                            String pe="Email: : "+modelPatient.getPatientEmail();
                            String pp="Phone: "+modelPatient.getPatientPhoneET();
                            patientName.setText(pn);
                            patientBloodGroup.setText(pbg);
                            patientEmail.setText(pe);
                            patientPhoneET.setText(pp);



                            //alert dialog for emailskdjkdsjjsh
                            AlertDialog.Builder builder= new AlertDialog.Builder(PrescriptionViewForDoctor.this);
                            builder.setTitle("Add Patient medical Information");

                            LayoutInflater inflater = PrescriptionViewForDoctor.this.getLayoutInflater();
                            View view =inflater.inflate(R.layout.update_medical_info_alert_dialog, null);
                            final TextInputEditText patientBloodPressure11=view.findViewById(R.id.patientBloodPressure);
                            final TextInputEditText patientWeight11=view.findViewById(R.id.patientWeight);
                            final TextInputEditText patientBodyTemp11=view.findViewById(R.id.patientBodyTemp);
                            final TextInputEditText patientMedicalCondition11=view.findViewById(R.id.patientMedicalCondition);
                            final TextInputEditText patientSugar11=view.findViewById(R.id.patientSuger);


                            // inflate and set the layout for the dialog
                            // pass null as the parent view because its going in the dialog layout
                            builder.setView(view);


                                    builder.setPositiveButton("ADD INFO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String pbp= patientBloodPressure11.getText().toString().trim();
                                        String pw= patientWeight11.getText().toString().trim();
                                        String pbt= patientBodyTemp11.getText().toString().trim();
                                        String pmc= patientMedicalCondition11.getText().toString().trim();
                                        String ps=patientSugar11.getText().toString().trim();

                                        Intent intent =new Intent(PrescriptionViewForDoctor.this,PrescriptionFinal.class);
                                        intent.putExtra("patient_uid", modelPatient.getId());
                                        intent.putExtra("patientName",modelPatient.getPatientName());
                                        intent.putExtra("patientAge",modelPatient.getPatientAge());
                                        intent.putExtra("patientBloodGroup",modelPatient.getPatientBloodGroup());
                                        intent.putExtra("patientEmail",modelPatient.getPatientEmail());
                                        intent.putExtra("patientPhoneET",modelPatient.getPatientPhoneET());
                                        intent.putExtra("patientBloodPressure",pbp);
                                        intent.putExtra("patientWeight",pw);
                                        intent.putExtra("patientBodyTemp",pbt);
                                        intent.putExtra("patientMedicalCondition", pmc);
                                        intent.putExtra("patientSugar", ps);
                                        startActivity(intent);

                                    }catch (NullPointerException e){
                                        e.printStackTrace();

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

                            //alert dialog for emailskdjkdsjjsh


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });




    }

    private void getPressureGraph(String patientUid) {
        Query query = FirebaseDatabase.getInstance().getReference("prescription_list/"+patientUid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> xAxisList =new ArrayList<>();
                List<Entry> dataSet1=new ArrayList<>();
                List<Entry> dataSet2=new ArrayList<>();
                int i=0;
                float bt,bt2;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    final ModelPrescription modelPrescription = ds.getValue(ModelPrescription.class);
                    String bodyTemp= modelPrescription.getPatientBloodPressure();
                    String ts=modelPrescription.getTimestamp();
                    String date = DateFormat.format("d MMM, ''yy", Long.parseLong(ts)).toString();

                    try {
                        String[] parts = bodyTemp.split("/");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1];


                        bt=Float.parseFloat(part1);
                        bt2=Float.parseFloat(part2);
                    }catch (Exception e){
                        bt=0.0f;
                        bt2=0.0f;
                    }
                    dataSet1.add(new Entry(i,bt));
                    dataSet2.add(new Entry(i,bt2));
                    xAxisList.add(date);
                    i++;
                }


                XAxis xAxis = pressureLineChart.getXAxis();




                LineDataSet lineDataSet=new LineDataSet(dataSet1,"High BP");
                lineDataSet.setColor(Color.GREEN);
                lineDataSet.setCircleColor(Color.GREEN);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);



                LineDataSet lineDataSet2=new LineDataSet(dataSet2,"Low BP");
                lineDataSet2.setColor(Color.CYAN);
                lineDataSet2.setCircleColor(Color.CYAN);
                lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);


                List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(lineDataSet);
                dataSets.add(lineDataSet2);
                LineData lineDatas=new LineData(dataSets);

                pressureLineChart.setData(lineDatas);
                pressureLineChart.animateY(1000);







                pressureLineChart.setDrawGridBackground(false);
                pressureLineChart.setExtraLeftOffset(15);
                pressureLineChart.setExtraRightOffset(15);
                //to hide background lines
                pressureLineChart.getXAxis().setDrawGridLines(false);
                pressureLineChart.getAxisLeft().setDrawGridLines(false);
                pressureLineChart.getAxisRight().setDrawGridLines(false);
                pressureLineChart.getDescription().setEnabled(false);

                //to hide right Y and top X border
                YAxis rightYAxis = pressureLineChart.getAxisRight();
                rightYAxis.setEnabled(false);
                YAxis leftYAxis = pressureLineChart.getAxisLeft();
                leftYAxis.setEnabled(true);
                leftYAxis.setDrawLabels(false);

                XAxis topXAxis = pressureLineChart.getXAxis();
                topXAxis.setEnabled(false);



                xAxis.setGranularity(1f);
                // xAxis.setCenterAxisLabels(true);
                xAxis.setEnabled(true);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(3f);

                pressureLineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisList));

                pressureLineChart.invalidate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSugarGraph(String patientUid) {
        Query query = FirebaseDatabase.getInstance().getReference("prescription_list/"+patientUid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> xAxisWeightList =new ArrayList<>();
                List<Entry> dataSet=new ArrayList<>();
                int i=0;
                float bt;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    final ModelPrescription modelPrescription = ds.getValue(ModelPrescription.class);
                    String bodyTemp= modelPrescription.getPatientSugar();
                    String ts=modelPrescription.getTimestamp();
                    String date = DateFormat.format("d MMM, ''yy", Long.parseLong(ts)).toString();
                    try {
                        bt=Float.parseFloat(bodyTemp);
                    }catch (Exception e){
                        bt= 0.0f;
                    }
                    dataSet.add(new Entry(i,bt));
                    xAxisWeightList.add(date);
                    i++;
                }
                LineDataSet lineDataSet=new LineDataSet(dataSet,"Sugar in mg");
                lineDataSet.setDrawFilled(true);
                lineDataSet.setColor(Color.MAGENTA);
                lineDataSet.setCircleColor(Color.MAGENTA);
                lineDataSet.setFillColor(Color.MAGENTA);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                LineData lineData=new LineData(lineDataSet);
                sugarLineChart.setData(lineData);
                sugarLineChart.setDrawGridBackground(false);
                sugarLineChart.setExtraLeftOffset(15);
                sugarLineChart.setExtraRightOffset(15);
                //to hide background lines
                sugarLineChart.getXAxis().setDrawGridLines(false);
                sugarLineChart.getAxisLeft().setDrawGridLines(false);
                sugarLineChart.getAxisRight().setDrawGridLines(false);
                sugarLineChart.getDescription().setEnabled(false);

                //to hide right Y and top X border
                YAxis rightYAxis = sugarLineChart.getAxisRight();
                rightYAxis.setEnabled(false);
                YAxis leftYAxis = sugarLineChart.getAxisLeft();
                leftYAxis.setEnabled(true);
                leftYAxis.setDrawLabels(false);

                XAxis topXAxis = sugarLineChart.getXAxis();
                topXAxis.setEnabled(false);


                XAxis xAxis = sugarLineChart.getXAxis();
                xAxis.setGranularity(1f);
                // xAxis.setCenterAxisLabels(true);
                xAxis.setEnabled(true);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(3f);

                sugarLineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisWeightList));

                sugarLineChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getWeightGraph(String patientUid) {
        Query query = FirebaseDatabase.getInstance().getReference("prescription_list/"+patientUid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> xAxisWeightList =new ArrayList<>();
                List<Entry> dataSet=new ArrayList<>();
                int i=0;
                float bt;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    final ModelPrescription modelPrescription = ds.getValue(ModelPrescription.class);
                    String bodyTemp= modelPrescription.getPatientWeight();
                    String ts=modelPrescription.getTimestamp();
                    String date = DateFormat.format("d MMM, ''yy", Long.parseLong(ts)).toString();
                    try {
                        bt=Float.parseFloat(bodyTemp);
                    }catch (Exception e){
                        bt= 0.0f;
                    }
                    dataSet.add(new Entry(i,bt));
                    xAxisWeightList.add(date);
                    i++;
                }
                LineDataSet lineDataSet=new LineDataSet(dataSet,"Weight in KG");
                lineDataSet.setDrawFilled(true);
                lineDataSet.setColor(Color.RED);
                lineDataSet.setCircleColor(Color.RED);
                lineDataSet.setFillColor(Color.RED);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                LineData lineData=new LineData(lineDataSet);
                weightLineChart.setData(lineData);
                weightLineChart.setDrawGridBackground(false);
                weightLineChart.setExtraLeftOffset(15);
                weightLineChart.setExtraRightOffset(15);
                //to hide background lines
                weightLineChart.getXAxis().setDrawGridLines(false);
                weightLineChart.getAxisLeft().setDrawGridLines(false);
                weightLineChart.getAxisRight().setDrawGridLines(false);
                weightLineChart.getDescription().setEnabled(false);

                //to hide right Y and top X border
                YAxis rightYAxis = weightLineChart.getAxisRight();
                rightYAxis.setEnabled(false);
                YAxis leftYAxis = weightLineChart.getAxisLeft();
                leftYAxis.setEnabled(true);
                leftYAxis.setDrawLabels(false);

                XAxis topXAxis = weightLineChart.getXAxis();
                topXAxis.setEnabled(false);


                XAxis xAxis = weightLineChart.getXAxis();
                xAxis.setGranularity(1f);
                // xAxis.setCenterAxisLabels(true);
                xAxis.setEnabled(true);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(3f);

                weightLineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisWeightList));

                weightLineChart.invalidate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getTempGraph(String PatientUid) {
        Query query = FirebaseDatabase.getInstance().getReference("prescription_list/"+PatientUid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    String date = DateFormat.format("d MMM, ''yy", Long.parseLong(ts)).toString();
                    try {
                        bt=Float.parseFloat(bodyTemp);
                    }catch (Exception e){
                        bt=0.0f;
                    }
                    dataSet.add(new Entry(i,bt));
                    xAxisList.add(date);
                    i++;
                }
                LineDataSet lineDataSet=new LineDataSet(dataSet,"Body Temperature");
                lineDataSet.setDrawFilled(true);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

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
                xAxis.setTextSize(3f);

                tempLineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisList));

                tempLineChart.invalidate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllPrescription(String PatientUid) {

        Query query = FirebaseDatabase.getInstance().getReference("prescription_list/"+PatientUid);
              /*  .orderByChild("patient_uid")
                .equalTo(PatientUid);*/
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
