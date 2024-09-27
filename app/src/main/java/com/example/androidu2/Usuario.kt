package com.example.androidu2

data class Usuario(
    val id: Int,
    val usuario: String,
    val contrasena: String,
    val creado_en: String,
    val id_trabajador: Int
)

data class Campus(
    val id: Int,
    val nombre: String,
    val direccion: String
)

data class Edificio(
    val id: Int,
    val nombre: String,
    val id_campus: Int
)

