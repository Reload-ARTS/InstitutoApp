package com.arts.institutoapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient

import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder
import java.nio.charset.StandardCharsets
import java.util.*

class CertificadosActivity : AppCompatActivity() {

    // Variables
    private lateinit var txtNombre: EditText
    private lateinit var txtRut: EditText
    private lateinit var txtCarrera: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var spTipoCertificado: Spinner

    // Firebase
    private lateinit var db: FirebaseFirestore

    // MQTT
    private lateinit var mqttClient: Mqtt5AsyncClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificados)

        // Inicializar Firebase
        db = FirebaseFirestore.getInstance()

        // Configurar MQTT
        connectToMqttBroker()

        // Asociar variables con los IDs del XML
        txtNombre = findViewById(R.id.txtNombre)
        txtRut = findViewById(R.id.txtRut)
        txtCarrera = findViewById(R.id.txtCarrera)
        txtTelefono = findViewById(R.id.txtTelefono)
        spTipoCertificado = findViewById(R.id.spTipoCertificado)

        // Poblar Spinner con tipos de certificados
        val tiposCertificados = arrayOf("Alumno Regular", "Certificado de Notas", "Certificado de Asistencia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposCertificados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoCertificado.adapter = adapter
    }

    // Método para conectar a MQTT
    private fun connectToMqttBroker() {
        mqttClient = MqttClient.builder()
            .useMqttVersion5()
            .identifier("AndroidClient-${UUID.randomUUID()}")
            .serverHost("a922dba2817d46e79ed6829d08e4d6c9.s1.eu.hivemq.cloud")
            .serverPort(8883) // Puerto con TLS
            .sslWithDefaultConfig() // Habilitar SSL/TLS
            .buildAsync()

        // Configuración de conexión directa con autenticación
        mqttClient.connectWith()
            .cleanStart(true) // Clean session
            .keepAlive(60) // Tiempo de keep alive
            .simpleAuth()
            .username("hivemq.webclient.1733857923524")
            .password(">?HX%8uf4FxQTrG0l3&o".toByteArray(StandardCharsets.UTF_8))
            .applySimpleAuth()
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Toast.makeText(this, "Error al conectar a MQTT: ${throwable.message}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Conectado a MQTT", Toast.LENGTH_SHORT).show()
                }
            }
    }


    // Método para enviar datos
    fun enviarDatosFirestore(view: View) {
        val nombre = txtNombre.text.toString()
        val rut = txtRut.text.toString()
        val carrera = txtCarrera.text.toString()
        val telefono = txtTelefono.text.toString()
        val tipoCertificado = spTipoCertificado.selectedItem.toString()

        if (nombre.isEmpty() || rut.isEmpty() || carrera.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Generar código único
        val solicitudRef = db.collection("certificado").document()
        val codigo = solicitudRef.id

        val solicitud = hashMapOf(
            "codigo" to codigo,
            "nombre" to nombre,
            "rut" to rut,
            "carrera" to carrera,
            "telefono" to telefono,
            "tipoCertificado" to tipoCertificado,
            "estado" to "Pendiente",
            "fechaSolicitud" to Calendar.getInstance().time.toString()
        )

        // Subir a Firestore
        solicitudRef.set(solicitud)
            .addOnSuccessListener {
                Toast.makeText(this, "Solicitud enviada a Firestore correctamente", Toast.LENGTH_SHORT).show()

                // Publicar en MQTT
                val mqttMessage = """
            {
                "codigo": "$codigo",
                "nombre": "$nombre",
                "rut": "$rut",
                "carrera": "$carrera",
                "telefono": "$telefono",
                "tipoCertificado": "$tipoCertificado",
                "estado": "Pendiente"
            }
            """.trimIndent()
                publishMessage("instituto/certificados", mqttMessage)

                limpiarCampos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al enviar solicitud a Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    // Método para publicar mensajes en MQTT
    private fun publishMessage(topic: String, message: String) {
        mqttClient.publishWith()
            .topic(topic)
            .payload(message.toByteArray(StandardCharsets.UTF_8))
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Toast.makeText(this, "Error al enviar mensaje MQTT: ${throwable.message}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Mensaje enviado a $topic", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Método para limpiar campos
    private fun limpiarCampos() {
        txtNombre.text.clear()
        txtRut.text.clear()
        txtCarrera.text.clear()
        txtTelefono.text.clear()
        spTipoCertificado.setSelection(0)
    }
}
