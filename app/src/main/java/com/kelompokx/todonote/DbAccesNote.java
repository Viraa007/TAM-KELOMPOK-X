package com.kelompokx.todonote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbAccesNote {
    private SQLiteDatabase database;
    private DatabaseNote openHelper;
    private static volatile DbAccesNote instance;

    private DbAccesNote(Context context){
        this.openHelper = new DatabaseNote(context);
    }

    public static synchronized DbAccesNote getInstance(Context context){
        if (instance == null){
            instance = new DbAccesNote(context);
        }
        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if (database != null){
            this.database.close();
        }
    }

    public void save(Memo memo){
        ContentValues values = new ContentValues();
        values.put("date", memo.getTime());
        values.put("memo", memo.getText());
        database.insert(DatabaseNote.TABLE,null,values);
    }

    public void update(Memo memo){
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("memo", memo.getText());
        String date = Long.toString(memo.getTime());
        database.update(DatabaseNote.TABLE,values, "date = ?", new String[]{date});
    }

    public void delete(Memo memo){
        String date = Long.toString(memo.getTime());
        database.delete(DatabaseNote.TABLE, "date = ?", new String[]{date});
    }

    public List getAllMemos(){
        List memos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From memo ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            memos.add(new Memo(time, text));
            cursor.moveToNext();
        }

        cursor.close();
        return memos;
    }
}