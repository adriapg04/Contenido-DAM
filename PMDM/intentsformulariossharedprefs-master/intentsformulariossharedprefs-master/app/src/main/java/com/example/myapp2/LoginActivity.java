package com.example.myapp2;

import android.content.Intent;               // Para cambiar de Activity
import android.content.SharedPreferences;    // Para leer datos guardados
import android.os.Bundle;                    // Para el estado de la Activity
import android.view.View;                    // Para manejar eventos de la vista
import android.widget.Button;                // Para los botones
import android.widget.EditText;              // Para los campos de texto
import android.widget.Toast;                 // Para mostrar mensajes emergentes

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, btnIrRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se carga el layout activity_login.xml
        setContentView(R.layout.activity_login);

        // Se vinculan los elementos del layout
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        // Se obtienen los datos almacenados en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MisDatos", MODE_PRIVATE);
        final String storedEmail = prefs.getString("email", null);
        final String storedPassword = prefs.getString("password", null);

        // Si no hay datos guardados, se muestra el boton para ir a Registro
        if(storedEmail == null || storedPassword == null) {
            btnIrRegistro.setVisibility(View.VISIBLE);
        } else {
            btnIrRegistro.setVisibility(View.GONE);
        }

        // Listener para el boton de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = etEmail.getText().toString().trim();
                String passwordInput = etPassword.getText().toString().trim();

                // Se verifica que los campos no esten vacios
                if(emailInput.isEmpty() || passwordInput.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Se comparan los datos ingresados con los guardados
                    if(storedEmail != null && storedPassword != null &&
                            emailInput.equals(storedEmail) && passwordInput.equals(storedPassword)){
                        // Si coinciden, se inicia VistaActivity
                        Intent intent = new Intent(LoginActivity.this, VistaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Listener para el boton que redirige a RegistroActivity en caso de no haber datos
        btnIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
