package com.rsusyifamedika.syifamedika;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.rsusyifamedika.syifamedika.Daftar.LoginActivity;
import com.rsusyifamedika.syifamedika.Poliklinik.PemesananActivity;

public class PoliActivity extends AppCompatActivity {
    private WebView webPoli;
    private ProgressBar bar;
    private FirebaseAuth mAuth;
    private ImageView mRegis;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poli);
        mAuth = FirebaseAuth.getInstance();
        webPoli = (WebView) findViewById(R.id.wvPoli);
        bar = (ProgressBar) findViewById(R.id.pbPoli);

        webPoli.setWebViewClient(new myWebViewClient());
//        webPoli.getSettings().setJavaScriptEnabled(true); //untuk mengaktifkan javascrip
        webPoli.loadUrl("http://rsusyifamedika.com/jadwal-poliklinik");
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
                        Intent i = new Intent(PoliActivity.this, LoginActivity.class);
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
                        Intent i = new Intent(PoliActivity.this, DrawerActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (webPoli.canGoBack()) {
            webPoli.goBack();
        }
        super.onBackPressed();
    }

    public void regis(View view) {
        Intent i = new Intent(PoliActivity.this, PemesananActivity.class );
        startActivity(i);
    }

    public class myWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            AllertError();
        }
    }
}
