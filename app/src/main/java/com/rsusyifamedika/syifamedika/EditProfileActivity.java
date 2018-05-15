package com.rsusyifamedika.syifamedika;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;
    private EditText mEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mListView = (ListView) findViewById(R.id.listview);
        // Ke Firebase AUTH
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mEdit = (EditText) findViewById(R.id.etEdit) ;
//

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Harap Periksa Data Diri Anda " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            //menarik data
            UserInformation uInfo = new UserInformation();
            uInfo.setNama(ds.child(userID).getValue(UserInformation.class).getNama()); //nama
            uInfo.setAlamat(ds.child(userID).getValue(UserInformation.class).getAlamat()); //alamat
            uInfo.setNorm(ds.child(userID).getValue(UserInformation.class).getNorm()); // No RM
            uInfo.setTempat(ds.child(userID).getValue(UserInformation.class).getTempat()); //Tempat Lahor
            uInfo.setTgl_lahir(ds.child(userID).getValue(UserInformation.class).getTgl_lahir()); //Tanggal Lahir

            //menampilkan data
            Log.d(TAG, "showData: name: " + uInfo.getNama());
            Log.d(TAG, "showData: alamat: " + uInfo.getAlamat());
            Log.d(TAG, "showData: norm: " + uInfo.getNorm());
            Log.d(TAG, "showData: tempat: " + uInfo.getTempat());
            Log.d(TAG, "showData: tgl_lahir: " + uInfo.getTgl_lahir());

            ArrayList<String> array  = new ArrayList<>();
            array.add(uInfo.getNama());
            array.add(uInfo.getTempat());
            array.add(uInfo.getNorm());
            array.add(uInfo.getAlamat());
            array.add(uInfo.getTgl_lahir());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
            mEdit.setText(uInfo.getNama().toString());

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();



    }
}
