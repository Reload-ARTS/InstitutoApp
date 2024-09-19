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
    }
}
