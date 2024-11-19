package com.uni.proyecto.proyect_db_2.data.models

data class MenasajeDTO<T>(
    val error: Boolean,
    val respuesta: T
)
