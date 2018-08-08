package com.rsusyifamedika.syifamedika.Daftar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.LengkapiDataActivity;
import com.rsusyifamedika.syifamedika.R;

public class VerifikasiActivity extends AppCompatActivity {
    private Button mbtVerifikasi, mbtLanjut;
    private FirebaseAuth mAuth;
    private TextView mtvVerifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);
        mbtVerifikasi = (Button) findViewById(R.id.btVerifikasi);
        mbtLanjut = (Button) findViewById(R.id.btLanjutkan);
        mtvVerifikasi = (TextView) findViewById(R.id.tvVerifikasi);
        mAuth = FirebaseAuth.getInstance();


        mbtVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser()
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    AlertSuksesVerifikasi();
                                else
                                    AlertGagalVerifikasi();
                            }
                        });
            }
        });

       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified() != true) {
            AllertPesanVerifikasi();
//            Toast.makeText(this, "Email Belum TerVerifikasi", Toast.LENGTH_SHORT).show();
        }
        if (user.isEmailVerified() != false) {
            mtvVerifikasi.setText("Email : " + mAuth.getCurrentUser().getEmail().toString() + "Terverifikasi");
            startActivity(new Intent(getApplicationContext(), LengkapiDataActivity.class));
        }

        mbtLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(VerifikasiActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),
                        "Silahkan Masuk Untuk Melanjutkan", Toast.LENGTH_LONG).show();
            }
        });

        mtvVerifikasi.setText("Email : " + mAuth.getCurrentUser().getEmail().toString() + " Belum Terfirifikasi");
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Tidak Dapat Kembali", Toast.LENGTH_LONG).show();
    }

    private void AllertPesanVerifikasi() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Verifikasi Email");
        alertDialogBuilder
                .setMessage("Silahkan Verifikasi Email Terlebih Dahulu")
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

    private void AlertGagalVerifikasi() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Gagal Terkirim");
        alertDialogBuilder
                .setMessage("Silahlan Coba Beberapa Saat Lagi")
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

    private void AlertSuksesVerifikasi() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terkirim");
        alertDialogBuilder

                .setMessage("Silahkan Buka Email Anda Untuk Melanjutkan Pendaftaran")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.email");
//                        startActivity(intent);
                 /*       try{
                            Intent intent = new Intent (Intent.ACTION_VIEW ,Uri.parse("mailto:" + "arifuniska@gmail.com"));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Verifikasi Email");
                            intent.putExtra(Intent.EXTRA_TEXT, "Verifikasi Akan Email ku Si");
                            startActivity(intent);
                        }catch(Exception e){
                        }*/ //untuk membuat pesan email langsung.
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
