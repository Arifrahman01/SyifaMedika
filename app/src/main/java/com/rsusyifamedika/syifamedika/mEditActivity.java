package com.rsusyifamedika.syifamedika;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class mEditActivity extends AppCompatActivity {
    private EditText metNama, medNomor, medAlamat,metTmptLahir, metTglLahir;
    private Button mbtnSimpan, mbtnBatal;
    private TextView mtvNama, mtvRM, mtvAL, mtvTempatLahir1, mtvTglLahir1;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_edit);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mbtnBatal = (Button) findViewById(R.id.btnBatal);
        mbtnSimpan = (Button) findViewById(R.id.btnSimpan);

        metNama = (EditText) findViewById(R.id.etNama);
        medNomor = (EditText) findViewById(R.id.edNomor);
        medAlamat = (EditText) findViewById(R.id.etAlama);
        metTmptLahir = (EditText) findViewById(R.id.etTmptLahir);
        metTglLahir = (EditText) findViewById(R.id.etTglLahir);

        mtvNama = (TextView) findViewById(R.id.tvNama) ;
        mtvRM = (TextView) findViewById(R.id.tvRm);
        mtvAL = (TextView) findViewById(R.id.tvAL) ;
        mtvTempatLahir1 = (TextView) findViewById(R.id.tvTempatLahir1) ;
        mtvTglLahir1 = (TextView) findViewById(R.id.tvTglLahir1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Lengkapi Data Diri Anda:" + databaseError.getCode());
            }
        });
    }
    private void showdata (DataSnapshot dataSnapshot){
        for (DataSnapshot ds :dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();
            uInfo.setNama(ds.child(userID).getValue(UserInformation.class).getNama());
            uInfo.setNorm(ds.child(userID).getValue(UserInformation.class).getNorm());
            uInfo.setAlamat(ds.child(userID).getValue(UserInformation.class).getAlamat());
            uInfo.setTempat(ds.child(userID).getValue(UserInformation.class).getTempat());
            uInfo.setTgl_lahir(ds.child(userID).getValue(UserInformation.class).getTgl_lahir());

            mtvNama.setText(uInfo.getNama());
            mtvRM.setText(uInfo.getNorm());
            mtvAL.setText(uInfo.getAlamat());
            mtvTempatLahir1.setText(uInfo.getTempat());
            mtvTglLahir1.setText(uInfo.getTgl_lahir());
            }
    mbtnSimpan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nama = metNama.getText().toString().trim();
            String norm = medNomor.getText().toString().trim();
            String alamat = medAlamat.getText().toString().trim();
            String tmptlahir = metTmptLahir.getText().toString().trim();
            String tgllahir = metTglLahir.getText().toString().trim();


            if (TextUtils.isEmpty(nama)){
                metNama.setError("Nama Tidak Boleh Kosong");
                metNama.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(norm)){
                medNomor.setError("Nomor Rekam Medis Tidak Boleh Kosong");
                medNomor.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(alamat)){
                medAlamat.setError("Alamat Tidak Boleh Kosong");
                medAlamat.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(tmptlahir)){
                metTmptLahir.setError("Tempat Lahir Tidak Boleh Kosong");
                metTmptLahir.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(tgllahir)){
                metTglLahir.setError("Tanggal Lahir Tidak Boleh Kosong");
                metTglLahir.requestFocus();
                return;
            }
            AllertSimpan();
        }
    });
    }
    private void AllertSimpan() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Simpan");
        alertDialogBuilder
                .setMessage("Simpan Pembaruan ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                        String nama = metNama.getText().toString();
                        String tempat = metTmptLahir.getText().toString();
                        String alamat = medAlamat.getText().toString();
                        String tgl = metTglLahir.getText().toString();
                        String no_rm = medNomor.getText().toString();

                        Map newPost = new HashMap();
                        newPost.put("nama", nama);
                        newPost.put("tempat", tempat);
                        newPost.put("alamat", alamat);
                        newPost.put("tgl_lahir", tgl);
                        newPost.put("norm", no_rm);
                        current_user_db.setValue(newPost);
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