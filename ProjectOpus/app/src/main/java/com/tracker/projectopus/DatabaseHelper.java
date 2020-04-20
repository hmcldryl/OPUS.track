package com.tracker.projectopus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "";
    public static final String TABLE_NAME = "PH_CASE_DATA";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "RECOVERED";
    public static final String COL_4 = "CONFIRMED";
    public static final String COL_5 = "DEATHS";

    private static final String createTable = ("CREATE TABLE " + TABLE_NAME +
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DATE TEXT, " +
            "RECOVERED INTEGER, " +
            "CONFIRMED INTEGER, " +
            "DEATHS INTEGER) ");
    private static final String dropTable = ("DROP TABLE IF EXISTS " + TABLE_NAME);
    private static final String getTableData = ("SELECT * FROM " + TABLE_NAME);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable);
    }

    public boolean insertData (String date, int recovered, int confirmed, int deaths) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, recovered);
        contentValues.put(COL_4, confirmed);
        contentValues.put(COL_5, deaths);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(getTableData, null);
    }
}
