package com.atinity.doct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ChatWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_welcome);

        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            Intent i = new Intent(ChatWelcomeActivity.this, HomeChatActivity.class);
            startActivity(i);
            finish();
        }, 5000);
    }
}