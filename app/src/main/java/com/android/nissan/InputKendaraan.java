package com.android.nissan;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputKendaraan extends AppCompatActivity {

    Calendar myCalendar;
    EditText editTgl, editNopol, editJanji, editJenisKerja;
    Spinner spinMerk;
    Button btnSimpan;

    String tgl = "";
    public static boolean berhasilAdd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_kendaraan);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Input Kendaraan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initial();
        action();
        myCalendar = Calendar.getInstance();
    }

    private void initial() {
        editTgl = findViewById(R.id.editTgl);
        editNopol = findViewById(R.id.editNopol);
        editJanji = findViewById(R.id.editJanji);
        editJenisKerja = findViewById(R.id.editJenis);
        spinMerk = findViewById(R.id.spinMerek);
        btnSimpan = findViewById(R.id.btnSimpan);
    }

    private void action() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tgl = sdf.format(myCalendar.getTime());
                timerPicker(false);
            }
        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tgl = sdf.format(myCalendar.getTime());
                timerPicker(true);
            }
        };

        editTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                // TODO Auto-generated method stub
                DatePickerDialog mDate = new DatePickerDialog(InputKendaraan.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });

        editJanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                // TODO Auto-generated method stub
                DatePickerDialog mDate = new DatePickerDialog(InputKendaraan.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(InputKendaraan.this);
                final ProgressDialog progressDialog = new ProgressDialog(InputKendaraan.this);
                progressDialog.setMessage("Input Kendaraan");
                progressDialog.setCancelable(false);
                progressDialog.show();
                DB db = new DB(InputKendaraan.this);
                db.insertKendaraan(editTgl.getText().toString(), spinMerk.getSelectedItem().toString(), editNopol.getText().toString(), "New",
                        editJanji.getText().toString(), editJenisKerja.getText().toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (berhasilAdd) {
                            finish();
                            Toast.makeText(InputKendaraan.this, "Berhasil input data", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(InputKendaraan.this, "Gagal input data, silakan coba kembali", Toast.LENGTH_LONG).show();
                        }
                    }
                },2000);
            }
        });
    }

    private void timerPicker (final boolean isFromJanji) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(InputKendaraan.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (String.valueOf(selectedHour).length()>1 && String.valueOf(selectedMinute).length()<=1) {
                    if (isFromJanji) {
                        editJanji.setText( tgl +" pukul: "+ selectedHour + ".0" + selectedMinute);
                    } else {
                        editTgl.setText( tgl +" pukul: "+ selectedHour + ".0" + selectedMinute);
                    }
                } else if (String.valueOf(selectedHour).length()<=1 && String.valueOf(selectedMinute).length()>1) {
                    if (isFromJanji) {
                        editJanji.setText( tgl +" pukul: 0"+ selectedHour + "." + selectedMinute);
                    } else {
                        editTgl.setText( tgl +" pukul: 0"+ selectedHour + "." + selectedMinute);
                    }
                } else {
                    if (isFromJanji) {
                        editJanji.setText( tgl +" pukul: "+ selectedHour + "." + selectedMinute);
                    } else {
                        editTgl.setText( tgl +" pukul: "+ selectedHour + "." + selectedMinute);
                    }
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Pilih Waktu");
        mTimePicker.show();

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        SoundBtn.soundBtn(InputKendaraan.this);
        finish();
    }
}
