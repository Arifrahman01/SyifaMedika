package com.rsusyifamedika.syifamedika.Poliklinik;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class GigiAnakActivity extends AppCompatActivity {
    private ImageView mimageJadwal;
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
        setContentView(R.layout.activity_gigi_anak);

        mspinnerDokterPenyakidDalam = (Spinner) findViewById(R.id.spinnerDokterMata);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();

        medNama1 = (EditText) findViewById(R.id.edNamaGigiAnak);
        medNoIdenditas1 = (EditText) findViewById(R.id.edNoIdenditasGigiAnak);
        medKeluhan1 = (EditText) findViewById(R.id.edKeluhanGigiAnak);
        medPemesanan1 = (EditText) findViewById(R.id.edWaktuAnak);
        mspinnerDokterPenyakidDalam = (Spinner) findViewById(R.id.spinnerDokterGigiAnak);

        findViewById(R.id.btDaftarGigiAnak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = medNama1.getText().toString().trim();
                String ID = medNoIdenditas1.getText().toString().trim();
                String Waktu = medPemesanan1.getText().toString().trim();
                String Keluhan = medKeluhan1.getText().toString().trim();

                if (TextUtils.isEmpty(nama)) {
                    medNama1.setError("Nama Tidak Boleh Kosong");
                    medNama1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(ID)) {
                    medNoIdenditas1.setError("Identitas Tidak Boleh Kosong");
                    medNoIdenditas1.requestFocus();
                    return;
                }
                if (ID.length() < 10) {
                    medNoIdenditas1.setError("Nomor Tidak Valid");
                    medNoIdenditas1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Waktu)) {
                    medPemesanan1.setError("Waktu Pemesanan Tidak Boleh Kosong");
                    medPemesanan1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Keluhan)) {
                    medKeluhan1.setError("SIlahkan Isi Keluhan Anda");
                    medKeluhan1.requestFocus();
                    return;
                }


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

        myRef.child("DokterGigiAnak").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> DokterPenyakitDalam = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Dokter = ds.getValue(String.class);
                    DokterPenyakitDalam.add(Dokter);

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GigiAnakActivity.this, android.R.layout.simple_spinner_dropdown_item, DokterPenyakitDalam);
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
                .setMessage("Nama : " + medNama1.getText().toString() + "\nNomor Identitas : " + medNoIdenditas1.getText().toString() + "\nDokter : " + mspinnerDokterPenyakidDalam.getSelectedItem().toString())
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
                        String poli = "Klinik Gigi Anak";
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
                        Intent i = new Intent(GigiAnakActivity.this, DrawerActivity.class);
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

    private void AllertTampilJadwal() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Jadwal Praktek");
        alertDialogBuilder
                .setMessage("Senin – Kamis\n" +
                        "13.00-15.00: drg. Ike Ratna Dewi, Sp. KGA, M. Kes")
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
