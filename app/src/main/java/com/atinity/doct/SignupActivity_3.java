package com.atinity.doct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity_3 extends AppCompatActivity {

    TextView txt_sign_in, btn_signUp;
    CircleImageView profile_image;
    EditText reg_name, reg_email, reg_pass, reg_cPass;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Uri imageUri;

    FirebaseDatabase database;
    FirebaseStorage storage;

    String imageURI;
//    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt_sign_in = findViewById(R.id.txt_signin);
        btn_signUp = findViewById(R.id.btn_signUp);
        profile_image = findViewById(R.id.profile_image);
        reg_name = findViewById(R.id.reg_name);
        reg_pass = findViewById(R.id.reg_pass);
        reg_email = findViewById(R.id.reg_email);
        reg_cPass = findViewById(R.id.reg_cPass);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);


        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                progressDialog.show();

                String name = reg_name.getText().toString();
                String email = reg_email.getText().toString();
                String password = reg_pass.getText().toString();
                String cPassword = reg_cPass.getText().toString();


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
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {

                                Toast.makeText(SignupActivity_3.this, "User is created", Toast.LENGTH_SHORT).show();

                                DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
                                StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

                                if(imageUri != null) {
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageURI = uri.toString();
                                                        Users users = new Users(auth.getUid(), email, name, imageURI);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    Intent i = new Intent(SignupActivity_3.this, HomeActivity_5.class);
                                                                    startActivity(i);
                                                                } else {
                                                                    Toast.makeText(SignupActivity_3.this, "Error in Creating User", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    imageURI = "https://firebasestorage.googleapis.com/v0/b/doct-8e8d5.appspot.com/o/female_doctor_avatar.png?alt=media&token=40d3bf94-5ea7-4ad0-b782-26e39f7f2c5e";
                                    Users users = new Users(auth.getUid(), email, name, imageURI);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Intent i = new Intent(SignupActivity_3.this, HomeActivity_5.class);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(SignupActivity_3.this, "Error in Creating User", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            } else {

                                Toast.makeText(SignupActivity_3.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });

        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity_3.this, LoginActivity_4.class);
                startActivity(i);
            }
        });
    }

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