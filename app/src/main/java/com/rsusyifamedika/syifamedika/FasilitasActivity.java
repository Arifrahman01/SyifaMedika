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

public class FasilitasActivity extends AppCompatActivity {
    private WebView mwebViewLokasi;
    private ProgressBar mpbLokasi;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);
        mAuth = FirebaseAuth.getInstance();
        mwebViewLokasi = (WebView) findViewById(R.id.webFasilitas);
        mpbLokasi= (ProgressBar) findViewById(R.id.pbFasilitas);
//        mwebViewLokasi.getSettings().setJavaScriptEnabled(false);
        mwebViewLokasi.setWebViewClient(new myWebViewClient());
        mwebViewLokasi.loadUrl("http://rsusyifamedika.com/fasilitas/");

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
                        Intent i = new Intent(FasilitasActivity.this, LoginActivity.class);
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
                        Intent i = new Intent(FasilitasActivity.this, DrawerActivity.class);
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
