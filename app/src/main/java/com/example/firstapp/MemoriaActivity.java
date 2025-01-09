package com.example.firstapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MemoriaActivity extends AppCompatActivity {

    Toolbar tb_memoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria);

        tb_memoria = findViewById(R.id.tb_memoria);
        setSupportActionBar(tb_memoria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}