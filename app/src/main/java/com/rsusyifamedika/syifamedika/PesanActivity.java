package com.rsusyifamedika.syifamedika;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PesanActivity extends AppCompatActivity {
    private Button mbtPersonal, mbtUmum;
    private CardView mcvPersonal, mcvUmum;
    private TextView mtvPesanPersonal, mtvPesanUmum;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        mtvPesanPersonal = (TextView) findViewById(R.id.tvPesanPersonal);
        mtvPesanUmum = (TextView) findViewById(R.id.tvPesanUmum);
        mbtPersonal = findViewById(R.id.btPersonal);
        mbtUmum = findViewById(R.id.btUmum);

        mcvPersonal = (CardView) findViewById(R.id.cvPersonal);
        mcvUmum = (CardView) findViewById(R.id.cvUmum);
        myRef.child("Pesan").child("Pesan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                mtvPesanUmum.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error:" + databaseError.getCode());
            }
        });

        myRef.child("Users").child(userID).child("Pesan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                mtvPesanPersonal.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Tidak Ada Pesan:" + databaseError.getCode());
            }
        });

        mbtPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtPersonal.setTextColor(getColor(R.color.colorMerah));
                mbtUmum.setTextColor(getColor(R.color.colorHitam));
                mcvPersonal.setVisibility(View.VISIBLE);
                mcvUmum.setVisibility(View.GONE);
            }
        });
        mbtUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtUmum.setTextColor(getColor(R.color.colorMerah));
                mbtPersonal.setTextColor(getColor(R.color.colorHitam));
                mcvUmum.setVisibility(View.VISIBLE);
                mcvPersonal.setVisibility(View.GONE);

            }
        });
    }


}
