package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        val eventTitle = findViewById<EditText>(R.id.eventTitle)
        val eventDescription = findViewById<EditText>(R.id.eventDescription)
        val saveEventButton = findViewById<Button>(R.id.saveEventButton)

        saveEventButton.setOnClickListener {
            val title = eventTitle.text.toString()
            val description = eventDescription.text.toString()
            val date = intent.getStringExtra("selectedDate")

            // Crear el nuevo evento
            val newEvent = Event(title, description, date!!)

            // Crear un Intent de resultado
            val resultIntent = Intent()
            resultIntent.putExtra("newEvent", newEvent)

            // Establecer el resultado y cerrar la actividad
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
