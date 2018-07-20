package com.rsusyifamedika.syifamedika;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private EditText medNamaEditProfil, medNormEditProfil,medNohpEditProfil,medAlamatEditProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        medNamaEditProfil = (EditText) findViewById(R.id.edNamaEditProfil);
        medNohpEditProfil = (EditText) findViewById(R.id.edNohpEditProfil);
        medNormEditProfil = (EditText) findViewById(R.id.edNormEditProfil);
        medAlamatEditProfil = (EditText) findViewById(R.id.edAlamatEditProfil);

        myRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Nama = dataSnapshot.child("nama").getValue(String.class);
                String Norm = dataSnapshot.child("norm").getValue(String.class);
                String Nohp = dataSnapshot.child("Nohp").getValue(String.class);
                String Alamat = dataSnapshot.child("Alamat").getValue(String.class);

                medNamaEditProfil.setText(Nama);
                medNormEditProfil.setText(Norm);
                medNohpEditProfil.setText(Nohp);
                medAlamatEditProfil.setText(Alamat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findViewById(R.id.btPerbaruiProfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nama = medNamaEditProfil.getText().toString();
                String Norm = medNormEditProfil.getText().toString();

                if (TextUtils.isEmpty(Nama)){
                    medNamaEditProfil.setError("Nama Tidak Boleh Kosong");
                    medNamaEditProfil.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Norm)){
                    medNormEditProfil.setError("Nomor Rekam Medis Tidak Boleh Kosong");
                    medNormEditProfil.requestFocus();
                    return;
                }
                if (Norm.length()<6){
                    medNormEditProfil.setError("Nomor Rekam Medis Tidak Valid");
                    medNormEditProfil.requestFocus();
                    return;

                }
                if (Norm.length()>6){
                    medNormEditProfil.setError("Nomor Rekam Medis Tidak Valid");
                    medNormEditProfil.requestFocus();
                    return;
                }
                PerbaruiProfil();
            }
        });



    }

    private void PerbaruiProfil() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Profil");
        alertDialogBuilder
                .setMessage("Simpan Perubahan ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                        String Nama = medNamaEditProfil.getText().toString();
                        String Norm = medNormEditProfil.getText().toString();
                        String Nohp = medNohpEditProfil.getText().toString();
                        String Alamat = medAlamatEditProfil.getText().toString();
                        Map newPost = new HashMap();
                        newPost.put("nama", Nama);
                        newPost.put("norm", Norm);
                        newPost.put("Alamat", Alamat);
                        newPost.put("Nohp", Nohp);
                        current_user_db.setValue(newPost);
                        AlertBerhasil();
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

    private void AlertBerhasil() {
        Intent i = new Intent(EditProfileActivity.this,DrawerActivity.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(),
                "Perubahan Data Berhasil", Toast.LENGTH_LONG).show();
    }
}
