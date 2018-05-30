package com.rsusyifamedika.syifamedika;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rsusyifamedika.syifamedika.Daftar.MenuDaftarActivity;
import com.rsusyifamedika.syifamedika.Daftar.MenuLoginActivity;
import com.rsusyifamedika.syifamedika.Poliklinik.PemesananActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MenuLoginActivity.class));
//                            MenuLoginActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
