package com.rsusyifamedika.syifamedika;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.Poliklinik.DaftarDsActivity;

public class StatusActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private TextView mStatusNama, mStatusPoli, mStatusDokter, mStatusWaktu,  mStatusStatus, mNormStatus ;
    private EditText btdaftarPoliStatus, mStatusNorm;
    private CardView mcvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        findViewById(R.id.btdaftarPoliStatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StatusActivity.this, DaftarDsActivity.class);
                startActivity(i);
            }
        });
        mcvStatus = (CardView) findViewById(R.id.cvStatus);

        mNormStatus = (TextView) findViewById(R.id.tvNomorRMStatus);
        mStatusPoli = (TextView) findViewById(R.id.tvPoliStatus);
        mStatusDokter =(TextView) findViewById(R.id.tvDokterStatus);
        mStatusWaktu = (TextView) findViewById(R.id.tvJadwalStatus);
        mStatusStatus = (TextView) findViewById(R.id.tvStatusStatus);
        mStatusNama = (TextView) findViewById(R.id.tvNamaStatus);

        mStatusNorm = (EditText) findViewById(R.id.StatusNorm);
        String PoliStatus1 = mStatusNorm.getText().toString().trim();
        if (PoliStatus1.isEmpty()){
            mcvStatus.setVisibility(View.VISIBLE);
        }
        myRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Nama = dataSnapshot.child("nama").getValue(String.class);
                String Norm = dataSnapshot.child("norm").getValue(String.class);
                String Poli = dataSnapshot.child("Poli").getValue(String.class);
                String Dokter = dataSnapshot.child("Dokter").getValue(String.class);
                String Waktu = dataSnapshot.child("Waktu").getValue(String.class);
                String Status = dataSnapshot.child("status").getValue(String.class);
                mStatusNama.setText("Nama       :       "+Nama);
                mNormStatus.setText("Nomor Rekam Medis  :       "+Norm);
                mStatusPoli.setText("Poliklinik     :       "+Poli);
                mStatusDokter.setText("Dokter       :       "+Dokter);
                mStatusWaktu.setText("Jadwal        :       "+Waktu);
                mStatusStatus.setText("Status       :       "+Status);
                mStatusNorm.setText(Poli);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
