package com.kelompokx.todonote.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.kelompokx.todonote.FiturList;
import com.kelompokx.todonote.Model.ToDoModel;
import com.kelompokx.todonote.R;
import com.kelompokx.todonote.TambahNewTask;
import com.kelompokx.todonote.Utility.DBHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todoList;
    private FiturList activity;
    private DBHelper db;

    public ToDoAdapter(DBHelper db, FiturList activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();

        final ToDoModel item = todoList.get(position);
        holder.tugas.setText(item.getTugas());
        holder.tugas.setChecked(toBoolean(item.getStatus()));
        holder.tugas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else{
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount() {

        return todoList.size();
    }

    private boolean toBoolean(int n) {

        return n != 0;
    }

    public void setTugas(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTugas(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("tugas", item.getTugas());
        TambahNewTask fragment = new TambahNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), TambahNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox tugas;

        ViewHolder(View view) {
            super(view);
            tugas = view.findViewById(R.id.kotakChecklist);
        }
    }

}
