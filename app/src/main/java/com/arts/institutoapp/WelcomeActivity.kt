package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Obtener el texto de bienvenida y establecer el nombre de usuario
        val welcomeText = findViewById<TextView>(R.id.welcomeTextView)
        val userName = intent.getStringExtra("USERNAME") // Cambiado USER_ID a USERNAME

        // Verificar si se obtuvo el nombre de usuario correctamente
        if (userName != null) {
            welcomeText.text = "Bienvenido $userName"
        } else {
            welcomeText.text = "Error: Nombre de usuario no disponible"
        }

        // Configuración del botón de calendario
        val calendarButton = findViewById<ImageButton>(R.id.buttonCalendar)

        // Listener para el botón de calendario
        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        // Referencia al botón de Notas
        val buttonNotas: ImageButton = findViewById(R.id.buttonNotas) // Asegúrate de que este ID sea correcto

        // Configurar el listener para el botón de Notas
        buttonNotas.setOnClickListener {
            // Intent para abrir NotasActivity
            val intent = Intent(this, NotasActivity::class.java)
            startActivity(intent)
        }


        val buttonEvaluacion: ImageButton = findViewById(R.id.buttonEvaluacion)
        buttonEvaluacion.setOnClickListener {
            val intent = Intent(this, EvaluacionActivity::class.java)
            startActivity(intent)
        }

    }
}
