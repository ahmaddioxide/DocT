package com.atinity.doct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity_4 extends AppCompatActivity {

    TextView txt_signup, signIn_btn;
    EditText login_email, login_password;

    ImageView back_btn;
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
        back_btn=findViewById(R.id.login_back_button);

        // click listener on Sign In Button
        signIn_btn.setOnClickListener(view -> {

            // taking value from user in edit text and assigning its value below
            // then these email and password values are check by Firebase.signInWithEmailAndPassword
            // if user exist in database, we are successfully login.
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

                //
                //
                //

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Intent i = new Intent(LoginActivity_4.this, HomeActivity_5.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity_4.this, "Error in login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        txt_signup.setOnClickListener(view -> startActivity(new Intent(LoginActivity_4.this, SignupActivity_3.class)));

        back_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),SignupActivity_3.class));
            finish();
        });
    }
}