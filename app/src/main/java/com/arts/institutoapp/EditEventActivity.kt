package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditEventActivity : AppCompatActivity() {

    private lateinit var repository: CalendarRepository
    private var eventId: Int = -1
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        val eventTitle = findViewById<EditText>(R.id.eventTitleEdit)
        val eventDescription = findViewById<EditText>(R.id.eventDescriptionEdit)
        val saveEventButton = findViewById<Button>(R.id.saveEditButton)

        // Inicializar el repositorio
        repository = CalendarRepository(this)

        // Obtener el ID del evento y cargar los datos
        eventId = intent.getIntExtra("eventId", -1)
        selectedDate = intent.getStringExtra("selectedDate") ?: ""

        if (eventId != -1) {
            val event = repository.getEventById(eventId)
            event?.let {
                eventTitle.setText(it.title)
                eventDescription.setText(it.description)
            }
        } else {
            Toast.makeText(this, "Error al cargar el evento", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Guardar los cambios
        saveEventButton.setOnClickListener {
            val updatedTitle = eventTitle.text.toString()
            val updatedDescription = eventDescription.text.toString()

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                repository.updateEvent(eventId, updatedTitle, updatedDescription)
                Toast.makeText(this, "Evento actualizado", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
