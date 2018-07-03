package com.rsusyifamedika.syifamedika;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rsusyifamedika.syifamedika.Ambulan.PemesananAmbulanActivity;
import com.rsusyifamedika.syifamedika.Daftar.LoginActivity;
import com.rsusyifamedika.syifamedika.Daftar.MenuLoginActivity;
import com.rsusyifamedika.syifamedika.Poliklinik.DaftarDsActivity;
import com.rsusyifamedika.syifamedika.Poliklinik.PemesananActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private TextView mtvNamaDrawer, mtvEmailDrawer, mTVTelepon;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ImageView mingTelpDarurat, mimgjadwalPoli, mimgDokterPraktek, mimgPemesanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mtvNamaDrawer = (TextView) findViewById(R.id.tvNamaDrawer);

        mingTelpDarurat = findViewById(R.id.imgTelpDarurat);
        mimgjadwalPoli = findViewById(R.id.imgjadwalPoli);

        mimgPemesanan = findViewById(R.id.imgPemesanan);





        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
//        findViewById(R.id.buttontes).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String rcp = "082153424447";
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + rcp));
//                startActivity(intent);
//            }
//        });

        mimgjadwalPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DrawerActivity.this, PoliActivity.class);
                startActivity(i);
            }
        });
        mimgPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertPemesananDs();
//                Intent i = new Intent(DrawerActivity.this, PemesananActivity.class);
//                startActivity(i);
            }
        });

    }

    private void AlertPemesananDs() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Poliklinik");
        alertDialogBuilder
                .setMessage("Apakah Anda Mendaftarkan Untuk Orang Lain ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(DrawerActivity.this, DaftarDsActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog  = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actKeluar) {
            AllertKeluar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_keluar) {
            AllertKeluar();
            return true;

        } else if (id == R.id.nav_feedback) {
            Intent i = new Intent(DrawerActivity.this, FeedbackActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_lokasi) {
            Intent i = new Intent(DrawerActivity.this, LokasiActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_propil) {
            Intent i = new Intent(DrawerActivity.this, TesActivity.class);
            startActivity(i);

        } else if (id == R.id.Profile) {
            Intent i = new Intent(DrawerActivity.this, StatusActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Alert Keluar
    private void AllertKeluar() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Keluar");
        alertDialogBuilder
                .setMessage("Anda Yakin Ingin Keluar ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        Intent i = new Intent(DrawerActivity.this, MenuLoginActivity.class);
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
    public void TeleponDarutar(View view) {
        Date waktu = new Date();
        SimpleDateFormat getTime = new SimpleDateFormat("dd/MM/yyy HH:mm");


        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Aemergency").child("Darurat").child(user_id);
        String Darurat = "Darurat";
        String Time = getTime.format(waktu).toString();
        Map newPost = new HashMap();

        newPost.put("Daraurat", Darurat);
        newPost.put("waktu", Time);

        current_user_db.setValue(newPost);

        String rcp = "082153424447";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + rcp));
        startActivity(intent);

    }
    public void Ambulance(View view) {
        Date waktu = new Date();
        SimpleDateFormat getTime = new SimpleDateFormat("dd/MM/yyy HH:mm");

        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Aemergency").child("Ambulance").child(user_id);
        String Darurat = "Ambulance";
        String Time = getTime.format(waktu).toString();
        Map newPost = new HashMap();

        newPost.put("Ambulance", Darurat);
        newPost.put("waktu", Time);

        current_user_db.setValue(newPost);
        String rcp = "082153424447";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + rcp));
        startActivity(intent);
    }
}
