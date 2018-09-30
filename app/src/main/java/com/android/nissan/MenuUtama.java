package com.android.nissan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuUtama extends AppCompatActivity {

    Button btnProfil,btnHasil,btnDiagnosis,btnLogout;
    SharedPreferences spLogin;
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Beranda");
        }

        db = new DB(this);

        spLogin = getSharedPreferences(StaticVars.SP_LOGIN, MODE_PRIVATE);
        btnProfil = findViewById(R.id.btnProfile);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUtama.this, InputKendaraan.class));
            }
        });
        btnHasil = findViewById(R.id.btnHslDiagnosa);
        btnHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDiagnosis = findViewById(R.id.btnDiagnosa);
        btnDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Apa benar anda ingin Logout ?");
        alertDialogBuilder
                .setMessage("Klik Ya untuk logout!")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        SharedPreferences.Editor loginEditor = spLogin.edit();
                        loginEditor.clear();
                        loginEditor.apply();
                        finish();
                        startActivity(new Intent(MenuUtama.this,Login.class));
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
