package com.guanhaolin.writingdetector.model

data class DrawingUiState(
    val completedPaths: List<List<WritingPoint>> = listOf(),
    val currentPath: List<WritingPoint> = listOf()
)
