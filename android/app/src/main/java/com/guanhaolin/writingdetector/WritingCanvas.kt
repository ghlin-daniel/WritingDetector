package com.guanhaolin.writingdetector

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guanhaolin.writingdetector.model.WritingPoint

@Composable
fun WritingCanvas(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        viewModel.onDragStart(WritingPoint(offset.x, offset.y))
                    },
                    onDrag = { change, _ ->
                        viewModel.onDrag(WritingPoint(change.position.x, change.position.y))
                    },
                    onDragEnd = viewModel::onDragEnd
                )
            }
    ) {
        uiState.completedPaths.forEach { points ->
            if (points.size > 1) {
                val path = Path()
                path.moveTo(points[0].x, points[0].y)
                points.drop(1).forEach { point ->
                    path.lineTo(point.x, point.y)
                }
                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 5.dp.toPx())
                )
            }
        }

        if (uiState.currentPath.size > 1) {
            val path = Path()
            path.moveTo(uiState.currentPath[0].x, uiState.currentPath[0].y)
            uiState.currentPath.drop(1).forEach { point ->
                path.lineTo(point.x, point.y)
            }
            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 5.dp.toPx())
            )
        }
    }
}