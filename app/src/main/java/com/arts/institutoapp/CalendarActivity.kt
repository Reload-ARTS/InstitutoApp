package com.arts.institutoapp

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Referenciar el CalendarView y el TextView
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val selectedDateText = findViewById<TextView>(R.id.selectedDateText)

        // Listener para la selección de fecha
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // El mes en CalendarView comienza desde 0 (Enero = 0, Febrero = 1, etc.), por eso se suma 1
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            selectedDateText.text = "Fecha seleccionada: $selectedDate"
        }
    }
}