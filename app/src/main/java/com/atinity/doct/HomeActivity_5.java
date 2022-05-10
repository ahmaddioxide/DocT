package com.atinity.doct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity_5 extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdapter adapter;
    ArrayList<Users> usersArrayList;
    ImageView imglogOut;
    ImageView imgSetting;

    // taking data from firebase
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        usersArrayList = new ArrayList<>();

        DatabaseReference reference  = database.getReference().child("user");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    usersArrayList.add(users);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        imglogOut = findViewById(R.id.img_logOut);

        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainUserRecyclerView.setAdapter(adapter);
        adapter = new UserAdapter(HomeActivity_5.this, usersArrayList);

        // 9
        imgSetting = findViewById(R.id.img_setting);

        imglogOut.setOnClickListener(view -> {
            Dialog dialog = new Dialog(HomeActivity_5.this, R.style.Dialoge);

            dialog.setContentView(R.layout.dialog_layout);

            TextView yesBtn, noBtn;

            yesBtn = dialog.findViewById(R.id.yesBtn);
            noBtn = dialog.findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(view1 -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity_5.this, SignupActivity_3.class));
            });

            noBtn.setOnClickListener(view12 -> dialog.dismiss());


            dialog.show();

        });


        // 9
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity_5.this, SettingActivity.class));
            }
        });

    }
}