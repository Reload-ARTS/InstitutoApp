package com.arts.institutoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarActivity : AppCompatActivity() {

    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventList: MutableList<Event>
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val addEventButton = findViewById<Button>(R.id.addEventButton)
        val eventRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Inicializar lista de eventos
        eventList = mutableListOf()

        // Configurar RecyclerView
        eventAdapter = EventAdapter(eventList)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)
        eventRecyclerView.adapter = eventAdapter

        // Listener para seleccionar una fecha
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            // Filtrar los eventos por la fecha seleccionada
            filterEventsByDate(selectedDate)
        }

        // Listener para agregar un nuevo evento
        addEventButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivityForResult(intent, 100)
        }
    }

    private fun filterEventsByDate(date: String) {
        val filteredEvents = eventList.filter { it.date == date }
        eventAdapter.updateEventList(filteredEvents)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val newEvent = data?.getSerializableExtra("newEvent") as Event
            eventList.add(newEvent)
            filterEventsByDate(selectedDate)
        }
    }
}
