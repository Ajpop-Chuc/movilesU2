package com.example.androidu2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameField = findViewById(R.id.username)
        passwordField = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            // Aquí podrías verificar que los campos no estén vacíos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese ambos campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(username, password)
        }
    }

    private fun login(username: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.obtenerUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body()

                    // Busca el usuario con el nombre de usuario proporcionado
                    val usuario = usuarios?.find { it.usuario == username }

                    if (usuario != null) {
                        // Valida la contraseña
                        if (usuario.contrasena == password) {
                            Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_SHORT)
                                .show()
                            // Aquí puedes continuar con la siguiente actividad o acción
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Contraseña incorrecta",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Usuario no encontrado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error en la respuesta del servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error al conectar con el servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
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
}