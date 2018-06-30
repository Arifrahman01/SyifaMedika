package com.rsusyifamedika.syifamedika.Poliklinik;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
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

public class KlinikBedahActivity extends AppCompatActivity {

    private ImageView mimgBedah ;
    private FirebaseDatabase mFirebaseDatabase;
    private EditText edNamabedah, edNoIdenditasbedah, medWaktuPemesananBedah, medKeluhanBedah;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private Spinner mspinnerDokterBedah;
    private Button mbtDaftarKlinikBedah;
    private TextView mtv15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_bedah);

        mspinnerDokterBedah = (Spinner) findViewById(R.id.spinnerDokterBedah);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        edNamabedah = (EditText) findViewById(R.id.edNamabedah);
        edNoIdenditasbedah = (EditText)  findViewById(R.id.edNoIdenditasbedah);
        medKeluhanBedah = (EditText)  findViewById(R.id.edKeluhanBedah);
        medWaktuPemesananBedah = (EditText) findViewById(R.id.edWaktuPemesananBedah);
        mspinnerDokterBedah = (Spinner) findViewById(R.id.spinnerDokterBedah);

        mtv15 = findViewById(R.id.textView15);

        findViewById(R.id.btDaftarKlinikBedah).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDaftarBedah();

            }
        });

        mimgBedah = (ImageView) findViewById(R.id.imgBedah);

        mimgBedah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllertTampilJadwal();
            }
        });


        myRef.child("DokterBedah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> DokterPenyakitDalam = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Dokter = ds.getValue(String.class);
                    DokterPenyakitDalam.add(Dokter);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(KlinikBedahActivity.this, android.R.layout.simple_spinner_dropdown_item, DokterPenyakitDalam);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspinnerDokterBedah.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mspinnerDokterBedah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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




    private void AlertDaftarBedah() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Apakah Data Anda Sudah Sesuai ?");
        alertDialogBuilder
                .setMessage("Nama : "+edNamabedah.getText().toString()+"\nNomor Identitas : "+edNoIdenditasbedah.getText().toString()+"\nDokter : "+mspinnerDokterBedah.getSelectedItem().toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("PemesananPoli").child(user_id);
                        String nama1 = edNamabedah.getText().toString();
                        String noindenditas = edNoIdenditasbedah.getText().toString();
                        String waktu = medWaktuPemesananBedah.getText().toString();
                        String dokterspesialis = mspinnerDokterBedah.getSelectedItem().toString();
                        String keluhan = medKeluhanBedah.getText().toString();
                        String poli = "Klinik Bedah";
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
                        Intent i = new Intent(KlinikBedahActivity.this, DrawerActivity.class);
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
                .setMessage("Senin – Jum’at \n17.00 – 21.00 : dr. R.M. Ardani Fitriansyah SY, Sp.B")
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
