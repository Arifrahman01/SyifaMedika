package com.rsusyifamedika.syifamedika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class SidangActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private Button mbtnSimpan;
    private EditText medKodeBuku, medJudul ,medPengarang, edPenerbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidang);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        medJudul = (EditText) findViewById(R.id.edJudul);
        medKodeBuku = (EditText) findViewById(R.id.edKodeBuku);
        medPengarang = (EditText) findViewById(R.id.edPengarang);
        edPenerbit = (EditText) findViewById(R.id.edPenerbit);

        findViewById(R.id.edSimpanBuku).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference current_db = FirebaseDatabase.getInstance().getReference().child("AplikasiBuku").child(userID);
                String kode = medKodeBuku.getText().toString();
                String judul = medJudul.getText().toString();
                String pengarang   = medPengarang.getText().toString();
                String Penerbit = edPenerbit.getText().toString();

                Map newpost = new HashMap();
                newpost.put("kode", kode);
                newpost.put("judul", judul);
                newpost.put("pengarang", pengarang);
                newpost.put("penerbit", Penerbit);
                current_db.setValue(newpost);

            }
        });


    }
}
