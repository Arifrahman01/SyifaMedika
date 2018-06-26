package com.rsusyifamedika.syifamedika;

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
    private EditText metNAma, metNomor, metAlamat, metTempatLahir, metTglLahir;


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
        metAlamat = (EditText) findViewById(R.id.etAlama) ;
        metTempatLahir = (EditText) findViewById(R.id.etTmptLahir) ;
        metTglLahir = (EditText) findViewById(R.id.etTglLahir) ;
        userID = user.getUid();
        mbtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date waktu = new Date();
                SimpleDateFormat getTime = new SimpleDateFormat("dd/MM/yyy HH:mm");

                String user_id = mAuth.getCurrentUser().getUid();

                DatabaseReference current_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                String nama = metNAma.getText().toString();
                String norm = metNomor.getText().toString();
                String Time = getTime.format(waktu).toString();
                String token = FirebaseInstanceId.getInstance().getToken();

                if (nama.isEmpty()){
                    metNAma.setError("Nama Tidak Boleh Kosong");
                    metNAma.requestFocus();
                    return;
                }
                if (norm.isEmpty()){
                    metAlamat.setError("Nomor Rekam Medis Tidak Boleh Kosong");
                    metAlamat.requestFocus();
                    return;
                }
                if (norm.length() < 6){
                    metNomor.setError("Nomor Rekam Medis Tidak Valid");
                    metNomor.requestFocus();
                    return;
                }
                if (norm.length()>6){
                    metNomor.setError("Nomor Rekam Medis Tidak Valid");
                    metNomor.requestFocus();
                    return;

                }



                Map newpost = new HashMap();
                newpost.put("nama", nama);
                newpost.put("norm", norm);
                newpost.put("waktuLogin", Time);
                newpost.put("Token",token);
                current_db.setValue(newpost);
                Intent i = new Intent(LengkapiDataActivity.this, DrawerActivity.class);
                startActivity(i);
            }
        });
    }
}
