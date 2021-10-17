package com.kelompokx.todonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnMenuNote;
    private Button btnMenuList;
    private Button btnMenuCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    btnMenuNote = (Button)findViewById(R.id.menuNote);
    btnMenuNote.setOnClickListener(this);
    btnMenuList = (Button)findViewById(R.id.menuList);
    btnMenuList.setOnClickListener(this);
    btnMenuCalendar = (Button)findViewById(R.id.menuCalendar);
    btnMenuCalendar.setOnClickListener(this);

    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menuNote:
                Intent moveNote = new Intent(MainActivity.this, FiturNote.class);
                startActivity(moveNote);
                break;
            case R.id.menuList:
                Intent moveList = new Intent(MainActivity.this, FiturList.class);
                startActivity(moveList);
                break;
            case R.id.menuCalendar:
                Intent moveCalendar = new Intent(MainActivity.this, FiturCalendar.class);
                startActivity(moveCalendar);
                break;
        }
    }
}