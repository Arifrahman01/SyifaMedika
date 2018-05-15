package com.rsusyifamedika.syifamedika.Daftar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.R;

public class VerifNoHPActivity extends AppCompatActivity {
    private Button mbtVerifNoHp;
    private EditText medVerifNoHp;
    private FirebaseAuth mAuth;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif_no_hp);

        mAuth = FirebaseAuth.getInstance();

        mbtVerifNoHp = (Button) findViewById(R.id.btMasukVerifNoHP);
        medVerifNoHp = (EditText)findViewById(R.id.edVerifkasiNoHP);


        mbtVerifNoHp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifikasiKodeMasuk();
            }
        });
    }

    private void VerifikasiKodeMasuk() {
        String code = medVerifNoHp.getText().toString();
        PhoneAuthCredential credential  = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Login Succes", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(VerifNoHPActivity.this, DrawerActivity.class);
                            startActivity(i);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),
                                        "Kode Verifikasi Tidak Cocok", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
