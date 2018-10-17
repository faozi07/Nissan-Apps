package com.android.nissan;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText editNik, editPassword, editNama;
    Button btnRegister;
    public static boolean isRegistered = false, registerSukses = false;
    DB DB = null;

    @Override
    protected void onResume() {
        super.onResume();
        isRegistered = false;
        registerSukses = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DB = new DB(this);
        final SQLiteDatabase sqlDb = DB.getWritableDatabase();
        DB.onCreate(sqlDb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        editNama = findViewById(R.id.nama);
        editPassword = findViewById(R.id.textPassword);
        editNik = findViewById(R.id.nik);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(Register.this);
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }

                if (editNama.getText().toString().equals("") || editPassword.getText().toString().equals("") ||
                        editNik.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Isi data dengan lengkap", Toast.LENGTH_LONG).show();
                } else {
                    DB.cekUser(editNik.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                    progressDialog.setMessage("Mendaftar ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRegistered) {
                                Toast.makeText(Register.this, "User sudah terdaftar, gunakan data user lain", Toast.LENGTH_LONG).show();
                            } else {
                                DB.insertUser(editNik.getText().toString(), editPassword.getText().toString(), editNama.getText().toString());
                            }
                        }
                    }, 2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (registerSukses) {
                                finish();
                                Toast.makeText(Register.this, "Berhasil Daftar, silahkan login", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Register.this, "Gagal Daftar, silahkan coba lagi", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 4000);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        SoundBtn.soundBtn(Register.this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
