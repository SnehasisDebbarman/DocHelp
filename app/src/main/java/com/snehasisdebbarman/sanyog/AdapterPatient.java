package com.snehasisdebbarman.sanyog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterPatient  extends RecyclerView.Adapter<AdapterPatient.Myholder>{

    Context context;
    List<ModelPatient> patientList;

    //constructor

    public AdapterPatient(Context context, List<ModelPatient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_patient,parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myholder holder, int position) {

        final String hisUID = patientList.get(position).getId();
        String patientName=patientList.get(position).getPatientName();
        String patientPhone=patientList.get(position).getPatientPhoneET();
        final String patientEmail=patientList.get(position).getPatientEmail();
        String patientAge =patientList.get(position).getPatientAge();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = DateFormat.format("dd-MM-yyyy", Long.parseLong(String.valueOf(System.currentTimeMillis()))).toString();


            holder.row_patient_name.setText(patientName);
            holder.row_patient_phone.setText(patientPhone);
            holder.row_patient_email.setText(patientEmail);
            holder.row_patient_age.setText(patientAge);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, PrescriptionViewForDoctor.class);
                    intent.putExtra("PatientUid",hisUID);
                    context.startActivity(intent);
                    // Toast.makeText(context,""+patientEmail,Toast.LENGTH_LONG).show();
                }
            });





    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class Myholder extends RecyclerView.ViewHolder{

        TextView row_patient_name,row_patient_phone,row_patient_email,row_patient_age;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            //init view
            row_patient_name = itemView.findViewById(R.id.row_patient_name);
            row_patient_email = itemView.findViewById(R.id.row_patient_email);
            row_patient_phone = itemView.findViewById(R.id.row_patient_phone);
            row_patient_age=itemView.findViewById(R.id.row_patient_age);

        }
    }
}
