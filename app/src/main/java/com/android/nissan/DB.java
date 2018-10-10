package com.android.nissan;

/*
 * Created by faozi on 14/03/18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // ================================================ USER ======================================
    private static final String TABLE_NAME_USER = "nisanUser";
    private static final String NIK = "NIK";
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";

    // ================================================ KENDARAAN ======================================
    private static final String TABLE_NAME_KENDARAAN = "nisanVehicle";
    private static final String TGL_INPUT = "TGL_INPUT";
    private static final String MEREK = "MEREK";
    private static final String NOPOL = "NOPOL";
    private static final String STATUS = "STATUS";
    private static final String JANJI_SELESAI = "JANJI_SELESAI";
    private static final String JENIS_KERJA = "JENIS_KERJA";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " (" + NIK + " INTEGER PRIMARY KEY, " + NAME + " TEXT null, " +
                    PASSWORD + " TEXT null);";
            Log.d("DataKomoditi ", "onCreate: " + sql);

            String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_KENDARAAN + " (" + TGL_INPUT + " TEXT NULL, " +
                    MEREK + " TEXT NULL, " + NOPOL + " TEXT NULL, " + STATUS + " TEXT NULL, " + JANJI_SELESAI + " TEXT NULL, " +
                    JENIS_KERJA +" TEXT NOT NULL );";

            Log.d("DataKomoditi ", "onCreate: " + sql2);
            db.execSQL(sql);
            db.execSQL(sql2);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);
    }

    public void dropTable() {
        try {
            SQLiteDatabase database = getWritableDatabase();
            String updateQuery = "DROP TABLE IF EXISTS " + TABLE_NAME_USER;
            database.execSQL(updateQuery);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertUser(String nik, String password, String nama) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "INSERT INTO " + TABLE_NAME_USER + " (" + NIK + ", " + PASSWORD + ", "+NAME+") VALUES ('"
                    + nik + "', '" + password + "', '" + nama + "');";
            db.execSQL(sql);
            Register.registerSukses = true;
        } catch (Exception exp) {
            exp.printStackTrace();
            Register.registerSukses = false;
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void cekUser(String nik) {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT " + NIK +" FROM " + TABLE_NAME_USER +
                " WHERE " + NIK + "='" + nik + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Register.isRegistered = cursor.getString(0) != null && nik.equals(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void login(String nik, String password) {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_USER +
                " WHERE " + NIK + "='" + nik + "' AND "+PASSWORD+" = '"+password+"'", null);
        if (cursor.moveToFirst()) {
            do {
                StaticVars.isTerdaftar = cursor.getString(0) != null && nik.equals(cursor.getString(0));
                StaticVars.nik = cursor.getString(0);
                StaticVars.nama = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        db.close();
    }

    // ============================================== Kendaraan ==============================================
    public void insertKendaraan(String tglInput, String merek, String nopol, String status, String janjiSlsai, String jenisKerja) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "INSERT INTO " + TABLE_NAME_KENDARAAN + " (" + TGL_INPUT + ", " + MEREK + ", "+ NOPOL + ", "+ STATUS + ", "+
                    JANJI_SELESAI + ", "+ JENIS_KERJA +") VALUES ('"
                    + tglInput + "', '" + merek + "', '" + nopol + "', '" + status + "', '" + janjiSlsai + "', '" + jenisKerja + "');";
            db.execSQL(sql);
            InputKendaraan.berhasilAdd = true;
        } catch (Exception exp) {
            exp.printStackTrace();
            InputKendaraan.berhasilAdd = false;
        }
    }

    public void updateKendaraan(String nopol, String status) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "UPDATE " + TABLE_NAME_KENDARAAN + " SET "+ STATUS +"='"+ status +"' WHERE "+ NOPOL +"='" + nopol + "';";
            db.execSQL(sql);
            UpdateKendaraan.suksesUpdate = true;
        } catch (Exception exp) {
            exp.printStackTrace();
            UpdateKendaraan.suksesUpdate = false;
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void cekKendaraan() {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT "+ NOPOL +" FROM " + TABLE_NAME_KENDARAAN, null);
        if (cursor.moveToFirst()) {
            do {
//                modKend.setNopol(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        db.close();
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void listKendaraanByNopol(String nopol) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_KENDARAAN + " WHERE "+ NOPOL + "='"+ nopol + "'", null);
        if (cursor.moveToFirst()) {
            do {
                UpdateKendaraan.merek = cursor.getString(1);
                UpdateKendaraan.status = cursor.getString(3);
            } while (cursor.moveToNext());
        }
        db.close();
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void listKendaraan() {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_KENDARAAN, null);
        if (cursor.moveToFirst()) {
            do {
                modelKendaraan modKend = new modelKendaraan();
                modKend.setTglInput(cursor.getString(0));
                modKend.setMerek(cursor.getString(1));
                modKend.setNopol(cursor.getString(2));
                modKend.setStatus(cursor.getString(3));
                modKend.setTglJanji(cursor.getString(4));
                modKend.setJnsKerja(cursor.getString(5));

                ListKendaraan.arrKendaraan.add(modKend);
            } while (cursor.moveToNext());
        }
        db.close();
    }

    public void listNopol() {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_KENDARAAN, null);
        if (cursor.moveToFirst()) {
            do {
                UpdateKendaraan.arrNopol.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        db.close();
    }

}

