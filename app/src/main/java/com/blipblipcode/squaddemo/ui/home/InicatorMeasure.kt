package com.blipblipcode.squaddemo.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize

@Composable
fun IndicatorMeasure(
    sizeDp: DpSize,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    ini: Float = 0f,
    onChanged: (Float) -> Unit
) {
    var touch by remember { mutableFloatStateOf(ini) }


    Canvas(modifier = modifier
        .size(sizeDp)
        .then(
            Modifier

                .graphicsLayer {
                    translationY = touch
                }
                .pointerInput(Unit) {

                    detectDragGestures { _, dragAmount ->
                        touch += dragAmount.y
                        onChanged(touch)
                    }
                })
    ) {

        val path = Path().apply {
            moveTo(size.height.times(0.6f), 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(size.height.times(0.6f), size.height)
            lineTo(0f, size.height.div(2f))
            close()
        }


        drawRect(
            backgroundColor,
            topLeft = Offset(size.width.times(0.3f), 0f),
            size = Size(size.width.times(0.7f), size.height)
        )
        drawPath(path, Color.Black, style = Stroke(width = 5f))

    }


}