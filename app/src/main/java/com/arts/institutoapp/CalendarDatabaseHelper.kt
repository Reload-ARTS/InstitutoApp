package com.arts.institutoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CalendarDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Nombre y versión de la base de datos
        private const val DATABASE_NAME = "calendar.db"
        private const val DATABASE_VERSION = 1

        // Nombre de la tabla y columnas
        const val TABLE_EVENTS = "events"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla "events"
        val createTableQuery = """
            CREATE TABLE $TABLE_EVENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, -- Clave primaria
                $COLUMN_DATE TEXT NOT NULL,                   -- Fecha (no puede ser nulo)
                $COLUMN_TITLE TEXT NOT NULL,                  -- Título del evento
                $COLUMN_DESCRIPTION TEXT                     -- Descripción del evento
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // En caso de actualización, eliminar la tabla antigua y crear una nueva
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EVENTS")
        onCreate(db)
    }

    /**
     * Método opcional para borrar todas las filas de la tabla "events".
     */
    fun clearEvents() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_EVENTS")
        db.close()
    }
}
