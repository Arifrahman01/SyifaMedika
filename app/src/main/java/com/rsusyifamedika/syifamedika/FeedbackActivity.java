package com.rsusyifamedika.syifamedika;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private EditText mSaran;
    private Button mSipan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mSaran = findViewById(R.id.edSaran);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mSipan = findViewById(R.id.btFeedback);


        mSipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback  = mSaran.getText().toString();
                if (TextUtils.isEmpty(feedback)){
                    mSaran.setError("Silahkan Masukan Kritikan dan Saran Anda . .");
                    mSaran.requestFocus();
                    return;
                }

                AllertFeedback();

            }
        });
    }

    private void AllertFeedback() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Kritik dan Saran");
        alertDialogBuilder
                .setMessage("Kirim Kiritikan dan Saran Anda ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(FeedbackActivity.this, DrawerActivity.class);
                        startActivity(i);

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Aemergency").child("Feedback").child(user_id);
                        String saran = mSaran.getText().toString();

                        Map newPost = new HashMap();
                        newPost.put("Kritik dan Saran", saran);

                        current_user_db.setValue(newPost);


                        Toast.makeText(getApplicationContext(),
                                "Terimakasih Atas Kritikan dan Saranya", Toast.LENGTH_LONG).show();
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
}
