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

public class KlinikAnakActivity extends AppCompatActivity {
    private ImageView mimageJadwal ;
    private FirebaseDatabase mFirebaseDatabase;
    private EditText medNamaAnak, medNoIdenditasAnak, medPemesananAnak, medKeluhanAnak;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private Spinner mspinnerDokterAnak;
    private Button mbtDaftarAnak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_anak);

        mspinnerDokterAnak = (Spinner) findViewById(R.id.spinnerDokterAnak);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();

        medNamaAnak = (EditText) findViewById(R.id.edNamaAnak);
        medNoIdenditasAnak = (EditText)  findViewById(R.id.edNoIdenditasAnak);
        medKeluhanAnak = (EditText)  findViewById(R.id.edKeluhanAnak);
        medPemesananAnak = (EditText) findViewById(R.id.edWaktuPemesananAnak);
        mspinnerDokterAnak = (Spinner) findViewById(R.id.spinnerDokterAnak);

        findViewById(R.id.btDaftarPenyakitDalam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = medNamaAnak.getText().toString().trim();
                String ID = medNoIdenditasAnak.getText().toString().trim();
                String Waktu = medPemesananAnak.getText().toString().trim();
                String Keluhan = medKeluhanAnak.getText().toString().trim();

                if (TextUtils.isEmpty(nama)){
                    medNamaAnak.setError("Nama Tidak Boleh Kosong");
                    medNamaAnak.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(ID)){
                    medNoIdenditasAnak.setError("Identitas Tidak Boleh Kosong");
                    medNoIdenditasAnak.requestFocus();
                    return;
                }
                if (ID.length() <10 ){
                    medNoIdenditasAnak.setError("Nomor Tidak Valid");
                    medNoIdenditasAnak.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Waktu)){
                    medPemesananAnak.setError("Waktu Pemesanan Tidak Boleh Kosong");
                    medPemesananAnak.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Keluhan)){
                    medKeluhanAnak.setError("SIlahkan Isi Keluhan Anda");
                    medKeluhanAnak.requestFocus();
                    return;
                }



                AlertDaftarKlinikAnak();

            }
        });

        mimageJadwal = (ImageView) findViewById(R.id.imgJadwal);
        mimageJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllertTampilJadwal();
            }
        });

        myRef.child("DokterAnak").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> DokterPenyakitDalam = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Dokter = ds.getValue(String.class);
                    DokterPenyakitDalam.add(Dokter);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(KlinikAnakActivity.this, android.R.layout.simple_spinner_dropdown_item, DokterPenyakitDalam);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspinnerDokterAnak.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mspinnerDokterAnak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void AlertDaftarKlinikAnak() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Apakah Data Anda Sudah Sesuai ?");
        alertDialogBuilder
                .setMessage("Nama : "+medNamaAnak.getText().toString()+"\nNomor Identitas : "+medNoIdenditasAnak.getText().toString()+"\nDokter : "+mspinnerDokterAnak.getSelectedItem().toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("PemesananPoli").child(user_id);
                        String nama1 = medNamaAnak.getText().toString();
                        String noindenditas = medNoIdenditasAnak.getText().toString();
                        String waktu = medPemesananAnak.getText().toString();
                        String dokterspesialis = mspinnerDokterAnak.getSelectedItem().toString();
                        String keluhan = medKeluhanAnak.getText().toString();
                        String poli = "Klinik Anak";
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
                        Intent i = new Intent(KlinikAnakActivity.this, DrawerActivity.class);
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
                        "16.00 – 18.00 : dr. Hj. Naili Muna, Sp.A.M.Kes")
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
