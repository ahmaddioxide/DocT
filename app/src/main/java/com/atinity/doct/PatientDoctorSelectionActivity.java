package com.atinity.doct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;

import com.atinity.doct.SignupLogin.PatientLoginActivity;

public class PatientDoctorSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_doctor_selection);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void startPatientLoginAvtivity(View view)
    {
        startActivity(new Intent(getApplicationContext(), PatientLoginActivity.class));

    }
}