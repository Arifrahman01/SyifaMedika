package com.rsusyifamedika.syifamedika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TesActivity extends AppCompatActivity {
    private Button mbuttonss;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private RadioGroup mtess;
    private EditText edd;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes);

        mbuttonss = (Button) findViewById(R.id.buttonss);
        edd = findViewById(R.id.edd);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mtess = findViewById(R.id.tess);

        mbuttonss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Testing();


            }
        });



    }

    private void Testing() {
        String Tess = edd.getText().toString().trim();

        if (TextUtils.isEmpty(Tess)){


            Toast.makeText(getApplicationContext(),
                    "Testing TIdak Boleh Kosong", Toast.LENGTH_LONG).show();
            findViewById(R.id.Tes1).requestFocus();
            return;
        }


       Lanjut();

    }

    private void Lanjut() {
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("AATES").child(user_id);
        String tes = edd.toString();


        Map newPost = new HashMap();
        newPost.put("nama", edd);
        current_user_db.setValue(newPost);
    }

    public void onRadioButtonClicked(View view) {
        boolean chcked  = ((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.Tes1:
                if (chcked)
                    edd.setText("Tes 111");
                    break;
            case R.id.Test2:
                if (chcked)
                    edd.setText("Tes 22");
                    break;
        }
    }
}
