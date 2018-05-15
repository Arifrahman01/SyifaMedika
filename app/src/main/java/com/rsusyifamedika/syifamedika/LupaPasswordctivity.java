package com.rsusyifamedika.syifamedika;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rsusyifamedika.syifamedika.Daftar.MainActivity;

public class LupaPasswordctivity extends AppCompatActivity {
    private EditText medLupaPassword;
    private Button mbtLupaPassword;
    private TextView mKembali;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_passwordctivity);
        medLupaPassword = (EditText) findViewById(R.id.edLupaPassword);
        mbtLupaPassword = (Button)   findViewById(R.id.btLupaPassword);
        mKembali        = (TextView) findViewById(R.id.tvKembali);

        mAuth = FirebaseAuth.getInstance();
        mKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LupaPasswordctivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        mbtLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = medLupaPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    medLupaPassword.setError("Silahkan Masukkan Email");
                    medLupaPassword.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    medLupaPassword.setError("Masukkan Email Yang Valid");
                    medLupaPassword.requestFocus();
                    return;
                }
                resetPassword(medLupaPassword.getText().toString());
            }
        });

    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertSukses();
                            }
                            else {
                            AlertGagal();
                                                    }
                    }
                });
    }
    private void AlertGagal() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Gagal");
        alertDialogBuilder
                .setMessage(medLupaPassword.getText().toString()+" Belum Terdaftar, Silahkan Daftar Terlebih Dahulu")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(LupaPasswordctivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void AlertSukses() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terkirim");
        alertDialogBuilder
                .setMessage("Silahkan Cek Email Anda Untuk Reset Password : "+medLupaPassword.getText().toString())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
/*                        Packet Untuk Langsung Membuka EMail*/
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
