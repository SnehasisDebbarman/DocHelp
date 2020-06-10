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

public class AdapterMedicine extends RecyclerView.Adapter<AdapterMedicine.myHolder>{

    Context context;
    List<ModelMedicine> medicineList;

    public AdapterMedicine(Context context, List<ModelMedicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_medicine,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        final String medicineName =medicineList.get(position).getMedicine_name();
        final String medicineDose =medicineList.get(position).getDose();
        final String medicineHMT=medicineList.get(position).getHowManyTimesET();
        holder.row_medicine_name.setText(medicineName);
        holder.row_medicine_dose.setText(medicineDose);
        holder.row_medicine_how_many_times.setText(medicineHMT);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+medicineName+"\n"+medicineDose+"\n"+medicineHMT,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    class myHolder extends RecyclerView.ViewHolder{
        TextView row_medicine_name,row_medicine_dose,row_medicine_how_many_times;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            row_medicine_name=itemView.findViewById(R.id.row_medicine_name);
            row_medicine_dose=itemView.findViewById(R.id.row_medicine_dose);
            row_medicine_how_many_times=itemView.findViewById(R.id.row_medicine_how_many_times);


        }
    }
}
