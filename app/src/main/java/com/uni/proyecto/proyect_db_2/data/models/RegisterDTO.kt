package com.uni.proyecto.proyect_db_2.data.models

data class RegisterDTO(

    val afiliadoDirectoId: Long? = null,
    val nombreUsuario: String,
    val clave: String,
    val correo: String,
    val telefono: String? = null,
    val direccion: String? = null
)
