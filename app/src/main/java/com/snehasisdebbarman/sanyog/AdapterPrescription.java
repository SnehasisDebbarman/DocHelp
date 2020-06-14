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

import java.util.List;

public class AdapterPrescription extends  RecyclerView.Adapter<AdapterPrescription.myHolder>{
    Context context;
    List<ModelPrescription> prescriptionList;

    public AdapterPrescription(Context context, List<ModelPrescription> prescriptionList) {
        this.context = context;
        this.prescriptionList = prescriptionList;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_prescription,parent,false);
        return new myHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        final  String PatientUid =prescriptionList.get(position).getPatient_uid();
        final  String DoctorUid =prescriptionList.get(position).getDoctor_uid();
        final  String PrescriptionID=prescriptionList.get(position).getPrescription_id();
        String pNamePres =prescriptionList.get(position).getPatient_name();
        final String pEmailPres=prescriptionList.get(position).getPatient_email();
        String timeStampPres=prescriptionList.get(position).getTimestamp();

        String date = DateFormat.format("EEE, d MMM yyyy HH:mm:ss", Long.parseLong(timeStampPres)).toString();
        int pos=position+1;

        holder.rowPresPatientName.setText(pNamePres);
        holder.prescription_count.setText("Prescription No:"+pos);
        holder.rowPresPatientEmail.setText(pEmailPres);
        holder.timeStamp.setText("Created at: "+date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, PatientMedicineListForDoctor.class);
                intent.putExtra("PatientUid",PatientUid);
                intent.putExtra("DoctorUid",DoctorUid);
                intent.putExtra("PrescriptionID",PrescriptionID);
                context.startActivity(intent);
                Toast.makeText(context,""+pEmailPres,Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    class myHolder extends RecyclerView.ViewHolder{

        TextView rowPresPatientName,rowPresPatientEmail,timeStamp,prescription_count;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            rowPresPatientName=itemView.findViewById(R.id.rowPresPatientName);
            rowPresPatientEmail=itemView.findViewById(R.id.rowPresPatientEmail);
            timeStamp=itemView.findViewById(R.id.timeStamp);
            prescription_count=itemView.findViewById(R.id.prescription_count);
        }
    }
}
