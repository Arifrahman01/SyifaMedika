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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsusyifamedika.syifamedika.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PemesananActivity extends AppCompatActivity {
    public Spinner mSpiner, mSpinerDokter;
    private Button mbtnPemesanan;
    private TextView mtvHasil;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ProgressBar mpbPemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        mSpiner = (Spinner) findViewById(R.id.SpinerPoli);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
//        if (mSpiner.getSelectedItem().toString().length() > 1 ){
//            mpbPemesanan.setVisibility(View.GONE);
//        }


        myRef.child("Poliklinik").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> Polispesial = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Poli = ds.getValue(String.class);
                    Polispesial.add(Poli);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_dropdown_item, Polispesial);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpiner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Silahkan Pilih Poliklinik Tujuan", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        AlertPenyakitDalam();
                        break;
                    case 2:
                        AlertBedah();
                        break;
                    case 3:
                        AlertKlinikAnak();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void AlertKlinikAnak() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pilih Poli");
        alertDialogBuilder
                .setMessage("Anda Memilih Poli" + mSpiner.getSelectedItem().toString() + " ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                        Intent i = new Intent(PemesananActivity.this, KlinikAnakActivity.class);
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

    private void AlertBedah() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pilih Poli");
        alertDialogBuilder
                .setMessage("Anda Memilih Poli" + mSpiner.getSelectedItem().toString() + " ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                        Intent i = new Intent(PemesananActivity.this, KlinikBedahActivity.class);
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


    private void AlertPenyakitDalam() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pilih Poli");
        alertDialogBuilder
                .setMessage("Anda Memilih Poli" + mSpiner.getSelectedItem().toString() + " ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(PemesananActivity.this, PenyakitDalamActivity.class);
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


}
