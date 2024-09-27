package com.example.androidu2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.LinearLayout
import android.widget.Button

class activity_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val maintenanceButton = findViewById<LinearLayout>(R.id.maintenanceButton)
        val incidentButton = findViewById<LinearLayout>(R.id.incidentButton)
        val sendInfoButton = findViewById<LinearLayout>(R.id.sendInfoButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Redireccionar al hacer clic en Ingresar Mantenimiento
        maintenanceButton.setOnClickListener {
            val intent = Intent(this@activity_menu, activity_mantenimiento::class.java)
            startActivity(intent)
        }

        // Redireccionar al hacer clic en Ingresar Incidente
        incidentButton.setOnClickListener {
            val intent = Intent(this@activity_menu, activity_mantenimiento::class.java)
            startActivity(intent)
        }

        // Redireccionar al hacer clic en Enviar Información
        sendInfoButton.setOnClickListener {
            val intent = Intent(this@activity_menu, activity_mantenimiento::class.java)
            startActivity(intent)
        }

        // Acción para el botón Logout
        logoutButton.setOnClickListener {
            // Aquí puedes redirigir a la actividad de login o realizar alguna otra acción
            val intent = Intent(this@activity_menu, MainActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad actual

        }
    }
}