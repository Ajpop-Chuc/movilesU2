package com.example.androidu2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class activity_areatrabajo : AppCompatActivity() {

    private lateinit var spinnerCampus: Spinner
    private lateinit var spinnerEdificio: Spinner
    private var campusList: List<Campus>? = null
    private var edificioList: List<Edificio>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_areatrabajo)

        spinnerCampus = findViewById(R.id.spinnerCampus)
        spinnerEdificio = findViewById(R.id.spinnerEdificio)
        val confirmButton: Button = findViewById(R.id.photoButton)

        cargarCampus()
        cargarEdificios()

        confirmButton.setOnClickListener {
            enviarDatos()
        }
    }

    private fun cargarCampus() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.obtenerCampus().enqueue(object : Callback<List<Campus>> {
            override fun onResponse(call: Call<List<Campus>>, response: Response<List<Campus>>) {
                if (response.isSuccessful) {
                    campusList = response.body()
                    val nombresCampus = campusList?.map { it.nombre } ?: emptyList()

                    val adapter = ArrayAdapter(this@activity_areatrabajo, android.R.layout.simple_spinner_item, nombresCampus)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerCampus.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Campus>>, t: Throwable) {
                Toast.makeText(this@activity_areatrabajo, "Error al cargar los campus", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarEdificios() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.obtenerEdificios().enqueue(object : Callback<List<Edificio>> {
            override fun onResponse(call: Call<List<Edificio>>, response: Response<List<Edificio>>) {
                if (response.isSuccessful) {
                    edificioList = response.body()
                    val nombresEdificios = edificioList?.map { it.nombre } ?: emptyList()

                    val adapter = ArrayAdapter(this@activity_areatrabajo, android.R.layout.simple_spinner_item, nombresEdificios)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerEdificio.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Edificio>>, t: Throwable) {
                Toast.makeText(this@activity_areatrabajo, "Error al cargar los edificios", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun enviarDatos() {
        val campusSeleccionado = spinnerCampus.selectedItemPosition
        val edificioSeleccionado = spinnerEdificio.selectedItemPosition

        val campusId = campusList?.get(campusSeleccionado)?.id ?: -1
        val edificioId = edificioList?.get(edificioSeleccionado)?.id ?: -1

        val message = "Campus ID: $campusId, Edificio ID: $edificioId"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        // Crear un Intent para abrir la nueva actividad
        val intent = Intent(this, activity_mantenimiento::class.java)
        intent.putExtra("campusId", campusId)
        intent.putExtra("edificioId", edificioId)
        startActivity(intent)
    }
}

object RetrofitClient {
    private const val BASE_URL = "https://b1t2mdr7-8000.use2.devtunnels.ms/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

