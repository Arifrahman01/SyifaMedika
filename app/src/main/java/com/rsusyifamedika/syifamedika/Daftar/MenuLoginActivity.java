package com.rsusyifamedika.syifamedika.Daftar;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rsusyifamedika.syifamedika.DrawerActivity;
import com.rsusyifamedika.syifamedika.LengkapiDataActivity;
import com.rsusyifamedika.syifamedika.R;

public class MenuLoginActivity extends Activity {
    private FirebaseAuth mAuth;
    private Button mbtMenuMasuk,btMenuLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (mAuth.getCurrentUser() != null){
            Intent i = new Intent(MenuLoginActivity.this, DrawerActivity.class);
            startActivity(i);
        }

        findViewById(R.id.btMenuMasukk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuLoginActivity.this, MenuMasukActivity.class);
                startActivity(i);

            }
        });
        findViewById(R.id.btMenuDaftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuLoginActivity.this, MenuDaftarActivity.class);
                startActivity(i);
            }
        });
    }
}
