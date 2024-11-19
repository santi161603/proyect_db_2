package com.uni.proyecto.proyect_db_2.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.proyecto.proyect_db_2.data.models.RegisterDTO
import com.uni.proyecto.proyect_db_2.data.networt.RetrofitApi
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel() {

    private val _uiState = MutableLiveData<RegisterUiState>()
    val uiState: LiveData<RegisterUiState> = _uiState

    // Método que realiza la llamada al servicio de registro
    fun registerUsuario(registerDTO: RegisterDTO) {
        _uiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            val response = RetrofitApi.apiGson.registerUsuario(registerDTO)
            val mensajeDTO = response.body()
            if (mensajeDTO != null && !mensajeDTO.error) {
                _uiState.value = RegisterUiState.Success(mensajeDTO.respuesta)
            } else {
                _uiState.value = RegisterUiState.Error("Error en respuesta: ${mensajeDTO?.respuesta}")
            }
        }
    }

    sealed class RegisterUiState {
        object Loading : RegisterUiState()
        object HideLoading : RegisterUiState()
        data class Success(val data: String) : RegisterUiState()
        data class Error(val message: String) : RegisterUiState()
    }
}