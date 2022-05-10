package com.atinity.doct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity_3 extends AppCompatActivity {

    TextView txt_sign_in, btn_signUp;
    CircleImageView profile_image;
    EditText reg_name, reg_email, reg_pass, reg_cPass;
    ImageView back_btn;

    FirebaseAuth auth;
    Uri imageUri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseDatabase database;
    FirebaseStorage storage;

    String imageURI; // store the uri/url value of image that we get from firebase storage when we store that image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt_sign_in = findViewById(R.id.txt_signin);
        btn_signUp = findViewById(R.id.btn_signUp);

        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_pass = findViewById(R.id.reg_pass);
        reg_cPass = findViewById(R.id.reg_cPass);
        profile_image = findViewById(R.id.profile_image);

        back_btn = findViewById(R.id.login_back_button);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        back_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),P_D_Select_Activity_2.class));
            finish();
        });

        btn_signUp.setOnClickListener(view -> {

            String name = reg_name.getText().toString();
            String email = reg_email.getText().toString();
            String password = reg_pass.getText().toString();
            String cPassword = reg_cPass.getText().toString();
            String status = "Hey there You need help!";


            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(cPassword)) {
                Toast.makeText(SignupActivity_3.this, "Enter valid Data", Toast.LENGTH_SHORT).show();
            } else if(!email.matches(emailPattern)) {
                reg_email.setError("Invalid Email");
                Toast.makeText(SignupActivity_3.this, "Invalid Email", Toast.LENGTH_SHORT).show();
            } else if(password.length() < 6 || !password.equals(cPassword)) {
                reg_pass.setError("Invalid Password");
                Toast.makeText(SignupActivity_3.this, "Password is too short or\n Password not Matched", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {

                    // storing data in database if, user input data is valid
                        // here after child("user"), we make anOther child {(child(auth.getUid()))}, for authentication id
                        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(auth.getUid()));
                        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

                        // this code is to check, whether the user select image from the gallery or not

                        if(imageUri != null) {
                            // and if user selected the image, then we store the image into StorageReference (Firebase Storage)
                            storageReference.putFile(imageUri).addOnCompleteListener(task1 -> {

                                if(task1.isSuccessful()) {
                                    // if image is stored successfully, then we need the URL of image that we have stored
                                    // and added addOnSuccessListener here, gives us the URL value
                                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                                        imageURI = uri.toString();
                                        Users users = new Users(name, email, imageURI, auth.getUid(), status);
                                        // now we add the users into reference (realtime database) after checking all the inputs and converting them
                                        reference.setValue(users).addOnCompleteListener(task11 -> {

                                            if(task11.isSuccessful()) {
                                                Intent i = new Intent(SignupActivity_3.this, HomeActivity_5.class);
                                                startActivity(i);
                                                finish();
                                            } else { Toast.makeText(SignupActivity_3.this, "Error in Creating User", Toast.LENGTH_SHORT).show(); }
                                        });
                                    });
                                }
                            });
                        } else {
                            String status1 = "Hey there You need help!";
                            imageURI = "https://firebasestorage.googleapis.com/v0/b/doct-8e8d5.appspot.com/o/female_doctor_avatar.png?alt=media&token=40d3bf94-5ea7-4ad0-b782-26e39f7f2c5e";
                            Users users = new Users(name, email, imageURI, auth.getUid(), status1);
                            reference.setValue(users).addOnCompleteListener(task12 -> {
                                if(task12.isSuccessful()) {

//                                        Log.d(name, "This is my name");
//                                        Log.d(email, "This is my email");
//                                        Log.d(imageURI, "This is my imageURI");
//                                        Log.d(auth.getUid(), "This is my getAuth");
//                                        Log.d(status, "This is my status");

                                    Intent i = new Intent(SignupActivity_3.this, HomeActivity_5.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Toast.makeText(SignupActivity_3.this, "Error in Creating User", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } else {

                        Toast.makeText(SignupActivity_3.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // to open gallery of phone, and select image
        profile_image.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
        });

        txt_sign_in.setOnClickListener(view -> {
            Intent i = new Intent(SignupActivity_3.this, LoginActivity_4.class);
            startActivity(i);
        });
    }

    // after selecting image, code get into this function, and check the request code
    // and any value (image) is present or not.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10) {
            if(data != null) {
                imageUri = data.getData();
                profile_image.setImageURI(imageUri);
            }
        }
    }
}

