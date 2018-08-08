package com.rsusyifamedika.syifamedika;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Sidang2Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private TextView mtvKodeBuku, mtvJudulBuku, mtvPengarang, mtvPenerbit;
    private Button btTambahDataBuku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidang2);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mtvJudulBuku = (TextView) findViewById(R.id.tvJudulBuku);
        mtvKodeBuku = (TextView) findViewById(R.id.tvKodeBuku);
        mtvPenerbit = (TextView) findViewById(R.id.tvPenerbit);
        mtvPengarang = (TextView) findViewById(R.id.tvPengarang);

        btTambahDataBuku = (Button) findViewById(R.id.btTambahBuku) ;

        findViewById(R.id.btHapusData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertHapus();
            }
        });

                myRef.child("AplikasiBuku").child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String kode = dataSnapshot.child("kode").getValue(String.class);
                        String judul = dataSnapshot.child("judul").getValue(String.class);
                        String pengarang = dataSnapshot.child("pengarang").getValue(String.class);
                        String penerbit = dataSnapshot.child("penerbit").getValue(String.class);

                        mtvKodeBuku.setText("Kode Buku     :   " + kode);
                        mtvJudulBuku.setText("Judul Buku     :   " + judul);
                        mtvPenerbit.setText("Penerbit       :   " + penerbit);
                        mtvPengarang.setText("Pengarang     :   " + pengarang);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        btTambahDataBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sidang2Activity.this, SidangActivity.class);
                startActivity(i);

            }
        });
    }

    private void AlertHapus() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Hapus Data");
        alertDialogBuilder
                .setMessage("AAnda Yakin Ingi Menghapus Data ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference current_db = FirebaseDatabase.getInstance().getReference().child("AplikasiBuku").child(userID);
                        String kode = "";
                        String judul = "";
                        String pengarang   = "";
                        String Penerbit = "";

                        Map newpost = new HashMap();
                        newpost.put("kode", kode);
                        newpost.put("judul", judul);
                        newpost.put("pengarang", pengarang);
                        newpost.put("penerbit", Penerbit);
                        current_db.setValue(newpost);
                        AlertTerhapus();


                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog  = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void AlertTerhapus() {
        Toast.makeText(getApplicationContext(),
                "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
    }
}
