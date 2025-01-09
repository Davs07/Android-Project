// app/src/main/java/com/example/firstapp/MemoriaActivity.java
package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Juego;

public class MemoriaActivity extends AppCompatActivity {

    private Toolbar tb_memoria;
    private int[] imagenes = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6};
    private int[] tablero = new int[12];
    private ImageView[] celdas = new ImageView[12];
    private int[] imagenesSeleccionadas = new int[2];
    private int paresAcertados = 0;
    private int intentos = 0;
    private List<Juego> juegos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria);

        tb_memoria = findViewById(R.id.tb_memoria);
        setSupportActionBar(tb_memoria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayout grid = findViewById(R.id.gridJuego);

        // Inicializar celdas
        for (int i = 0; i < grid.getChildCount(); i++) {
            celdas[i] = (ImageView) grid.getChildAt(i);
            celdas[i].setTag(i);
            celdas[i].setOnClickListener(this::manejarClickImagen);
        }

        inicializarJuego();

        Button btnNuevoJuego = findViewById(R.id.btnNuevoJuego);
        btnNuevoJuego.setOnClickListener(v -> inicializarJuego());

        Button btnListado = findViewById(R.id.btnListado);
        btnListado.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListadoActivity.class);
            intent.putParcelableArrayListExtra("juegos", new ArrayList<>(juegos));
            startActivity(intent);
        });
    }

    private void inicializarJuego() {
        paresAcertados = 0;
        intentos = 0;
        List<Integer> imagenesList = new ArrayList<>();
        for (int imagen : imagenes) {
            imagenesList.add(imagen);
            imagenesList.add(imagen);
        }
        Collections.shuffle(imagenesList);
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = imagenesList.get(i);
        }
        for (ImageView celda : celdas) {
            celda.setImageResource(R.drawable.carta_volteada);
            celda.setEnabled(true);
        }
    }

    private void manejarClickImagen(View view) {
        ImageView img = (ImageView) view;
        int index = (int) img.getTag();
        img.setImageResource(tablero[index]);
        img.setEnabled(false);

        if (imagenesSeleccionadas[0] == 0) {
            imagenesSeleccionadas[0] = index;
        } else {
            imagenesSeleccionadas[1] = index;
            verificarPareja();
        }
    }

    private void verificarPareja() {
        intentos++;
        if (tablero[imagenesSeleccionadas[0]] == tablero[imagenesSeleccionadas[1]]) {
            paresAcertados++;
            Toast.makeText(this, "¡Acertaste!", Toast.LENGTH_SHORT).show();
            if (paresAcertados == 6) {
                juegos.add(new Juego(juegos.size() + 1, paresAcertados));
                Toast.makeText(this, "¡Ganaste el juego!", Toast.LENGTH_LONG).show();
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                celdas[imagenesSeleccionadas[0]].setImageResource(R.drawable.carta_volteada);
                celdas[imagenesSeleccionadas[1]].setImageResource(R.drawable.carta_volteada);
                celdas[imagenesSeleccionadas[0]].setEnabled(true);
                celdas[imagenesSeleccionadas[1]].setEnabled(true);
            }, 1000);
        }
        imagenesSeleccionadas[0] = 0;
        imagenesSeleccionadas[1] = 0;
    }
}