package com.rsusyifamedika.syifamedika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();
            uInfo.setNama(ds.child(userID).getValue(UserInformation.class).getNama());
            uInfo.setNorm(ds.child(userID).getValue(UserInformation.class).getNorm());
            uInfo.setAlamat(ds.child(userID).getValue(UserInformation.class).getAlamat());

            mtvNomorRM.setText(uInfo.getNorm());
            mtvNamaLengkap.setText(uInfo.getNama());
            mtvAlamat.setText(uInfo.getAlamat());
        }
    }
}
