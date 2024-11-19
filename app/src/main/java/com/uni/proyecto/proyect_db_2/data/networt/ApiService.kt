package com.uni.proyecto.proyect_db_2.data.networt

import com.uni.proyecto.proyect_db_2.data.models.LoginDTO
import com.uni.proyecto.proyect_db_2.data.models.MenasajeDTO
import com.uni.proyecto.proyect_db_2.data.models.RegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("funciones/saludar")
    suspend fun obtenerTexto(@Query("nombre") nombre: String): String

    @POST("procedimientos/registrar")
    suspend fun registerUsuario(
        @Body registerDTO: RegisterDTO
    ): Response<MenasajeDTO<String>>

    @PUT("funciones/iniciar-sesion")
    suspend fun iniciarSesion(
        @Body loginDTO: LoginDTO
    ): Response<MenasajeDTO<Boolean>>
}
