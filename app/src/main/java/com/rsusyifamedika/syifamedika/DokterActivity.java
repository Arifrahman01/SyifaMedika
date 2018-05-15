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

public class DokterActivity extends AppCompatActivity {
    private WebView WebDokter;
    private ProgressBar BarDokter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);

        mAuth = FirebaseAuth.getInstance();
        WebDokter = (WebView) findViewById(R.id.webDokter);
        BarDokter = (ProgressBar) findViewById(R.id.pbDokter);
        WebDokter.setWebViewClient(new mWebViewClient());
        WebDokter.loadUrl("http://rsusyifamedika.com/dokter-spesialis-rawat-inap");

        if (mAuth.getCurrentUser() == null) {
            AllertKeluatDokter();
        }
    }
    private void AllertKeluatDokter() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder
                .setMessage("Silahkan Login Terlibih Dahulu")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(DokterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (WebDokter.canGoBack()) {
            WebDokter.goBack();
        }

        super.onBackPressed();
    }

    public class mWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BarDokter.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            AllertErrorDoter();
        }
    }
    private void AllertErrorDoter() {
        AlertDialog.Builder adialog = new AlertDialog.Builder(this);
        adialog.setTitle("Error");
        adialog
                .setMessage("Periksa Koneksi Internet Anda ...")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(DokterActivity.this, DrawerActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = adialog.create();
        alertDialog.show();
    }
}
