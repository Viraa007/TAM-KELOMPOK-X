package com.kelompokx.todonote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseNote extends SQLiteOpenHelper {
    public static final String DATABASE = "note.db";
    public static final String TABLE = "memo";
    public static final int VERSION = 1;

    public DatabaseNote( Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memo(date INTEGER PRIMARY KEY, memo TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
