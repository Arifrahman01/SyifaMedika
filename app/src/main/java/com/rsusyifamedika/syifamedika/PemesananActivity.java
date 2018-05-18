package com.rsusyifamedika.syifamedika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PemesananActivity extends AppCompatActivity {
    public Spinner mSpiner, mSpinerDokter;
    private Button mbtnPemesanan;
    private TextView mtvHasil;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        mtvHasil = (TextView) findViewById(R.id.tvHasil);
        mSpiner = (Spinner) findViewById(R.id.SpinerPoli);
        mSpinerDokter = (Spinner) findViewById(R.id.SpinerDokter);
        mbtnPemesanan = (Button) findViewById(R.id.btnPemesanan);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReferenceFromUrl("https://rsu-syifa-medika.firebaseio.com/DokterSpesialis");


        List<String> item = new ArrayList<>();
        item.add("<--Pilih-->");
        item.add("Poli Jantung");
        item.add("Poli Mata");
        item.add("Poli Mulut");
        item.add("Poli Rehab");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_dropdown_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpiner.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> Polispesial = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Poli = ds.getValue(String.class);
                    Polispesial.add(Poli);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_dropdown_item, Polispesial);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinerDokter.setAdapter(arrayAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mbtnPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtvHasil.setText(mSpiner.getSelectedItem().toString());

            }
        });
    }
}
