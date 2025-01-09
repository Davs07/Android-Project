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
    private final int[] imagenes = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6};
    private int[] tablero = new int[12];
    private ImageView[] celdas = new ImageView[12];
    private int[] seleccion = {-1, -1}; // Almacena los índices seleccionados
    private boolean bloqueo = false; // Para evitar clicks durante la verificación
    private int paresAcertados = 0;
    private int intentos = 0;
    private final List<Juego> juegos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria);

        tb_memoria = findViewById(R.id.tb_memoria);
        setSupportActionBar(tb_memoria);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GridLayout grid = findViewById(R.id.gridJuego);

        // Inicializar las celdas del tablero
        for (int i = 0; i < grid.getChildCount(); i++) {
            celdas[i] = (ImageView) grid.getChildAt(i);
            celdas[i].setTag(i); // Asignar el índice como tag
            celdas[i].setOnClickListener(this::manejarClickImagen);
        }

        inicializarJuego();

        // Botón para iniciar un nuevo juego
        Button btnNuevoJuego = findViewById(R.id.btnNuevoJuego);
        btnNuevoJuego.setOnClickListener(v -> inicializarJuego());

        // Botón para ver el listado de juegos
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
        bloqueo = false;

        // Mezclar imágenes y asignarlas al tablero
        List<Integer> imagenesList = new ArrayList<>();
        for (int imagen : imagenes) {
            imagenesList.add(imagen);
            imagenesList.add(imagen);
        }
        Collections.shuffle(imagenesList);

        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = imagenesList.get(i);
        }

        // Reiniciar celdas
        for (ImageView celda : celdas) {
            celda.setImageResource(R.drawable.carta_volteada);
            celda.setEnabled(true);
        }
    }

    private void manejarClickImagen(View view) {
        if (bloqueo) return; // Evitar clicks si el tablero está bloqueado

        ImageView img = (ImageView) view;
        int index = (int) img.getTag();

        img.setImageResource(tablero[index]); // Mostrar la imagen seleccionada
        img.setEnabled(false); // Deshabilitar la celda seleccionada

        if (seleccion[0] == -1) {
            // Primera selección
            seleccion[0] = index;
        } else {
            // Segunda selección
            seleccion[1] = index;
            verificarPareja();
        }
    }

    private void verificarPareja() {
        bloqueo = true; // Bloquear el tablero durante la verificación
        intentos++;

        // Verificar si las imágenes seleccionadas coinciden
        if (tablero[seleccion[0]] == tablero[seleccion[1]]) {
            paresAcertados++;
            Toast.makeText(this, "¡Acertaste!", Toast.LENGTH_SHORT).show();

            // Verificar si se han encontrado todos los pares
            if (paresAcertados == imagenes.length) {
                juegos.add(new Juego(juegos.size() + 1, intentos));
                Toast.makeText(this, "¡Ganaste el juego!", Toast.LENGTH_LONG).show();
            }

            desbloquearTablero();
        } else {
            // Si no coinciden, ocultar las imágenes después de 1 segundo
            new Handler().postDelayed(() -> {
                celdas[seleccion[0]].setImageResource(R.drawable.carta_volteada);
                celdas[seleccion[1]].setImageResource(R.drawable.carta_volteada);
                celdas[seleccion[0]].setEnabled(true);
                celdas[seleccion[1]].setEnabled(true);
                desbloquearTablero();
            }, 1000);
        }
    }

    private void desbloquearTablero() {
        seleccion[0] = -1;
        seleccion[1] = -1;
        bloqueo = false; // Desbloquear el tablero
    }
}
