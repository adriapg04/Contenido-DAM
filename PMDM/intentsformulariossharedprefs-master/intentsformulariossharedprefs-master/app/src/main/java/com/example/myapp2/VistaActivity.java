package com.example.myapp2;

import android.content.SharedPreferences;    // Para leer datos guardados
import android.os.Bundle;                    // Para el estado de la Activity
import android.widget.TextView;              // Para mostrar el mensaje

import androidx.appcompat.app.AppCompatActivity;

public class VistaActivity extends AppCompatActivity {
    private TextView tvSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se carga el layout activity_vista.xml
        setContentView(R.layout.activity_vista);

        // Se vincula el TextView del layout
        tvSaludo = findViewById(R.id.tvSaludo);

        // Se obtienen los datos guardados en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MisDatos", MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");

        // Se muestra el saludo con el nombre registrado
        tvSaludo.setText("Hola, " + nombre + "!");
    }
}
