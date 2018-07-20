package com.rsusyifamedika.syifamedika;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.session.MediaSession;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class LengkapiDataActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private Button mbtnSimpan;
    private EditText metNAma, metNomor, metAlamat, metTempatLahir, metTglLahir, medNomorh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lengkapi_data);

        mbtnSimpan = (Button) findViewById(R.id.btnSimpann);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        metNAma = (EditText) findViewById(R.id.etNama) ;
        metNomor = (EditText) findViewById(R.id.edNomor) ;
        metAlamat = (EditText) findViewById(R.id.edAlamat) ;
        medNomorh = (EditText) findViewById(R.id.edNomorhp1);

        userID = user.getUid();
        mbtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = metNAma.getText().toString();
                String norm = metNomor.getText().toString();
                String alamat = metAlamat.getText().toString();
                String Nomorph = medNomorh.getText().toString();
                if (nama.isEmpty()){
                    metNAma.setError("Nama Tidak Boleh Kosong");
                    metNAma.requestFocus();
                    return;
                }
                if (alamat.isEmpty()){
                    metAlamat.setError("Alamat Tidak Boleh Kosong");
                    metAlamat.requestFocus();
                    return;
                }
                if (Nomorph.isEmpty()){
                    medNomorh.setError("Nomor Hanphone Tidak Boleh Kosong");
                    medNomorh.requestFocus();
                    return;
                }
                if (medNomorh.length()<10){
                    medNomorh.setError("Nomor Hanphone Tidak Valid");
                    medNomorh.requestFocus();
                    return;
                }
                if (medNomorh.length()<13){
                    medNomorh.setError("Nomor Hanphone Tidak Valid");
                    medNomorh.requestFocus();
                    return;
                }
                if (norm.length()== 1){
                    metNomor.setError("Nomor Rekam Medik Tidak Valis");
                    metNomor.requestFocus();
                    return;
                }
                if (norm.length()==2){
                    metNomor.setError("Nomor Rekam Medik Tidak Valis");
                    metNomor.requestFocus();
                    return;
                }
                if (norm.length()==3){
                    metNomor.setError("Nomor Rekam Medik Tidak Valis");
                    metNomor.requestFocus();
                    return;
                }
                if (norm.length()==4){
                    metNomor.setError("Nomor Rekam Medik Tidak Valis");
                    metNomor.requestFocus();
                    return;
                }
                if (norm.length()==5){
                    metNomor.setError("Nomor Rekam Medik Tidak Valis");
                    metNomor.requestFocus();
                    return;
                }
                if (norm.length()>6){
                    metNomor.setError("Nomor Rekam Medik Tidak Valis");
                    metNomor.requestFocus();
                    return;
                }
                SimpanProfil();
            }
        });
    }

    private void SimpanProfil() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Profil");
        alertDialogBuilder
                .setMessage("Apakah Data Anda Sudah Sesuai ? \n Nama : "+ metNAma.getText().toString()+"\n Nomor Rekam Medis : "+metNomor.getText().toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date waktu = new Date();
                        SimpleDateFormat getTime = new SimpleDateFormat("dd/MM/yyy HH:mm");
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                        String nama = metNAma.getText().toString();
                        String norm = metNomor.getText().toString();
                        String alamat   = metAlamat.getText().toString();
                        String Hp = medNomorh.getText().toString();
                        String Time = getTime.format(waktu).toString();

                        String token = FirebaseInstanceId.getInstance().getToken();
                        String Pesan = "Tidak Ada Pesan";
                        Map newpost = new HashMap();
                        newpost.put("nama", nama);
                        newpost.put("norm", norm);
                        newpost.put("Alamat", alamat);
                        newpost.put("NoHp", Hp);
                        newpost.put("waktuLogin", Time);
                        newpost.put("Token",token);
                        newpost.put("Pesan", Pesan);
                        current_db.setValue(newpost);
                        Intent i = new Intent(LengkapiDataActivity.this, DrawerActivity.class);
                        startActivity(i);
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
}
