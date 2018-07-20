package com.rsusyifamedika.syifamedika;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.Daftar.LoginActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView mtvNamaProfil1, mtvNormProfil1, mtvAlamatProfil1, mtvNohandphone1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mtvNamaProfil1 = (TextView) findViewById(R.id.tvNamaProfil1);
        mtvNormProfil1 = (TextView) findViewById(R.id.tvNormProfil1);
        mtvAlamatProfil1 = (TextView) findViewById(R.id.tvalamatProfil1);
        mtvNohandphone1 = (TextView) findViewById(R.id.tvNohpProfil1);

        mRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Nama = dataSnapshot.child("nama").getValue(String.class);
                String Norm = dataSnapshot.child("norm").getValue(String.class);
                String Alamat = dataSnapshot.child("Alamat").getValue(String.class);
                String Nohp = dataSnapshot.child("Nohp").getValue(String.class);

                mtvNamaProfil1.setText(Nama);
                mtvNormProfil1.setText(Norm);
                mtvAlamatProfil1.setText(Alamat);
                mtvNohandphone1.setText(Nohp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findViewById(R.id.btEditProfil1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });


        //Logout Otomatis
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Harap Login Terlebih Dahulu", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


    }
}
