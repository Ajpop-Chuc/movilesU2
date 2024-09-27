package com.example.androidu2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.URL

class activity_mantenimiento : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var editTextMultiLine: EditText
    private lateinit var photoButton: ImageButton
    private lateinit var guardarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento)

        spinner = findViewById(R.id.spinner)
        editTextMultiLine = findViewById(R.id.editTextTextMultiLine)
        photoButton = findViewById(R.id.photoButton)
        guardarButton = findViewById(R.id.guardarButton)

        loadSpinnerData()

        photoButton.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

        guardarButton.setOnClickListener {
            // Implementar la lógica para guardar los datos
        }
    }

    private fun loadSpinnerData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = URL("https://b1t2mdr7-8000.use2.devtunnels.ms/mantenimientos_predefinidos/").readText()
                val jsonArray = JSONArray(response)
                val tiposMantenimiento = mutableListOf<String>()

                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    tiposMantenimiento.add(item.getString("tipo_mantenimiento"))
                }

                launch(Dispatchers.Main) {
                    val adapter = ArrayAdapter(this@activity_mantenimiento, android.R.layout.simple_spinner_item, tiposMantenimiento)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val CAMERA_REQUEST_CODE = 101
    }
}