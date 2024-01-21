package com.blipblipcode.squaddemo.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.blipblipcode.squaddemo.ui.utilities.cmToPx
import com.blipblipcode.squaddemo.ui.utilities.inToPx
import com.blipblipcode.squaddemo.ui.utilities.toPx

@Composable
fun HomeScreen() {
    MeasureContent()
}

@Composable
fun MeasureContent() {
    //var rowSize by remember { mutableStateOf(Size.Zero) } // myModifier.onGloballyPositioned
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {

        MeasureSquad(height = maxHeight, width = maxWidth)
    }
}

@Composable
fun MeasureSquad(
    height: Dp,
    width: Dp,
    udm: Udm = Udm.Centimeter,
    rulerSize: Size = Size(100.dp.toPx(), 150.dp.toPx())
) {
    //region Fields
    val textMeasurer = rememberTextMeasurer()
    val scaleRuler = when (udm) {
        Udm.Inches -> 1f.inToPx()
        Udm.Centimeter -> 1f.cmToPx()
    }
    //endregion

    /*
        P0	X:0, Y:0
        P1	X:size.width, Y:0
        P2	X:size.width, Y:rulerSize.height * 0.5f
        P3	X:size.width - rulerSize.height * 0.5f, Y =  rulerSize.height
        P4	X: rulerSize.width,Y =  rulerSize.height
        P5	X: rulerSize.width,Y =  size.height - rulerSize.width *0.5f
        P6	X: rulerSize.width *0.5f, Y: size.height
        P7	X:0 , Y: size.height
    * */

    Canvas(
        modifier = Modifier
            .height(height)
            .width(width)
    ) {

        val countY = size.height / scaleRuler

        val countX = size.width / scaleRuler

        //region Frame
        val frameRuler = Path().apply {
            moveTo(x = 0f, y = 0f) // P0
            lineTo(x = size.width, y = 0f) // P1
            lineTo(x = size.width, y = rulerSize.height * 0.5f) // P2
            lineTo(x = size.width - (rulerSize.height * 0.5f), y = rulerSize.height) //P3
            lineTo(x = rulerSize.width, y = rulerSize.height) // P4
            lineTo(x = rulerSize.width, y = size.height - (rulerSize.width * 0.5f)) // P5
            lineTo(x = rulerSize.width * 0.5f, y = size.height)
            lineTo(x = 0f, y = size.height)
            close()
        }

        drawPath(frameRuler, Color.Magenta)
        //endregion

        //region LineZero
        drawLine(
            Color.Black,
            start = Offset.Zero,
            end = Offset(x = rulerSize.width * 0.15f, y = rulerSize.width * 0.15f),
            strokeWidth = 4f
        )
        //endregion
        // valor  -- 100
        //  x      -- 1


        for (it in 0 until countY.toInt()) {
            val yPoint = (it + 1) * scaleRuler

            //linea secundaria
            drawLine(
                Color.Black,
                start = Offset(x = 0f, y = yPoint - (scaleRuler * 0.5f)),
                end = Offset(x = 20f, y = yPoint - (scaleRuler * 0.5f)),
                strokeWidth = 2f
            )


            //linea Principal
            drawLine(
                Color.Black,
                start = Offset(x = 0f, y = yPoint),
                end = Offset(x = 70f, y = yPoint),
                strokeWidth = 4f
            )

            //rotulacion de la regla
            if (it == 0) {
                rotate(
                    degrees = 315f,
                    pivot = Offset(scaleRuler, scaleRuler)
                ) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = "1",
                        topLeft = Offset(scaleRuler, scaleRuler - (5f * density))
                    )
                }
            } else {
                rotate(
                    degrees = 270f,
                    pivot = Offset(scaleRuler + (3f * density), yPoint + (3f * density))
                ) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = "${it + 1}",
                        topLeft = Offset(scaleRuler, yPoint + (9f * density))
                    )
                }
            }
        }
        /*repeat(countX.toInt()){

        }*/
    }

}
