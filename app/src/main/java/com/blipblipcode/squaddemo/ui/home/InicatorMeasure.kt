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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.DpSize
import com.blipblipcode.squaddemo.ui.utilities.toCm
import com.blipblipcode.squaddemo.ui.utilities.toInches

@Composable
fun IndicatorMeasure(
    sizeDp: DpSize,
    backgroundColor: Color,
    sizeWindow: Size = Size.Zero,
    isRight: Boolean = false,
    modifier: Modifier = Modifier,
    udm: Udm,
    ini: Float = 0f,
    onChanged: (Float) -> Unit
) {
    var touch by remember { mutableFloatStateOf(ini) }
    var value by remember { mutableFloatStateOf(ini) }
    val textMeasurer = rememberTextMeasurer(20)


    Canvas(
        modifier = modifier
            .size(sizeDp)
            .then(
                Modifier

                    .graphicsLayer {
                        translationY = touch

                    }
                    .pointerInput(Unit) {
                        val heightSpan = sizeDp.height.toPx()
                        detectDragGestures { _, dragAmount ->
                            val target = touch + dragAmount.y
                            if (target >= 0f && target <= sizeWindow.height - heightSpan) {
                                touch += dragAmount.y
                                value = if (!isRight) {
                                    onChanged(touch.plus(heightSpan))
                                    touch.plus(heightSpan)
                                } else {
                                    onChanged(touch)
                                    touch
                                }
                            }

                        }
                    })
    ) {

        val path = Path().apply {
            moveTo(size.height.times(0.6f), 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(size.height.times(0.6f), size.height)
            if (!isRight) {

                lineTo(0f, size.height)
            } else {
                lineTo(0f, 0f)
            }

            close()
        }

        drawRect(
            backgroundColor,
            topLeft = Offset(size.width.times(0.5f), 0f),
            size = Size(size.width.times(0.5f), size.height)
        )
        drawPath(path, Color.Black, style = Stroke(width = 3f))
        val text = when (udm) {
            Udm.Centimeter -> value.toCm()
            Udm.Inches -> value.toInches()
        }
        val textResult = textMeasurer.measure(String.format("%.2f", text), TextStyle.Default)
        drawText(
            textResult, Color.Black,
            topLeft = Offset(
                size.width.div(2f).plus(textResult.size.width.div(2f)),
                size.height.div(2f) - textResult.size.height.div(2f)
            )
        )

    }


}