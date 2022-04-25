package com.atinity.doct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity_4 extends AppCompatActivity {

    TextView txt_signup;
    EditText login_email, login_password;
    TextView signIn_btn;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();

        txt_signup = findViewById(R.id.txt_signup);
        signIn_btn = findViewById(R.id.signin_btn);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity_4.this, "Enter valid Data", Toast.LENGTH_SHORT).show();
                } else if(!email.matches(emailPattern)) {
                    login_email.setError("Invalid Email");
                    Toast.makeText(LoginActivity_4.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                } else if(password.length() < 6) {
                    login_password.setError("Invalid Password");
                    Toast.makeText(LoginActivity_4.this, "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent i = new Intent(LoginActivity_4.this, HomeActivity_5.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(LoginActivity_4.this, "Error in login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        txt_signup.setOnClickListener(view -> startActivity(new Intent(LoginActivity_4.this, SignupActivity_3.class)));

    }
}