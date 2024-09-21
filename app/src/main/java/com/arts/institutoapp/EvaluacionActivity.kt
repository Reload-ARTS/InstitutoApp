package com.arts.institutoapp

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EvaluacionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluacion)

        val docenteNameTextView: TextView = findViewById(R.id.docenteName)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val saveRatingButton: Button = findViewById(R.id.saveRatingButton)

        // Asigna un nombre al docente, por ejemplo
        docenteNameTextView.text = "Docente Ejemplo"

        saveRatingButton.setOnClickListener {
            val rating = ratingBar.rating
            // Aquí puedes guardar la evaluación (en una base de datos, por ejemplo)
            // O mostrar un mensaje de éxito
        }
    }
}
