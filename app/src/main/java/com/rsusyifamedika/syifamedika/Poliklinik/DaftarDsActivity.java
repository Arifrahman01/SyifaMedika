package com.rsusyifamedika.syifamedika.Poliklinik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
                    medNoDaftar.setError("Nomor Rekam Medis Tidak Boleh Kosong");
                    medNoDaftar.requestFocus();
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
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("NoPoli").child(user_id);
        String nama = medNamaDaftar.getText().toString();
        String nomor = medNoDaftar.getText().toString();

        Map newPost = new HashMap();
        newPost.put("Nama", nama);
        newPost.put("NoRM", nomor);

        current_user_db.setValue(newPost);

        Intent i = new Intent(DaftarDsActivity.this, PemesananActivity.class);
        startActivity(i);

    }

    public void DaftarNomorRM(View view) {
        Intent i = new Intent(DaftarDsActivity.this, DaftarRMActivity.class);
        startActivity(i);
    }
}
