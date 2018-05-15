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





                String user_id = mAuth.getCurrentUser().getUid();

                DatabaseReference current_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                String nama = metNAma.getText().toString();
                String norm = metNomor.getText().toString();
                String alamat = metAlamat.getText().toString();
                String tglLahir =  metTglLahir.getText().toString();
                String tmptLahir = metTempatLahir.getText().toString();
                String token = FirebaseInstanceId.getInstance().getToken();

                if (nama.isEmpty()){
                    metNAma.setError("Nama Tidak Boleh Kosong");
                    metNAma.requestFocus();
                    return;
                }
                if (alamat.isEmpty()){
                    metAlamat.setError("Nama Tidak Boleh Kosong");
                    metAlamat.requestFocus();
                    return;
                }
                if (tglLahir.isEmpty()){
                    metTglLahir.setError("Nama Tidak Boleh Kosong");
                    metTglLahir.requestFocus();
                    return;
                }
                if (tglLahir.isEmpty()){
                    metTglLahir.setError("Nama Tidak Boleh Kosong");
                    metTglLahir.requestFocus();
                    return;
                }
                if (tmptLahir.isEmpty()){
                    metTempatLahir.setError("Tempat Lahir Tidak Boleh Kosong");
                    metTempatLahir.requestFocus();
                }
                Map newpost = new HashMap();
                newpost.put("nama", nama);
                newpost.put("norm", norm);
                newpost.put("alamat", alamat);
                newpost.put("tglLahit", tglLahir);
                newpost.put("tmptLahir", tmptLahir);
                newpost.put("Token",token);

                current_db.setValue(newpost);

                Intent i = new Intent(LengkapiDataActivity.this, DrawerActivity.class);
                startActivity(i);
            }
        });
    }
}
