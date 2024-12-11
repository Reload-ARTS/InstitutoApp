package com.arts.institutoapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val markers = mutableListOf<Marker>()
    private val sharedPrefsName = "MarkersPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Inicializar el fragmento de mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Solicitar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            try {
                map.isMyLocationEnabled = true
                getCurrentLocation()
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        // Cargar marcadores guardados
        loadMarkers()

        // Añadir marcadores al hacer clic en el mapa
        map.setOnMapClickListener { latLng ->
            val newMarker = map.addMarker(MarkerOptions().position(latLng).title("Nueva sede"))
            if (newMarker != null) {
                markers.add(newMarker)
                saveMarkers()  // Guardar los marcadores cada vez que se agrega uno nuevo
            }
        }

        // Listener para los marcadores existentes (clic en el marcador)
        map.setOnMarkerClickListener { marker ->
            showEditDeleteDialog(marker)
            true
        }
    }

    // Mostrar un diálogo para editar o eliminar el marcador
    private fun showEditDeleteDialog(marker: Marker) {
        val options = arrayOf("Editar", "Eliminar")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Opciones del marcador")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> showEditMarkerDialog(marker)
                1 -> {
                    marker.remove()
                    markers.remove(marker)
                    saveMarkers()  // Guardar después de eliminar
                    Toast.makeText(this, "Marcador eliminado", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.show()
    }

    // Diálogo para editar el título del marcador
    private fun showEditMarkerDialog(marker: Marker) {
        val editText = EditText(this)
        editText.setText(marker.title)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Editar marcador")
            .setView(editText)
            .setPositiveButton("Guardar") { _, _ ->
                val newTitle = editText.text.toString()
                marker.title = newTitle
                marker.showInfoWindow()
                saveMarkers()  // Guardar después de editar
                Toast.makeText(this, "Marcador editado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    // Obtener la ubicación actual del usuario
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                map.addMarker(MarkerOptions().position(currentLatLng).title("Tu ubicación actual"))
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Guardar los marcadores en SharedPreferences
    private fun saveMarkers() {
        val sharedPreferences = getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val markerArray = JSONArray()
        for (marker in markers) {
            val markerObject = JSONObject()
            markerObject.put("lat", marker.position.latitude)
            markerObject.put("lng", marker.position.longitude)
            markerObject.put("title", marker.title)
            markerArray.put(markerObject)
        }

        editor.putString("markers", markerArray.toString())
        editor.apply()
    }

    // Cargar los marcadores de SharedPreferences
    private fun loadMarkers() {
        val sharedPreferences = getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val markerString = sharedPreferences.getString("markers", null)

        if (markerString != null) {
            val markerArray = JSONArray(markerString)

            for (i in 0 until markerArray.length()) {
                val markerObject = markerArray.getJSONObject(i)
                val lat = markerObject.getDouble("lat")
                val lng = markerObject.getDouble("lng")
                val title = markerObject.getString("title")

                val marker = map.addMarker(
                    MarkerOptions().position(LatLng(lat, lng)).title(title)
                )
                if (marker != null) {
                    markers.add(marker)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                try {
                    map.isMyLocationEnabled = true
                    getCurrentLocation()
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
