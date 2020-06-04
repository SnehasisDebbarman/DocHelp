package com.snehasisdebbarman.sanyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class PrescriptionFinal extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1;
    StorageReference storageReference;

    String DoctorName,DoctorEmail,DoctorPhone,DoctorQualification,DoctorPicture,DoctorLocation;

    String patientName,patientAge,patientBloodGroup,patientEmail,patientPhone,patientBloodPressure,patientWeight,patientBodyTemp;
    Button add,add2,add3;
    LinearLayout medicineLL2,medicineLL3,medicineLL4;

    EditText medicineNameET,medicineNameET2,medicineNameET3,medicineNameET4;
    EditText doseET,doseET2,doseET3,doseET4;
    EditText howManyTimesET,howManyTimesET2,howManyTimesET3,howManyTimesET4;
    EditText whenET,whenET2,whenET3,whenET4;
    EditText splInstruction,splInstruction2,splInstruction3,splInstruction4;

    FloatingActionButton fab_createPDF;

    int pageWidth=2100;
    int pageHight= 2900;

    int addClicked=0;
    int addClicked2=0;
    int addClicked3=0;

    static final int PERMISSION_REQUEST_CODE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_final);

        firebaseAuth = FirebaseAuth.getInstance();
        user =firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 =firebaseDatabase.getReference("Doctors/");
        storageReference=getInstance().getReference();



        Intent i = getIntent();
        patientName=i.getStringExtra("patientName");
        patientAge=i.getStringExtra("patientAge");
        patientBloodGroup=i.getStringExtra("patientBloodGroup");
        patientEmail=i.getStringExtra("patientEmail");
        patientPhone=i.getStringExtra("patientPhone");
        patientBloodPressure=i.getStringExtra("patientBloodPressure");
        patientWeight=i.getStringExtra("patientWeight");
        patientBodyTemp=i.getStringExtra("patientBodyTemp");

        add=findViewById(R.id.add);
        medicineLL2=findViewById(R.id.medicineLL2);
        add2=findViewById(R.id.add2);
        medicineLL3=findViewById(R.id.medicineLL3);

        add3=findViewById(R.id.add3);
        medicineLL4=findViewById(R.id.medicineLL4);

        medicineNameET=findViewById(R.id.medicineNameET);
        medicineNameET2=findViewById(R.id.medicineNameET2);
        medicineNameET3=findViewById(R.id.medicineNameET3);
        medicineNameET4=findViewById(R.id.medicineNameET4);

        doseET=findViewById(R.id.doseET);
        doseET2=findViewById(R.id.doseET2);
        doseET3=findViewById(R.id.doseET3);
        doseET4=findViewById(R.id.doseET4);

        howManyTimesET=findViewById(R.id.howManyTimesET);
        howManyTimesET2=findViewById(R.id.howManyTimesET2);
        howManyTimesET3=findViewById(R.id.howManyTimesET3);
        howManyTimesET4=findViewById(R.id.howManyTimesET4);

        whenET=findViewById(R.id.whenET);
        whenET2=findViewById(R.id.whenET2);
        whenET3=findViewById(R.id.whenET3);
        whenET4=findViewById(R.id.whenET4);

        splInstruction=findViewById(R.id.splInstructionET);
        splInstruction2=findViewById(R.id.splInstructionET2);
        splInstruction3=findViewById(R.id.splInstructionET3);
        splInstruction4=findViewById(R.id.splInstructionET4);

        fab_createPDF=findViewById(R.id.fab_createPDF);



        Query query =databaseReference1.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String name =""+ds.child("name").getValue();
                    String email =""+ds.child("email").getValue();
                    String image =""+ds.child("image").getValue();
                    String phone =""+ds.child("Phone").getValue();
                    String qualification =""+ds.child("qualification").getValue();
                    String location =""+ds.child("location").getValue();


                    DoctorName=name;
                    DoctorEmail=email;
                    DoctorQualification=qualification;
                    DoctorPicture=image;
                    DoctorPhone=phone;
                    DoctorLocation=location;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineLL2.setVisibility(View.VISIBLE);
                addClicked=1;

            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineLL3.setVisibility(View.VISIBLE);
                addClicked2=1;

            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineLL4.setVisibility(View.VISIBLE);
                addClicked3=1;

            }
        });

        fab_createPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());


                PdfDocument document = new PdfDocument();
                // crate a page description
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHight, 1).create();
                // start a page
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                Paint RectPaint = new Paint();
                Paint paintText = new Paint();
                Paint paintName = new Paint();

                //header

                RectPaint.setColor(ContextCompat.getColor(PrescriptionFinal.this,R.color.colorPrimaryDark));
                canvas.drawRect(0,0,pageWidth,500,RectPaint);
                //name
                paintName.setColor(Color.WHITE);
                paintName.setTextSize(75);
                canvas.drawText("Dr."+DoctorName, 100, 100, paintName);
                //specialist
                paintText.setColor(Color.WHITE);
                paintText.setTextSize(60);
                canvas.drawText("Speciality : "+ DoctorQualification,100,180,paintText);
                //phone
                paintText.setColor(Color.WHITE);
                paintText.setTextSize(60);
                canvas.drawText("Phone No : "+ DoctorPhone ,100,260,paintText);
                //email
                paintText.setColor(Color.WHITE);
                paintText.setTextSize(60);
                canvas.drawText("Email : "+DoctorEmail,100,340,paintText);
                //medical id
                paintText.setColor(Color.WHITE);
                paintText.setTextSize(60);
                canvas.drawText("Medical Id : "+"123783290",100,420,paintText);

                //patient info

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Patient Name : "+ patientName.toString().trim(), 100, 600, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Age : " + patientAge, (pageWidth/3)*2, 600, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Blood Group : "+patientBloodGroup, 100, 700, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Weight : "+patientWeight, (pageWidth/3)*2, 700, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Blood Pressure : "+patientBloodPressure, 100, 800, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Phone No : "+patientPhone, (pageWidth/3)*2, 800, paintName);

                paintName.setColor(Color.GRAY);
                canvas.drawLine(0,900,pageWidth,900,paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(60);
                canvas.drawText("Date : "+formattedDate, (pageWidth/10)*6, 970, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(200);
                canvas.drawText("Rx", 100, 1150, paintName);

                paintName.setColor(Color.LTGRAY);
                canvas.drawLine(100,1250,pageWidth-100,1250,paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(50);
                canvas.drawText("Medicine Name", 100, 1320, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(50);
                canvas.drawText("Dose", ((pageWidth-200)/20)*6, 1320, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(50);
                canvas.drawText("How many times", ((pageWidth-200)/20)*9, 1320, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(50);
                canvas.drawText("When", ((pageWidth-200)/20)*14, 1320, paintName);

                paintName.setColor(Color.BLACK);
                paintName.setTextSize(50);
                canvas.drawText("Spl Instructions", ((pageWidth-200)/20)*17, 1320, paintName);

                paintName.setColor(Color.LTGRAY);
                canvas.drawLine(100,1380,pageWidth-100,1380,paintName);

                //medicine 1

                paintName.setColor(Color.GRAY);
                paintName.setTextSize(45);
                canvas.drawText(medicineNameET.getText().toString().trim()+"",100, 1450, paintName);

                paintName.setColor(Color.GRAY);
                paintName.setTextSize(45);
                canvas.drawText(doseET.getText().toString().trim()+"", ((pageWidth-200)/20)*6, 1450, paintName);

                paintName.setColor(Color.GRAY);
                paintName.setTextSize(45);
                canvas.drawText(howManyTimesET.getText().toString().trim()+"", ((pageWidth-200)/20)*9, 1450, paintName);

                paintName.setColor(Color.GRAY);
                paintName.setTextSize(45);
                canvas.drawText(whenET.getText().toString().trim()+"", ((pageWidth-200)/20)*14, 1450, paintName);

                paintName.setColor(Color.GRAY);
                paintName.setTextSize(45);
                canvas.drawText(splInstruction.getText().toString().trim()+"", ((pageWidth-200)/20)*17, 1450, paintName);

                paintName.setColor(Color.LTGRAY);
                canvas.drawLine(100,1510,pageWidth-100,1510,paintName);

                ///end of medicine 1

               if(addClicked==1){
                   //medicine 2
                   paintName.setColor(Color.GRAY);
                   paintName.setTextSize(45);
                   canvas.drawText(medicineNameET2.getText().toString().trim()+"", 100, 1560, paintName);

                   paintName.setColor(Color.GRAY);
                   paintName.setTextSize(45);
                   canvas.drawText(doseET2.getText().toString().trim()+"", ((pageWidth-200)/20)*6, 1560, paintName);

                   paintName.setColor(Color.GRAY);
                   paintName.setTextSize(45);
                   canvas.drawText(howManyTimesET2.getText().toString().trim()+"", ((pageWidth-200)/20)*9, 1560, paintName);

                   paintName.setColor(Color.GRAY);
                   paintName.setTextSize(45);
                   canvas.drawText(whenET2.getText().toString().trim()+"", ((pageWidth-200)/20)*14, 1560, paintName);

                   paintName.setColor(Color.GRAY);
                   paintName.setTextSize(45);
                   canvas.drawText(splInstruction2.getText().toString().trim()+"", ((pageWidth-200)/20)*17, 1560, paintName);

                   paintName.setColor(Color.LTGRAY);
                   canvas.drawLine(100,1610,pageWidth-100,1610,paintName);

                   ///end of medicine 2

               }


                // finish the page
                document.finishPage(page);
// draw text on the graphics object of the page
      /*  // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paintText = new Paint();
        paintText.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 100, paintText);
        document.finishPage(page);*/
                // write the document content

                if(checkPermission()){
                    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/my_pdf/";
                    File file = new File(directory_path);

                    boolean isDirectoryCreated=file.exists();
                    if (!isDirectoryCreated) {
                        isDirectoryCreated= file.mkdir();
                    }

                    if (isDirectoryCreated){
                        String targetPdf = directory_path+"test.pdf";
                        File filePath = new File(targetPdf);
                        try {
                            document.writeTo(new FileOutputStream(filePath));
                            Toast.makeText(PrescriptionFinal.this, "Done:"+filePath.toString(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.e("main", "error "+e.toString());
                            Toast.makeText(PrescriptionFinal.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
                        }
                        // close the document
                        document.close();

                    }

                }
                else {
                    requestPermission();
                }



            }


        });

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(PrescriptionFinal.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(PrescriptionFinal.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(PrescriptionFinal.this, "Write External Storage permission allows us to create files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(PrescriptionFinal.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }


}
