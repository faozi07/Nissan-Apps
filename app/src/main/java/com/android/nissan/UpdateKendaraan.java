package com.android.nissan;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateKendaraan extends AppCompatActivity {

    EditText editNopol, editMerk;
    Spinner spinStatus;
    Button btnUbah;
    ArrayAdapter<String> adapterNopol;
    DB db;
    String[] arrStatus;
    AlertDialog.Builder dialogs;
    AlertDialog dialogListNopol;
    static ArrayList<String> listClone = new ArrayList<>();
    public static boolean suksesUpdate = false;
    public static String merek = "";
    public static String status = "";
    public static ArrayList<String> arrNopol = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_kendaraan);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ubah Status");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();
        action();
    }

    private void init() {
        arrStatus = getResources().getStringArray(R.array.arrStatus);
        if (arrNopol.size() > 0) {
            arrNopol.clear();
        }
        db = new DB(this);
        db.listNopol();
        editNopol = findViewById(R.id.editNopol);
        editMerk = findViewById(R.id.editMerk);
        spinStatus = findViewById(R.id.spinStatus);
        btnUbah = findViewById(R.id.btnUbah);
        dialogs = new AlertDialog.Builder(UpdateKendaraan.this);
        dialogListNopol = dialogs.create();
    }


    private void action() {
        cekTeksCari();
        editNopol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(UpdateKendaraan.this);
                dialogListNopol();
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(UpdateKendaraan.this);
                db.updateKendaraan(editNopol.getText().toString(), spinStatus.getSelectedItem().toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (suksesUpdate) {
                            finish();
                            suksesUpdate = false;
                            Toast.makeText(UpdateKendaraan.this,"Berhasil ubah status",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateKendaraan.this,"Gagal ubah status",Toast.LENGTH_LONG).show();
                        }
                    }
                },2000);
            }
        });
    }
    private void cekTeksCari() {
        TextWatcher watcher = new TextWatcher() {
            int mStart = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStart = start + count;
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                String capitalizedText;
                if (input.length() < 1)
                    capitalizedText = input;
                else if (input.length() > 1 && input.contains(" ")) {
                    String fstr = input.substring(0, input.lastIndexOf(" ") + 1);
                    if (fstr.length() == input.length()) {
                        capitalizedText = fstr;
                    } else {
                        String sstr = input.substring(input.lastIndexOf(" ") + 1);
                        sstr = sstr.substring(0, 1).toUpperCase() + sstr.substring(1).toUpperCase();
                        capitalizedText = fstr + sstr;
                    }
                } else
                    capitalizedText = input.substring(0, 1).toUpperCase() + input.substring(1).toUpperCase();

                if (!capitalizedText.equals(editNopol.getText().toString())) {
                    editNopol.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editNopol.setSelection(mStart);
                            editNopol.removeTextChangedListener(this);
                        }
                    });
                    editNopol.setText(capitalizedText);
                }
            }
        };
        editNopol.addTextChangedListener(watcher);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        SoundBtn.soundBtn(UpdateKendaraan.this);
        finish();
    }

    // ========================== Dialog List Nopol =============================
    @SuppressLint("InflateParams")
    private void dialogListNopol() {
        LayoutInflater inflater;
        View dialog_layout;
        inflater = LayoutInflater.from(UpdateKendaraan.this);
        dialog_layout = inflater.inflate(R.layout.dialog_list_kendaraan, null);
        Button btnClose = dialog_layout.findViewById(R.id.btnClose);
        final ListView listNopol = dialog_layout.findViewById(R.id.listNopol);
        adapterNopol = new ArrayAdapter<>(UpdateKendaraan.this,
                android.R.layout.simple_list_item_1, arrNopol);
        listNopol.setAdapter(adapterNopol);

        dialogs.setView(dialog_layout);
        dialogs.setCancelable(true);
        dialogListNopol = dialogs.create();
        if (!dialogListNopol.isShowing()) {
            dialogListNopol.show();
        }

        listNopol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SoundBtn.soundBtn(UpdateKendaraan.this);
                db.listKendaraanByNopol(listNopol.getItemAtPosition(position).toString());
                for (int i=0; i<arrStatus.length;i++) {
                    if (arrStatus[i].equals(status)) {
                        spinStatus.setSelection(i);
                        editNopol.setText(arrNopol.get(position));
                        editMerk.setText(merek);

                        editNopol.setSelection(editNopol.getText().length());
                        editMerk.setSelection(editMerk.getText().length());
                        dialogListNopol.dismiss();
                    }
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(UpdateKendaraan.this);
                dialogListNopol.dismiss();
            }
        });
    }
}
