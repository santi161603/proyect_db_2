package com.uni.proyecto.proyect_db_2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.proyecto.proyect_db_2.data.models.LoginDTO
import com.uni.proyecto.proyect_db_2.data.networt.RetrofitApi
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> = _uiState

    fun loginUsuario(loginDTO: LoginDTO) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            val response = RetrofitApi.apiGson.iniciarSesion(loginDTO)
            val mensajeDTO = response.body()

            if (mensajeDTO != null && !mensajeDTO.error) {
                if(mensajeDTO.respuesta){
                    _uiState.value = LoginUiState.HideLoading
                    _uiState.value = LoginUiState.Success("sesion Iniciada")
                }else{
                    _uiState.value = LoginUiState.HideLoading
                    _uiState.value = LoginUiState.Error("Error correo o contraseña invalido")
                }
            } else {
                _uiState.value = LoginUiState.HideLoading
                _uiState.value = LoginUiState.Error("Error en respuesta: ${mensajeDTO?.respuesta}")
            }
        }

    }

    sealed class LoginUiState {
        object Loading : LoginUiState()
        object HideLoading : LoginUiState()
        data class Success(val data: String) : LoginUiState()
        data class Error(val message: String) : LoginUiState()
    }
}
