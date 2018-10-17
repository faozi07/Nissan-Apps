package com.android.nissan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText editNik, editPassword;
    DB DB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DB = new DB(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        editNik = findViewById(R.id.nik);
        editPassword = findViewById(R.id.textPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(Login.this);
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                if (editNik.getText().toString().equals("") || editPassword.getText().toString().equals("")) {
                    Toast.makeText(Login.this, "Isi data dengan lengkap", Toast.LENGTH_LONG).show();
                } else {
                    DB.login(editNik.getText().toString(), editPassword.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("Login ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            cekDataUser();
                        }
                    }, 3000);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(Login.this);
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void cekDataUser() {
        if (StaticVars.isTerdaftar) {
            finish();
            SharedPreferences spLogin = getSharedPreferences(StaticVars.SP_LOGIN, MODE_PRIVATE);
            SharedPreferences.Editor loginEditor = spLogin.edit();
            loginEditor.putString(StaticVars.SP_LOGIN_NIK, StaticVars.nik);
            loginEditor.putString(StaticVars.SP_LOGIN_NAMA, StaticVars.nama);
            loginEditor.apply();
            startActivity(new Intent(Login.this, MenuUtama.class));
        } else {
            Toast.makeText(Login.this, "Login gagal, silahkan coba lagi", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        SoundBtn.soundBtn(Login.this);
        finish();
    }
}
