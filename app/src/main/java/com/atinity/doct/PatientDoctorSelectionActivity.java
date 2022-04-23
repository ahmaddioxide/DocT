package com.atinity.doct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientDoctorSelectionActivity extends AppCompatActivity {
    private Button selctDoctor,selectPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_doctor_selection);
        selctDoctor=findViewById(R.id.buttonSelectDoctor);
        selectPatient=findViewById(R.id.buttonSelectPatient);

    }


    public void selectDocotor(View view)
    {
        startActivity(new Intent(getApplicationContext(),DoctorSignInActivity.class));
    }
}