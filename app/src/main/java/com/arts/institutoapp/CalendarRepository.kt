package com.arts.institutoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class CalendarRepository(context: Context) {
    private val dbHelper = CalendarDatabaseHelper(context)

    // Agregar un evento a la base de datos
    fun addEvent(date: String, title: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CalendarDatabaseHelper.COLUMN_DATE, date)
            put(CalendarDatabaseHelper.COLUMN_TITLE, title)
            put(CalendarDatabaseHelper.COLUMN_DESCRIPTION, description)
        }
        db.insert(CalendarDatabaseHelper.TABLE_EVENTS, null, values)
        db.close() // Asegurarse de cerrar la base de datos
    }

    // Obtener eventos por fecha
    fun getEventsByDate(date: String): List<Event> {
        val db = dbHelper.readableDatabase
        val events = mutableListOf<Event>()

        db.query(
            CalendarDatabaseHelper.TABLE_EVENTS,
            null,
            "${CalendarDatabaseHelper.COLUMN_DATE} = ?",
            arrayOf(date),
            null,
            null,
            null
        ).use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_DESCRIPTION))
                events.add(Event(title, description, date, id))
            }
        }

        db.close() // Cerrar base de datos
        return events
    }

    // Obtener evento por ID
    fun getEventById(id: Int): Event? {
        val db = dbHelper.readableDatabase
        var event: Event? = null

        db.query(
            CalendarDatabaseHelper.TABLE_EVENTS,
            null,
            "${CalendarDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_DATE))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_DESCRIPTION))
                event = Event(title, description, date, id)
            }
        }

        db.close() // Cerrar base de datos
        return event
    }

    // Actualizar un evento por ID
    fun updateEvent(id: Int, title: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CalendarDatabaseHelper.COLUMN_TITLE, title)
            put(CalendarDatabaseHelper.COLUMN_DESCRIPTION, description)
        }
        db.update(CalendarDatabaseHelper.TABLE_EVENTS, values, "${CalendarDatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        db.close() // Cerrar base de datos
    }

    // Eliminar un evento por ID
    fun deleteEvent(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(CalendarDatabaseHelper.TABLE_EVENTS, "${CalendarDatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        db.close() // Cerrar base de datos
    }
}
