package com.arts.institutoapp

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity

class NotasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        // Referencia al TableLayout en el XML
        val tableLayout: TableLayout = findViewById(R.id.notaTable)

        // Lista de asignaturas con notas y fechas
        val asignaturas = listOf(
            Triple("Programación I", 6.5, "01/09/2024"),
            Triple("Bases de Datos", 7.0, "15/09/2024"),
            Triple("Algoritmos y Estructuras de Datos", 6.2, "10/09/2024"),
            Triple("Desarrollo Web", 6.8, "20/09/2024"),
            Triple("Computación en la Nube", 7.0, "25/09/2024")
        )

        // Agregar dinámicamente las filas al TableLayout
        for (asignatura in asignaturas) {
            val tableRow = TableRow(this)

            val materiaTextView = TextView(this).apply {
                text = asignatura.first
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
                setPadding(30, 30, 30, 30)
            }

            val notaTextView = TextView(this).apply {
                text = asignatura.second.toString()
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
            }

            val fechaTextView = TextView(this).apply {
                text = asignatura.third
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
            }

            // Añadir los TextViews a la fila
            tableRow.addView(materiaTextView)
            tableRow.addView(notaTextView)
            tableRow.addView(fechaTextView)

            // Añadir la fila al TableLayout
            tableLayout.addView(tableRow)
        }
    }
}
