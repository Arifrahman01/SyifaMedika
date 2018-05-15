package com.rsusyifamedika.syifamedika.Daftar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button mbtDaftar;
    private EditText etPassword;
    private EditText etEmail, medNamaDaftar;
    private TextView mtLogin;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        mbtDaftar = (Button) findViewById(R.id.btDaftar);
        mtLogin = (TextView) findViewById(R.id.tLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        medNamaDaftar = (EditText) findViewById(R.id.edNamaPendaftar);



        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
        }

        mtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        mbtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String nama = medNamaDaftar.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            //keadaan jika email kosong
            Toast.makeText(this, "Harap Masukkan Email", Toast.LENGTH_SHORT).show();
            //menyetop function
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Masukkan Email Yang Valid");
            etEmail.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Harap Masukkan Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Password Tidak Boleh kurang Dari 6 Karakter");
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nama)){
            medNamaDaftar.setError("Nama Tidak Boleh Kosong");
            medNamaDaftar.requestFocus();
            return;
        }

        progressDialog.setMessage("Sedang Mendfatarkan Pengguna....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            progressDialog.setMessage("Pendafratan Berhasil");
                            progressDialog.show();
                            String user_id  = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference dataPendaftar  = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            String nama = medNamaDaftar.getText().toString();
                            String status = "Belum di Cek";
                            Map newPost = new HashMap();
                            newPost.put("nama",nama);
                            newPost.put("status",status);
                            dataPendaftar.setValue(newPost);
                            startActivity(new Intent(getApplicationContext(), VerifikasiActivity.class));
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}

