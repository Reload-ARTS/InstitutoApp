package com.arts.institutoapp

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class WelcomeActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Inicializar SensorManager y el sensor de luz
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            println("El sensor de luz no está disponible en este dispositivo")
        }

        // Obtener el texto de bienvenida y establecer el nombre de usuario
        val welcomeText = findViewById<TextView>(R.id.welcomeTextView)
        val userName = intent.getStringExtra("USERNAME")

        // Verificar si se obtuvo el nombre de usuario correctamente
        if (userName != null) {
            welcomeText.text = "Bienvenido $userName"
        } else {
            welcomeText.text = "Error: Nombre de usuario no disponible"
        }

        // Configuración del botón de calendario
        val calendarButton = findViewById<ImageButton>(R.id.buttonCalendar)
        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        val buttonNotas: ImageButton = findViewById(R.id.buttonNotas)
        buttonNotas.setOnClickListener {
            val intent = Intent(this, NotasActivity::class.java)
            startActivity(intent)
        }

        val buttonEvaluacion: ImageButton = findViewById(R.id.buttonEvaluacion)
        buttonEvaluacion.setOnClickListener {
            val intent = Intent(this, EvaluacionActivity::class.java)
            startActivity(intent)
        }

        val buttonSede: ImageButton = findViewById(R.id.buttonSede)
        buttonSede.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }

        // Configurar el botón de certificados
        val buttonCertificado: ImageButton = findViewById(R.id.buttonCertificado)
        buttonCertificado.setOnClickListener {
            // Abre la actividad de certificados
            val intent = Intent(this, CertificadosActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightLevel = event.values[0]
            if (lightLevel > 5000) { // Mucha luz → Modo oscuro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else { // Poca luz → Modo claro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se necesita implementación
    }
}
