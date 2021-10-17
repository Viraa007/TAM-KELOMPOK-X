package com.kelompokx.todonote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FiturNote extends AppCompatActivity {

    private ListView listView;
    private DbAccesNote databaseAcces;
    private List<Memo> memos;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_note);
        getSupportActionBar();

        databaseAcces = DbAccesNote.getInstance(this);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(FiturNote.this, EditNote.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAcces.open();
        this.memos = databaseAcces.getAllMemos();
        databaseAcces.close();
        MemoAdapter adapter = new MemoAdapter(this,memos);
        this.listView.setAdapter(adapter);
    }


    private class MemoAdapter extends ArrayAdapter<Memo>{

        MemoAdapter(@NonNull Context context, List<Memo> objects) {
            super(context, 0,  objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.layout_list_note, parent, false);
            }

            Button btnEdit = convertView.findViewById(R.id.btnEdit);
            ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

            TextView isiDate =  convertView.findViewById(R.id.isiDate);
            TextView isiNote = convertView.findViewById(R.id.isiNote);

            final Memo memo = memos.get(position);
            isiDate.setText(memo.getDate());
            isiNote.setText(memo.getShortText());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FiturNote.this, EditNote.class);
                    intent.putExtra("MEMO", memo);
                    startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseAcces.open();
                    databaseAcces.delete(memo);
                    databaseAcces.close();

                    ArrayAdapter<Memo> adapter = (ArrayAdapter<Memo>) listView.getAdapter();
                    adapter.remove(memo);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}