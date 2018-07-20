package com.rsusyifamedika.syifamedika.Daftar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaftarPoliklinikActivity extends AppCompatActivity {
    private EditText medNamaPoli;
    private Spinner mspPoli, mspWaktuPoli, mspDokterPoli;
    private Button mbtDaftarPoli;
    private TextView mtvNormPoli;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID, Norm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_poliklinik);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        medNamaPoli = (EditText) findViewById(R.id.edNamaPolii);

        mspPoli = (Spinner) findViewById(R.id.spPoli);
        mspDokterPoli = (Spinner) findViewById(R.id.spDokterPoli);
        mspWaktuPoli = (Spinner) findViewById(R.id.spWwaktuPoli);

        mtvNormPoli =(TextView) findViewById(R.id.tvNormPoli);
        findViewById(R.id.btDaftarPolii).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NamaPoli = medNamaPoli.getText().toString().trim();

                if (TextUtils.isEmpty(NamaPoli)) {
                    medNamaPoli.setError("Nama Tidak Boleh Kosong");
                    medNamaPoli.requestFocus();
                    return;
                }
                AlertKonfirmasiDataPemesananPoli();
            }
        });
        myRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("nama").getValue(String.class);
                String Noo = dataSnapshot.child("norm").getValue(String.class);
                medNamaPoli.setText(value);
                mtvNormPoli.setText(Noo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("Poliklinik").child("Poli").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> MTanggungan = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Tanggungan = ds.getValue(String.class);
                    MTanggungan.add(Tanggungan);
                }
                ArrayAdapter<String> AdapterTanggungan = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item, MTanggungan);
                AdapterTanggungan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspPoli.setAdapter(AdapterTanggungan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mspPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterAnak").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliAnak();
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterBedah").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliBdah();
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigi").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliGigi();
                        break;
                    case 3:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigiAnak").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliGigiAnak();
                        break;
                    case 4:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigiBedahMulut").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliGigiBedahMulut();
                        break;
                    case 5:
                        myRef.child("Poliklinik").child("Dokter").child("DokterMata").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliMata();
                        break;
                    case 6:
                        myRef.child("Poliklinik").child("Dokter").child("DokterParu").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliParu();
                        break;
                    case 7:
                        myRef.child("Poliklinik").child("Dokter").child("DokterPenyakitDalam").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliPenyakitDalam();
                        break;
                    case 8:
                        myRef.child("Poliklinik").child("Dokter").child("DokterRehabMedik").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliRehabMedik();
                        break;
                    case 9:
                        myRef.child("Poliklinik").child("Dokter").child("DokterSyaraf").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliSyaraf();
                        break;
                    case 10:
                        myRef.child("Poliklinik").child("Dokter").child("DokterTHT").child("Nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspDokterPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        PoliTHT();
                        break;
                    case 11:
                        Toast.makeText(getApplicationContext(),
                                "Silahkan Perbarui Aplikasi", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void AlertKonfirmasiDataPemesananPoli() {
        String Nama = medNamaPoli.getText().toString();
        String Poli = mspPoli.getSelectedItem().toString();
        String Dokter = mspDokterPoli.getSelectedItem().toString();
        String Waktu = mspWaktuPoli.getSelectedItem().toString();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pemesanan Poliklinik");
        alertDialogBuilder
                .setMessage("Apakah Data Anda Sudah Sesuai ?\n Nama :\n     " + Nama + "\n Poliklinik Tujuan :\n     "+Poli +"\n Dokter Praktek :\n     "+ Dokter +"\n Jadwal Pemesanan :\n     "+ Waktu)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date waktu = new Date();
                        SimpleDateFormat getTime = new SimpleDateFormat("dd/MM/yyy HH:mm");

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("PemesananPoli").child(user_id);
                        String nama = medNamaPoli.getText().toString();
                        String Poli = mspPoli.getSelectedItem().toString();
                        String Dokter = mspDokterPoli.getSelectedItem().toString();
                        String Jadwal = mspWaktuPoli.getSelectedItem().toString();
                        String Norm  = mtvNormPoli.getText().toString();
                        String Status = "Belum Terkonfirmasi";
                        String Timer = getTime.format(waktu).toString();

                        Map newPost = new HashMap();
                        newPost.put("nama", nama);
                        newPost.put("Poli", Poli);
                        newPost.put("Dokter", Dokter);
                        newPost.put("Jadwal", Jadwal);
                        newPost.put("norm", Norm);
                        newPost.put("Waktu", Timer);
                        newPost.put("status",Status);
                        current_user_db.setValue(newPost);
                        AlertBerhasilDaftar();
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

    private void AlertBerhasilDaftar() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Sukses");
        alertDialogBuilder
                .setMessage("Pendaftaran Berhasil, Silahkan Tunggu Beberapa Menit Untuk Konfirmasi")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(DaftarPoliklinikActivity.this, DrawerActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog  = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void PoliTHT() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterTHT").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterTHT").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterTHT").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void PoliRehabMedik() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterRehabMedik").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterRehabMedik").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterRehabMedik").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void PoliPenyakitDalam() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterPenyakitDalam").child("Jadwal1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterPenyakitDalam").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterPenyakitDalam").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void PoliParu() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterParu").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterParu").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterParu").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void PoliMata() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterMata").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterMata").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterMata").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void PoliGigiBedahMulut() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int index;
            index = parent.getSelectedItemPosition();
            switch (index){
                case 0:
                    myRef.child("Poliklinik").child("Dokter").child("DokterGigiBedahMulut").child("Jadwal").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> ProvAceh = new ArrayList<String>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                String Prov = ds.getValue(String.class);
                                ProvAceh.add(Prov);
                            }
                            ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                            ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mspWaktuPoli.setAdapter(ArrayProv);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    break;
                case 1:
                    myRef.child("Poliklinik").child("Dokter").child("DokterGigiBedahMulut").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> ProvAceh = new ArrayList<String>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                String Prov = ds.getValue(String.class);
                                ProvAceh.add(Prov);
                            }
                            ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                            ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mspWaktuPoli.setAdapter(ArrayProv);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    break;
                case 2:
                    myRef.child("Poliklinik").child("Dokter").child("DokterGigiBedahMulut").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> ProvAceh = new ArrayList<String>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                String Prov = ds.getValue(String.class);
                                ProvAceh.add(Prov);
                            }
                            ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                            ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mspWaktuPoli.setAdapter(ArrayProv);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
    }

    private void PoliGigiAnak() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigiAnak").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigiAnak").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigiAnak").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void PoliGigi() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigi").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigi").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterGigi").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void PoliSyaraf() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterSyaraf").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterSyaraf").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterSyaraf").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void PoliBdah() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterBedah").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterBedah").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterBedah").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void PoliAnak() {
        mspDokterPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index){
                    case 0:
                        myRef.child("Poliklinik").child("Dokter").child("DokterAnak").child("Jadwal").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        myRef.child("Poliklinik").child("Dokter").child("DokterAnak").child("Jadwal2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        myRef.child("Poliklinik").child("Dokter").child("DokterAnak").child("Jadwal3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarPoliklinikActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspWaktuPoli.setAdapter(ArrayProv);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
