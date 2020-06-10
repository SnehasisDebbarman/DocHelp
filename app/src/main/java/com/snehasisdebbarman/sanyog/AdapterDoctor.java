package com.snehasisdebbarman.sanyog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.myHolder> {
    Context context;
    List<ModelDoctor> doctorList;

    public AdapterDoctor(Context context, List<ModelDoctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_doctor,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {

        String doctorName=doctorList.get(position).getName();
        String doctorPhone=doctorList.get(position).getPhone();
        final String doctorEmail=doctorList.get(position).getEmail();

        holder.row_doctor_email.setText(doctorEmail);
        holder.row_doctor_name.setText(doctorName);
        holder.row_doctor_phone.setText(doctorPhone);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+doctorEmail,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    class myHolder extends RecyclerView.ViewHolder{

        TextView row_doctor_name,row_doctor_phone,row_doctor_email;

        public myHolder(@NonNull View itemView) {
            super(itemView);

            row_doctor_name =itemView.findViewById(R.id.row_doctor_name);
            row_doctor_phone =itemView.findViewById(R.id.row_doctor_phone);
            row_doctor_email =itemView.findViewById(R.id.row_doctor_email);
        }
    }
}
