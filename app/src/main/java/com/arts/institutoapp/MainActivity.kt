package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los campos de texto y botón
        val userIdField = findViewById<EditText>(R.id.editTextUserID)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        // Datos de usuario estáticos para simular la autenticación
        val validUserId = "alumno123"
        val validPassword = "12345"

        // Listener para el botón de inicio de sesión
        loginButton.setOnClickListener {
            val enteredUserId = userIdField.text.toString()
            val enteredPassword = passwordField.text.toString()

            if (enteredUserId == validUserId && enteredPassword == validPassword) {
                // Login exitoso
                Toast.makeText(this, "Login Exitoso", Toast.LENGTH_SHORT).show()

                // Redirigir a WelcomeActivity con el nombre del usuario
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("USERNAME", enteredUserId) // Enviar el nombre de usuario
                startActivity(intent)

                // Opcional: Cerrar la actividad actual para que no pueda volver al login con el botón de retroceso
                finish()
            } else {
                // Login fallido
                Toast.makeText(this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
