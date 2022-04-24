package com.atinity.doct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class HomeChatActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chat);

        auth = FirebaseAuth.getInstance();

//        if user didn't login then we send the user to registration form
        if(auth.getCurrentUser() == null) {
            Intent i = new Intent(HomeChatActivity.this, RegistrationOrSignupActivity.class);
            startActivity(i);
        }
    }
}