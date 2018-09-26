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

public class UserDB extends SQLiteOpenHelper {

    // ===================================== TAMBAH KOMODITAS LAHAN ================================
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // ================================================ LAHAN ======================================
    private static final String TABLE_NAME_USER = "nisanUser";
    private static final String NIK = "NIK";
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " (" + NIK + " INTEGER PRIMARY KEY, " + NAME + " TEXT null, " +
                    PASSWORD + " TEXT null);";
            Log.d("DataKomoditi ", "onCreate: " + sql);
            db.execSQL(sql);
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

}
