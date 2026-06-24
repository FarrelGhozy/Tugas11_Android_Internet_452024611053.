package com.example.tugas11.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas11.model.Photo
import com.example.tugas11.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class PhotoUiState {
    data object Loading : PhotoUiState()
    data class Success(val photos: List<Photo>) : PhotoUiState()
    data class Error(val message: String) : PhotoUiState()
}

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PhotoUiState>(PhotoUiState.Loading)
    val uiState: StateFlow<PhotoUiState> = _uiState.asStateFlow()

    init {
        fetchPhotos()
    }

    fun fetchPhotos() {
        viewModelScope.launch {
            _uiState.value = PhotoUiState.Loading
            try {
                val photos = RetrofitInstance.api.getPhotos()
                _uiState.value = PhotoUiState.Success(photos)
            } catch (e: Exception) {
                _uiState.value = PhotoUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
