package com.rsusyifamedika.syifamedika.Poliklinik;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PenyakitDalamActivity extends AppCompatActivity {

    private ImageView mimageJadwal ;
    private FirebaseDatabase mFirebaseDatabase;
    private EditText medNama1, medNoIdenditas1, medPemesanan1, medKeluhan1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private Spinner mspinnerDokterPenyakidDalam;
    private Button mbtDaftar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit_dalam);

        mspinnerDokterPenyakidDalam = (Spinner) findViewById(R.id.spinnerDokterPenyakidDalam);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();

        medNama1 = (EditText) findViewById(R.id.edNama1);
        medNoIdenditas1 = (EditText)  findViewById(R.id.edNoIdenditas1);
        medKeluhan1 = (EditText)  findViewById(R.id.edKeluhan1);
        medPemesanan1 = (EditText) findViewById(R.id.edWaktuPemesanan);
        mspinnerDokterPenyakidDalam = (Spinner) findViewById(R.id.spinnerDokterPenyakidDalam);

        findViewById(R.id.btDaftarPenyakitDalam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDaftarPenyalkitDalam();

                }
        });

        mimageJadwal = (ImageView) findViewById(R.id.imgJadwal);
        mimageJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllertTampilJadwal();
            }
        });

        myRef.child("DokterPenyakitDalam").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> DokterPenyakitDalam = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Dokter = ds.getValue(String.class);
                    DokterPenyakitDalam.add(Dokter);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PenyakitDalamActivity.this, android.R.layout.simple_spinner_dropdown_item, DokterPenyakitDalam);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspinnerDokterPenyakidDalam.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mspinnerDokterPenyakidDalam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Silahkan Pilih Dokter Spesialis", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        AlertPenyakitDalam();
                        break;
                    case 2:
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void AlertDaftarPenyalkitDalam() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Apakah Data Anda Sudah Sesuai ?");
        alertDialogBuilder
                .setMessage("Nama : "+medNama1.getText().toString()+"\nNomor Identitas : "+medNoIdenditas1.getText().toString()+"\nDokter : "+mspinnerDokterPenyakidDalam.getSelectedItem().toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("PemesananPoli").child(user_id);
                        String nama1 = medNama1.getText().toString();
                        String noindenditas = medNoIdenditas1.getText().toString();
                        String waktu = medPemesanan1.getText().toString();
                        String dokterspesialis = mspinnerDokterPenyakidDalam.getSelectedItem().toString();
                        String keluhan = medKeluhan1.getText().toString();
                        String poli = "Klinik Penyakit Dalam";
                        String status = "Belum Terkonfirmasi";

                        Map newPost = new HashMap();
                        newPost.put("nama", nama1);
                        newPost.put("noidentitas", noindenditas);
                        newPost.put("dokterspesialis", dokterspesialis);
                        newPost.put("waktupemesanan", waktu);

                        newPost.put("keluhan", keluhan);
                        newPost.put("poli", poli);
                        newPost.put("status", status);

                        current_user_db.setValue(newPost);
                        Toast.makeText(getApplicationContext(),
                                "Pendaftaran Berhasil", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(PenyakitDalamActivity.this, DrawerActivity.class);
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

    private void AllertTampilJadwal() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Jadwal Praktek");
        alertDialogBuilder
                .setMessage("Senin – Jum’at\n" +
                        "14.30 – 16.00 : dr. Achmad Dainuri, Sp.PD\n" +
                        "17.00 – 21.00 : dr. Budi Indra, Sp.PD")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}