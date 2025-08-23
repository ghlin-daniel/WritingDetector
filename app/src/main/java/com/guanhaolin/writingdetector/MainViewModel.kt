package com.guanhaolin.writingdetector

import androidx.lifecycle.ViewModel
import com.guanhaolin.writingdetector.model.DrawingUiState
import com.guanhaolin.writingdetector.model.WritingPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState: StateFlow<DrawingUiState> = _uiState.asStateFlow()

    fun onDragStart(point: WritingPoint) {
        _uiState.value = _uiState.value.copy(currentPath = listOf(point))
    }

    fun onDrag(point: WritingPoint) {
        _uiState.value = _uiState.value.copy(currentPath = _uiState.value.currentPath + point)
    }

    fun onDragEnd() {
        _uiState.value = _uiState.value.copy(
            completedPaths = _uiState.value.completedPaths + listOf(_uiState.value.currentPath),
            currentPath = emptyList()
        )
    }
}