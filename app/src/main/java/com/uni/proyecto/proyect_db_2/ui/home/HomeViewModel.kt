package com.uni.proyecto.proyect_db_2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.proyecto.proyect_db_2.data.networt.RetrofitApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel : ViewModel() {

    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> = _uiState


    fun obtenerTexto(nombre: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val response = RetrofitApi.apiText.obtenerTexto(nombre)
                _uiState.value = HomeUiState.Success(response)
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("Error de red: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = HomeUiState.Error("Error del servidor: ${e.message}")
            } finally {
                _uiState.value = HomeUiState.HideLoading
            }
        }
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        object HideLoading: HomeUiState()
        data class Success(val data: String) : HomeUiState()
        data class Error(val message: String) : HomeUiState()
    }
}