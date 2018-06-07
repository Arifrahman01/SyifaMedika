package com.rsusyifamedika.syifamedika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class StatusActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private TextView mStatusNama, mStatusPoli, mStatusDokter, mStatusWaktu,  mStatusStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mStatusNama = findViewById(R.id.StatusNama);
        mStatusPoli = findViewById(R.id.StatusPoli);
        mStatusDokter = findViewById(R.id.StatusDokter);
        mStatusWaktu = findViewById(R.id.StatusWaktu);
        mStatusStatus = findViewById(R.id.StatusStatus);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Belum Ada Pemesanan Poli:" + databaseError.getCode());

            }
        });
    }

    private void showdata(DataSnapshot dataSnapshot) {
       for (DataSnapshot ds :dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();
            uInfo.setNama(ds.child(userID).getValue(UserInformation.class).getNama());
//            uInfo.setPoliklinik(ds.child(userID).getValue(UserInformation.class).getPoliklinik());
//            uInfo.setDokterSpesialis(ds.child(userID).getValue(UserInformation.class).getDokterSpesialis());
//            uInfo.setWaktu(ds.child(userID).getValue(UserInformation.class).getwaktu());
//            uInfo.setStatus(ds.child(userID).getValue(UserInformation.class).getSatus());

           mStatusNama.setText(uInfo.getNama());
//            mStatusPoli.setText(":  " + uInfo.getPoliklinik());
//            mStatusDokter.setText(":  " + uInfo.getDokterSpesialis());
//            mStatusWaktu.setText(":  " + uInfo.getwaktu());
//            mStatusStatus.setText(":  " + uInfo.getSatus());
        }
    }
}
