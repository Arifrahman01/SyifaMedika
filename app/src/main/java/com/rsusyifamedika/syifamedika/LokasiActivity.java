package com.rsusyifamedika.syifamedika;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.rsusyifamedika.syifamedika.Daftar.LoginActivity;


public class LokasiActivity extends AppCompatActivity {
    private WebView mwebViewLokasi;
    private ProgressBar mpbLokasi;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);
        mAuth = FirebaseAuth.getInstance();
        mwebViewLokasi = (WebView) findViewById(R.id.webViewLokasi);
        mpbLokasi= (ProgressBar) findViewById(R.id.pbLokasi);
        mwebViewLokasi.getSettings().setJavaScriptEnabled(true);
        mwebViewLokasi.setWebViewClient(new myWebViewClient());
        mwebViewLokasi.loadUrl("https://www.google.com/maps/place/RSU+Syifa+Medika+Banjarbaru/@-3.4583504,114.81462,17z/data=!3m1!4b1!4m5!3m4!1s0x2de6815c7365c8c3:0xc715acc03ac8135c!8m2!3d-3.4583504!4d114.8168087");

        if (mAuth.getCurrentUser() == null) {
            AllertKeluarPoli();
        }
    }
    private void AllertKeluarPoli() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder
                .setMessage("Silahkan Login Terlibih Dahulu")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        Intent i = new Intent(LokasiActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void AllertError() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder
                .setMessage("Periksa Koneksi Internet Anda...")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(LokasiActivity.this, DrawerActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        if (mwebViewLokasi.canGoBack()) {
            mwebViewLokasi.goBack();
        }
        super.onBackPressed();
    }

    public class myWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mpbLokasi.setVisibility(View.GONE);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            AllertError();
        }
    }
}
