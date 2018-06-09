package com.rsusyifamedika.syifamedika.Ambulan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.Poliklinik.PenyakitDalamActivity;
import com.rsusyifamedika.syifamedika.R;

import java.util.HashMap;
import java.util.Map;

public class PemesananAmbulanActivity extends AppCompatActivity {
    private EditText mNamaAmbulan, mAlamatAmbulan, mKeluhanAmbulan;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_ambulan);
        mNamaAmbulan = findViewById(R.id.edNamaAmbulan);
        mAlamatAmbulan = findViewById(R.id.edAlamatAmbulan);
        mKeluhanAmbulan = findViewById(R.id.edKeluhanAbmulan);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        findViewById(R.id.btDaftarAmbulan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = mNamaAmbulan.getText().toString().trim();
                String alamat = mAlamatAmbulan.getText().toString().trim();
                String keluhan = mKeluhanAmbulan.getText().toString().trim();


                if (TextUtils.isEmpty(nama)) {
                    mNamaAmbulan.setError("Nama Tidak Boleh Kosong");
                    mNamaAmbulan.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(alamat)) {
                    mAlamatAmbulan.setError("Alamat Tidak Boleh Kosong");
                    mAlamatAmbulan.requestFocus();
                    return;
                }
                AlertPemesananAmbulan();


            }
        });
    }

    private void AlertPemesananAmbulan() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Apadak Data Sudah Sesuai");
        alertDialogBuilder
                .setMessage("Nama : "+mNamaAmbulan.getText().toString()+"\nAlamat : "+mAlamatAmbulan.getText().toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Ambulan").child(user_id);
                        String nama = mNamaAmbulan.getText().toString();
                        String alamat = mAlamatAmbulan.getText().toString();
                        String keluhan = mKeluhanAmbulan.getText().toString();

                        Map newPost = new HashMap();
                        newPost.put("nama", nama);
                        newPost.put("alamat", alamat);
                        newPost.put("keluhan", keluhan);
                        current_user_db.setValue(newPost);
                        Toast.makeText(getApplicationContext(),
                                "Pendaftaran Berhasil, Silahkan Tungu Beberapa Saat", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(PemesananAmbulanActivity.this, DrawerActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
