package com.sisteminformasi.gigtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gigtrack.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LATIHAN = "tb_latihan";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_JUDUL = "judul";
    public static final String COLUMN_ARTIS = "artis";
    public static final String COLUMN_CATATAN = "catatan";
    public static final String COLUMN_DURASI = "durasi";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_LATIHAN + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_JUDUL + " TEXT, " +
                    COLUMN_ARTIS + " TEXT, " +
                    COLUMN_CATATAN + " TEXT, " +
                    COLUMN_DURASI + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LATIHAN);
        onCreate(db);
    }

    // Method to add training session
    public long insertLatihan(String judul, String artis, String catatan, String durasi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JUDUL, judul);
        values.put(COLUMN_ARTIS, artis);
        values.put(COLUMN_CATATAN, catatan);
        values.put(COLUMN_DURASI, durasi);
        return db.insert(TABLE_LATIHAN, null, values);
    }

    // Method to get all sessions
    public Cursor getAllLatihan() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LATIHAN + " ORDER BY " + COLUMN_ID + " DESC", null);
    }
}
