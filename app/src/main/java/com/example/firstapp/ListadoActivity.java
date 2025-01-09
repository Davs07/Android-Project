// app/src/main/java/com/example/firstapp/ListadoActivity.java
package com.example.firstapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import models.Juego;

public class ListadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

         @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = findViewById(R.id.listView);
        ArrayList<Juego> juegos = getIntent().getParcelableArrayListExtra("juegos");

        ArrayAdapter<Juego> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, juegos);
        listView.setAdapter(adapter);
    }
}