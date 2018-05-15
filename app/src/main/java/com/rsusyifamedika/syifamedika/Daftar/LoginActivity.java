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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rsusyifamedika.syifamedika.LupaPasswordctivity;
import com.rsusyifamedika.syifamedika.R;

public class LoginActivity extends AppCompatActivity {
    private Button mbtMasuk;
    private EditText etEmail;
    private EditText etPassword;
    private TextView mtDaftar, mLupaPass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), VerifikasiActivity.class));
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        mbtMasuk = (Button) findViewById(R.id.btMasuk);
        mtDaftar = (TextView) findViewById(R.id.tDaftar);
        mLupaPass= (TextView) findViewById(R.id.tvLupaPass);
        progressDialog = new ProgressDialog(this);




        mbtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        mLupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, LupaPasswordctivity.class);
                startActivity(i);
            }
        });
        mtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //keadaan jika email kosong
            Toast.makeText(this, "Harap Masukkan Email", Toast.LENGTH_SHORT).show();
            //menyetop function

            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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


        progressDialog.setMessage("Sedang Login, Tunggu Beberapa Saat........");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(LoginActivity.this, VerifikasiActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }

                });
    }
}
