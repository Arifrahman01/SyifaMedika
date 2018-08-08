package com.rsusyifamedika.syifamedika;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.Daftar.DaftarRMActivity;

public class KartuActivity extends AppCompatActivity {
    private TextView mtvNomorRM, mtvNamaLengkap, mtvAlamat;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu);


        mtvNomorRM = (TextView) findViewById(R.id.tvNomorRM);
        mtvNamaLengkap = (TextView) findViewById(R.id.tvNamaLengkap);
        mtvAlamat = (TextView) findViewById(R.id.tvAlamat);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

      myRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              String Nama = dataSnapshot.child("nama").getValue(String.class);
              String Alamat = dataSnapshot.child("Alamat").getValue(String.class);
              String Norm = dataSnapshot.child("norm").getValue(String.class);

              mtvNomorRM.setText(Norm);
              mtvNamaLengkap.setText(Nama);
              mtvAlamat.setText(Alamat);
          }
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
}
