package com.guanhaolin.writingdetector

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


data class WritingPoint(val x: Float, val y: Float)

@Composable
fun WritingCanvas() {
    var completedPaths by remember { mutableStateOf(listOf<List<WritingPoint>>()) }
    var currentPoints by remember { mutableStateOf(listOf<WritingPoint>()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPoints = listOf(WritingPoint(offset.x, offset.y))
                    },
                    onDrag = { change, _ ->
                        currentPoints = currentPoints + WritingPoint(
                            change.position.x,
                            change.position.y
                        )
                    },
                    onDragEnd = {
                        completedPaths = completedPaths + listOf(currentPoints)
                        currentPoints = emptyList()
                    }
                )
            }
    ) {
        completedPaths.forEach { points ->
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

        if (currentPoints.size > 1) {
            val path = Path()
            path.moveTo(currentPoints[0].x, currentPoints[0].y)
            currentPoints.drop(1).forEach { point ->
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