package com.rsusyifamedika.syifamedika.Daftar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.LengkapiDataActivity;
import com.rsusyifamedika.syifamedika.R;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class LoginNoPhoneActivity extends AppCompatActivity {
    private EditText medNoHandphone, medKodeVerifikasi;
    private Button mbtKirimKode, mbtMasukNohp;
    private ProgressBar mpbNoHP, mpbKodeVerifikasi, mpbMasuk;
    private TextView mtvKirimUlang;

    private ImageView mLock, mTelepon;

    private FirebaseAuth mAuth;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_no_phone);

        medNoHandphone = findViewById(R.id.edNomorHP);
        medKodeVerifikasi = findViewById(R.id.edKodeVerifikasi);
        mtvKirimUlang = findViewById(R.id.tvKirimUlang);
        mpbNoHP = findViewById(R.id.pbNomorHP);
        mpbKodeVerifikasi = findViewById(R.id.pbKodeVerifikASI);
        mpbMasuk = findViewById(R.id.pbMasuk);

        mLock = findViewById(R.id.imLock);
        mTelepon = findViewById(R.id.imTelepon);

        mbtMasukNohp = findViewById(R.id.btMasukNoHP);
        mbtKirimKode = findViewById(R.id.btKirimKode);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
        }

        findViewById(R.id.btKirimKode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomor = medNoHandphone.getText().toString();
                if (nomor.isEmpty()) {
                    medNoHandphone.setError("Masukan Nomor Handphone");
                    medNoHandphone.requestFocus();
                    return;
                }
                if (!Patterns.PHONE.matcher(nomor).matches()) {
                    medNoHandphone.setError("Nomor Handpone Tidak Valid");
                    medNoHandphone.requestFocus();
                    return;
                }
                if (nomor.length() < 11) {
                    medNoHandphone.setError("Nomor Handpone Tidak Valid");
                    medNoHandphone.requestFocus();
                    return;

                }
                if (nomor.length() > 13) {
                    medNoHandphone.setError("Nomor Handpone Tidak Valid");
                    medNoHandphone.requestFocus();
                    return;

                }
                String text = "120";
                int detik = Integer.valueOf(text);
                CountDownTimer countDownTimer = new CountDownTimer(detik * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mtvKirimUlang.setText("Kirim Ulang Kode Dalam : "+ (int)(millisUntilFinished / 1000));


                    }

                    @Override
                    public void onFinish() {
                        mtvKirimUlang.setText("");
                        mbtKirimKode.setVisibility(View.VISIBLE);
                        medNoHandphone.setEnabled(true);

                    }
                }.start();


                AlertKirimKode();


            }
        });

        findViewById(R.id.btMasukNoHP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = medKodeVerifikasi.getText().toString();
                if (code.isEmpty()) {
                    medKodeVerifikasi.setError("Masukkan Kode Verifikasi");
                    medKodeVerifikasi.requestFocus();
                }
                if (code.length() > 8) {
                    medKodeVerifikasi.setError("Kode Verifikasi Tidak Valid");
                    medKodeVerifikasi.requestFocus();

                }
                if (code.length() < 5) {
                    medKodeVerifikasi.setError("Kode Verifikasi Tidak Valid");
                    medKodeVerifikasi.requestFocus();
                }


                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // Tampilan
                medKodeVerifikasi.setVisibility(View.VISIBLE);
                mbtKirimKode.setVisibility(View.INVISIBLE);
                mpbNoHP.setVisibility(View.INVISIBLE);
                medNoHandphone.setEnabled(false);
                mbtMasukNohp.setEnabled(true);
                mbtMasukNohp.setVisibility(View.VISIBLE);
                mLock.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),
                        "Permintaan Sedang Diproses, Harap Tunggu Beberapa Saat", Toast.LENGTH_LONG).show();
            }
        };

    }

    private void AlertKirimKode() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Daftartaran");
        alertDialogBuilder
                .setMessage("Apakah Nomor Tersebut Terubung ke WhatsApp ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = medNoHandphone.getText().toString();
                        mpbNoHP.setVisibility(View.VISIBLE);
                        mtvKirimUlang.setVisibility(View.VISIBLE);

                        if (!phoneNumber.substring(0, 2).equals("+62")) {
                            phoneNumber = "+62" + phoneNumber.substring(1);
                        }
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneNumber,
                                60,
                                TimeUnit.SECONDS,
                                LoginNoPhoneActivity.this,
                                mCallbacks
                        );

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        https://play.google.com/store/apps/details?id=com.whatsapp
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            mpbMasuk.setVisibility(View.VISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            Intent i = new Intent(LoginNoPhoneActivity.this, LengkapiDataActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Code Verifikasi Salah", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Gagal, Coba Beberapa Saat Lagi", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}



