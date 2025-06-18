package com.example.myapp2;

import android.content.Intent;               // Para cambiar de Activity
import android.content.SharedPreferences;    // Para guardar datos
import android.os.Bundle;                    // Para el estado de la Activity
import android.view.View;                    // Para manejar eventos de la vista
import android.widget.Button;                // Para el boton
import android.widget.EditText;              // Para los campos de texto
import android.widget.Toast;                 // Para mostrar mensajes emergentes

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNombre, etEmail, etPassword;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se carga el layout activity_registro.xml
        setContentView(R.layout.activity_registro);

        // Se vinculan los componentes del layout con sus respectivas variables
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Se establece el listener para el boton de registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se obtienen y limpian los textos ingresados
                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Se verifica que ningun campo este vacio
                if(nombre.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegistroActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Se guardan los datos en SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("MisDatos", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nombre", nombre);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();

                    // Se inicia LoginActivity y se finaliza RegistroActivity
                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
