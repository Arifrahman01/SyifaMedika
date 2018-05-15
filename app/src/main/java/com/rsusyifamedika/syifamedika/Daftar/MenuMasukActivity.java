package com.rsusyifamedika.syifamedika.Daftar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rsusyifamedika.syifamedika.R;

public class MenuMasukActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_masuk);
        findViewById(R.id.btMasukdenganEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuMasukActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btLoginNophone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuMasukActivity.this, LoginNoPhoneActivity.class);
                startActivity(i);
            }
        });

    }
}
