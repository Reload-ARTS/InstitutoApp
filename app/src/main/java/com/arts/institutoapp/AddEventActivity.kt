package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        val eventTitle = findViewById<EditText>(R.id.eventTitle)
        val eventDescription = findViewById<EditText>(R.id.eventDescription)
        val saveEventButton = findViewById<Button>(R.id.saveEventButton)

        // Recuperar la fecha seleccionada
        val date = intent.getStringExtra("selectedDate")

        saveEventButton.setOnClickListener {
            val title = eventTitle.text.toString().trim()
            val description = eventDescription.text.toString().trim()

            // Validar entradas
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (date == null) {
                Toast.makeText(this, "Fecha no válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar el evento en SQLite
            val calendarRepository = CalendarRepository(this)
            calendarRepository.addEvent(date, title, description)

            // Enviar resultado de éxito y cerrar actividad
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
