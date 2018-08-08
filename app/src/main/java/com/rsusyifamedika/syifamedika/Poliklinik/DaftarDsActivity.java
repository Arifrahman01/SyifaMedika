package com.rsusyifamedika.syifamedika.Poliklinik;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.Daftar.DaftarPoliklinikActivity;
import com.rsusyifamedika.syifamedika.Daftar.DaftarRMActivity;
import com.rsusyifamedika.syifamedika.R;

import java.util.HashMap;
import java.util.Map;

public class DaftarDsActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private EditText medNamaDaftar, medNoDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ds);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        medNamaDaftar = findViewById(R.id.edNamaDaftar);
        medNoDaftar = findViewById(R.id.edNoDaftar);

        myRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("nama").getValue(String.class);
                String Norm = dataSnapshot.child("norm").getValue(String.class);
                medNamaDaftar.setText(value);
                medNoDaftar.setText(Norm);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        findViewById(R.id.btDaftarr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = medNamaDaftar.getText().toString();
                String Nomor = medNoDaftar.getText().toString();
                if (TextUtils.isEmpty(nama)){
                    medNamaDaftar.setError("Nama Tidak Boleh Kosong");
                    medNamaDaftar.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Nomor)) {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Daftar Rekam Medis Terlebih Dahulu", Toast.LENGTH_LONG).show();
//                    medNoDaftar.setError("Nomor Rekam Medis Tidak Boleh Kosong");
//                    medNoDaftar.requestFocus();
                    return;
                }
                if (Nomor.length()>6){
                    medNoDaftar.setError("Nomor Rekam Medis Tidak Valid");
                    medNoDaftar.requestFocus();
                    return;
                }
                if (Nomor.length()<6){
                    medNoDaftar.setError("Nomor Rekam Medis Tidak Valid");
                    medNoDaftar.requestFocus();
                    return;
                }
                SimpanPendaftar();
            }
        });
    }
    private void SimpanPendaftar() {
        Intent i = new Intent(DaftarDsActivity.this, DaftarPoliklinikActivity.class);
        startActivity(i);
    }
    public void DaftarNomorRM(View view) {
        String Norm = medNoDaftar.getText().toString();
        if (Norm.length() == 0){
            Intent i = new Intent(DaftarDsActivity.this, DaftarRMActivity.class);
            startActivity(i);
            return;
        }
        Toast.makeText(getApplicationContext(),
                "Akun Anda Sudah Terdaftar", Toast.LENGTH_LONG).show();

    }
}
