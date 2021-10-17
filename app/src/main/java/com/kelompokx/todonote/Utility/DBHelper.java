package com.kelompokx.todonote.Utility;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kelompokx.todonote.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME_TDL = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID_TDL = "id";
    private static final String TUGAS = "tugas";
    private static final String STATUS_TDL = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID_TDL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TUGAS + " TEXT, " + STATUS_TDL + " INTEGER)";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, NAME_TDL, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTugas(ToDoModel tugas){
        ContentValues cv = new ContentValues();
        cv.put(TUGAS, tugas.getTugas());
        cv.put(STATUS_TDL, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTugas() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel tugas = new ToDoModel();
                        tugas.setId(cur.getInt(cur.getColumnIndex(ID_TDL)));
                        tugas.setTugas(cur.getString(cur.getColumnIndex(TUGAS)));
                        tugas.setStatus(cur.getInt(cur.getColumnIndex(STATUS_TDL)));
                        taskList.add(tugas);
                    }
                    while (cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }
    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS_TDL, status);
        db.update(TODO_TABLE, cv, ID_TDL + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTugas(int id, String tugas){
        ContentValues cv = new ContentValues();
        cv.put(TUGAS, tugas);
        db.update(TODO_TABLE, cv, ID_TDL + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTugas(int id){
        db.delete(TODO_TABLE, ID_TDL + "=?", new String[] {String.valueOf(id)});
    }


}
