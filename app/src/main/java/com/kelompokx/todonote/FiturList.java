package com.kelompokx.todonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompokx.todonote.Adapter.ToDoAdapter;
import com.kelompokx.todonote.Model.ToDoModel;
import com.kelompokx.todonote.Utility.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiturList extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tugasAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_list);
        getSupportActionBar();

        db = new DBHelper(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tugasAdapter = new ToDoAdapter(db, FiturList.this);
        tasksRecyclerView.setAdapter(tugasAdapter);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tugasAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTugas();
        Collections.reverse(taskList);
        tugasAdapter.setTugas((taskList));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahNewTask.newInstance().show(getSupportFragmentManager(), TambahNewTask.TAG);
            }
        });
    }

    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTugas();
        Collections.reverse(taskList);
        tugasAdapter.setTugas(taskList);
        tugasAdapter.notifyDataSetChanged();
    }
}