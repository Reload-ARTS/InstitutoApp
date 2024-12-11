package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarActivity : AppCompatActivity() {

    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventList: MutableList<Event>
    private lateinit var selectedDate: String
    private lateinit var repository: CalendarRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val addEventButton = findViewById<Button>(R.id.addEventButton)
        val editEventButton = findViewById<Button>(R.id.editEventButton)
        val deleteEventButton = findViewById<Button>(R.id.deleteEventButton)
        val eventRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Inicializar repositorio y lista de eventos
        repository = CalendarRepository(this)
        eventList = mutableListOf()

        // Configurar RecyclerView
        eventAdapter = EventAdapter(eventList)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)
        eventRecyclerView.adapter = eventAdapter

        // Listener para seleccionar una fecha
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            loadEventsForSelectedDate(selectedDate)
        }

        // Listener para botón "Agregar"
        addEventButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivityForResult(intent, 100)
        }

        // Listener para botón "Editar"
        editEventButton.setOnClickListener {
            val selectedEvent = getSelectedEvent()
            if (selectedEvent != null) {
                val intent = Intent(this, EditEventActivity::class.java)
                intent.putExtra("eventId", selectedEvent.id)
                startActivityForResult(intent, 200)
            } else {
                Toast.makeText(this, "Selecciona un evento para editar", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para botón "Eliminar"
        deleteEventButton.setOnClickListener {
            val selectedEvent = getSelectedEvent()
            if (selectedEvent != null && selectedEvent.id != null) { // Verificar que el id no sea null
                repository.deleteEvent(selectedEvent.id!!)
                Toast.makeText(this, "Evento eliminado", Toast.LENGTH_SHORT).show()
                loadEventsForSelectedDate(selectedDate)
            } else {
                Toast.makeText(this, "Selecciona un evento válido para eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para cargar los eventos de una fecha seleccionada
    private fun loadEventsForSelectedDate(date: String) {
        eventList.clear()
        eventList.addAll(repository.getEventsByDate(date))
        eventAdapter.updateEventList(eventList)
    }

    // Método para manejar resultados de actividades
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            loadEventsForSelectedDate(selectedDate)
        }
    }

    // Método para obtener el evento seleccionado en el RecyclerView
    private fun getSelectedEvent(): Event? {
        val position = eventAdapter.getSelectedPosition()
        return if (position != -1) eventList[position] else null
    }
}
