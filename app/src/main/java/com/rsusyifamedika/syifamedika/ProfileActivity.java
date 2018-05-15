package com.rsusyifamedika.syifamedika;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rsusyifamedika.syifamedika.Daftar.LoginActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mbtSimpanProfile, mbtLogout;
    private EditText metNamaProfile, metTmptlahirProfile,
            metAlamatProfile ,mDateL;
    private static final String TAG = "ProfileActivity";
    private TextView mDisplayDate, mtvKartu;
    private DatePickerDialog.OnDateSetListener mDateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        //Logout Otomatis
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Harap Login Terlebih Dahulu", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Intent intent = new Intent(ProfileActivity.this, DrawerActivity.class);
                startActivity(intent);
                finish();
            }
        };

        mbtSimpanProfile = (Button) findViewById(R.id.btSimpanProfile);
        mbtLogout        = (Button) findViewById(R.id.btLogout);
        metNamaProfile = (EditText) findViewById(R.id.etNamaProfile);
        metTmptlahirProfile = (EditText) findViewById(R.id.etTmptlahirProfile);
        metAlamatProfile = (EditText) findViewById(R.id.etAlamatProfile);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mtvKartu = (TextView) findViewById(R.id.tvKartu) ;
        mDateL = (EditText) findViewById(R.id.etDate);

        mtvKartu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, KartuActivity.class);
                startActivity(i);
            }
        });


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                year = year -28;
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy " + dayOfMonth + "/" + month + "/" + year);
                String dates = dayOfMonth + "/" + month + "/" + year;
//                mDisplayDate.setText(dates);
                mDateL.toString();
                mDateL.setText(dates);
            }
        };

        mbtSimpanProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                String nama = metNamaProfile.getText().toString();
                String tempat = metTmptlahirProfile.getText().toString();
                String alamat = metAlamatProfile.getText().toString();
                String tgl = mDateL.getText().toString();
                String no_rm = "000000";

                Map newPost = new HashMap();
                newPost.put("nama", nama);
                newPost.put("tempat", tempat);
                newPost.put("alamat", alamat);
                newPost.put("tgl_lahir",tgl);
                newPost.put("norm", no_rm);
                current_user_db.setValue(newPost);
            }
        });
        mbtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllertKeluar();
            }
        });
    }
    // Batas Protecd Void


//    private void showData (DataSnapshot dataSnapshot){
//        for (DataSnapshot ds :dataSnapshot.getChildren()){
//            UserInformation uInfo = new UserInformation();
//            uInfo.setNama(ds.child(userID).getValue(UserInformation.class).getNama());
//            uInfo.setNorm(ds.child(userID).getValue(UserInformation.class).getNorm());
//            uInfo.setTempat(ds.child(userID).getValue(UserInformation.class).getTempat());
//            uInfo.setAlamat(ds.child(userID).getValue(UserInformation.class).getAlamat());
//            uInfo.setTgl_lahir(ds.child(userID).getValue(UserInformation.class).getTgl_lahir());
//
//            metNamaProfile.setText(uInfo.getNama().toString());
//            metTmptlahirProfile.setText(uInfo.getTempat().toString());
//            metAlamatProfile.setText(uInfo.getAlamat().toString());
//            mDateL.setText(uInfo.getTgl_lahir().toString());
//        }
//    }
    private void AllertKeluar() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Keluar");
        alertDialogBuilder
                .setMessage("Anda Yakin Ingin Keluar ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
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
    public void cekProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
        startActivity(intent);

    }
}
