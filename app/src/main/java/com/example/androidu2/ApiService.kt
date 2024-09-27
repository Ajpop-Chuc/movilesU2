package com.example.androidu2
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("usuarios/")
    fun obtenerUsuarios(): Call<List<Usuario>>

    @GET("mantenimientos_predefinidos/")
    fun obtenerMantenimientos(): Call<List<MantenimientoPredefinido>>

    @GET("campus/")
    fun obtenerCampus(): Call<List<Campus>>

    @GET("edificios/")
    fun obtenerEdificios(): Call<List<Edificio>>
}