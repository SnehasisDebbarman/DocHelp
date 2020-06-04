package com.snehasisdebbarman.sanyog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PrescriptionPatientInfo extends AppCompatActivity {
    EditText patientNameET,patientAgeET,patientBloodGroupET,patientEmailET,patientPhoneET,patientBloodPressureET,patientWeightET,patientBodyTempET;
    FloatingActionButton fab_save_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        patientNameET=findViewById(R.id.patientNameET);
        patientEmailET=findViewById(R.id.patientEmailET);
        patientAgeET=findViewById(R.id.patientAgeET);
        patientBloodGroupET=findViewById(R.id.patientBloodGroupET);
        patientPhoneET=findViewById(R.id.patientPhoneET);
        patientBloodPressureET=findViewById(R.id.patientBloodPressureET);
        patientWeightET=findViewById(R.id.patientWeightET);
        patientBodyTempET=findViewById(R.id.patientBodyTempET);
        fab_save_info=findViewById(R.id.fab_save_Info);

        fab_save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PrescriptionPatientInfo.this,PrescriptionFinal.class);
                try {
                    intent.putExtra("patientName",patientNameET.getText().toString().trim());
                    intent.putExtra("patientAge",patientAgeET.getText().toString().trim());
                    intent.putExtra("patientBloodGroup",patientBloodGroupET.getText().toString().trim());
                    intent.putExtra("patientEmail",patientEmailET.getText().toString().trim());
                    intent.putExtra("patientPhoneET",patientPhoneET.getText().toString().trim());
                    intent.putExtra("patientBloodPressure",patientBloodPressureET.getText().toString().trim());
                    intent.putExtra("patientWeight",patientWeightET.getText().toString().trim());
                    intent.putExtra("patientBodyTemp",patientBodyTempET.getText().toString().trim());
                }
                catch (NullPointerException ignored){

                }


                startActivity(intent);




            }
        });
    }
}
