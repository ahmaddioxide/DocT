package com.atinity.doct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    static String SPName="PatOrDoc";
    static String keyName="P_or_D";
    SharedPreferences SP;
    static String P_or_D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SP=getSharedPreferences(SPName,MODE_PRIVATE);
        P_or_D=SP.getString(keyName,null);
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
                    if (P_or_D.equals("patient")) {
                        Log.d("Result of P_O_D", P_or_D);
                        if (users.getDomain().equals("doctor")) {
                            usersArrayList.add(users);
                        }
                    }
                    if(P_or_D.equals("doctor"))
                    {
                        Log.d("Result of P_O_D", P_or_D);

                        if (users.getDomain().equals("patient")) {
                            usersArrayList.add(users);
                        }
                    }
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
        adapter = new UserAdapter(HomeActivity_5.this, usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);


        imgSetting = findViewById(R.id.img_setting);

        imglogOut.setOnClickListener(view -> {
            Dialog dialog = new Dialog(HomeActivity_5.this, R.style.Dialoge);

            dialog.setContentView(R.layout.dialog_layout);

            TextView yesBtn, noBtn;

            yesBtn = dialog.findViewById(R.id.yesBtn);
            noBtn = dialog.findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(view1 -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity_5.this, P_D_Select_Activity_2.class));
            });

            noBtn.setOnClickListener(view12 -> dialog.dismiss());
            dialog.show();

        });


        imgSetting.setOnClickListener(view ->
                startActivity(new Intent(HomeActivity_5.this, SettingActivity.class)));
    }

    public  void goTOMaps(View view)
    {
        startActivity(new Intent(this.getApplicationContext(),MapsActivity_6.class));
    }
}