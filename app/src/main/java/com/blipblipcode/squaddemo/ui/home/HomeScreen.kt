package com.blipblipcode.squaddemo.ui.home

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SaveAs
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.blipblipcode.squaddemo.ui.home.models.StrokeLine
import com.blipblipcode.squaddemo.ui.utilities.cmToPx
import com.blipblipcode.squaddemo.ui.utilities.inToPx
import com.blipblipcode.squaddemo.ui.utilities.toPx

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {  //navTo: (String) -> Unit,
    //var view = hiltViewModel<HomeScreenViewModel>()
    MeasureContent(viewModel)
}

@Composable
fun MeasureContent(viewModel: HomeScreenViewModel) {
    var sizeWindow by remember {
        mutableStateOf(Size.Zero)
    } // myModifier.onGloballyPositioned
    var heightStatusBar by remember {
        mutableStateOf(Offset.Zero)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .onGloballyPositioned {
                sizeWindow = it.size.toSize()
                heightStatusBar = it.positionInWindow()


            }

    ) {


        var finger1 by remember {
            mutableFloatStateOf(0f)
        }

        var finger2 by remember {
            mutableFloatStateOf(maxHeight.toPx().minus(200f))
        }

        MeasureSquad(
            height = maxHeight,
            width = maxWidth,
            udm = Udm.Inches
        )
        Card {
            Row {
                Text(text = "${viewModel.measure}")
                IconButton(onClick = { viewModel.onSaveMeasure(measure = viewModel.measure) }) {
                    Icon(imageVector = Icons.Outlined.SaveAs, contentDescription = "Save")
                }
            }
        }

        IndicatorMeasure(
            sizeDp = DpSize(100.dp, 30.dp),
            backgroundColor = Color.Magenta,
            sizeWindow = Size(maxWidth.toPx(), maxHeight.toPx()),
            isRight = true,
            udm = Udm.Inches
        ) {
            finger1 = it

            Log.d("IndicatorMeasure", "MeasureContent A:$it ")
            viewModel.onChangedValue(finger1, finger2)
        }

        IndicatorMeasure(
            sizeDp = DpSize(100.dp, 30.dp),
            backgroundColor = Color.Red,
            sizeWindow = Size(maxWidth.toPx(), maxHeight.toPx()),
            onChanged = {
                finger2 = it
                Log.d("IndicatorMeasure", "MeasureContent A:$it ")
                viewModel.onChangedValue(finger1, finger2)
            },
            udm = Udm.Inches
        )


    }
}

@Composable
fun MeasureSquad(
    height: Dp,
    width: Dp,
    udm: Udm,
    rulerSize: Size = Size(100.dp.toPx(), 150.dp.toPx()),
    textStyle: TextStyle = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    ),
    backgroundColor: Color = Color.Yellow,
    principalLine: StrokeLine = StrokeLine(30.dp, 4f, Color.Black),
    secondaryLine: StrokeLine = StrokeLine(10.dp, 1f, Color.Black)
) {
    //region Fields
    val textMeasurer = rememberTextMeasurer()
    val scaleRuler = when (udm) {
        Udm.Inches -> 1f.inToPx()
        Udm.Centimeter -> 1f.cmToPx()
    }

    /*var countdownValue by remember { mutableStateOf(5) }
    var isCounting by remember { mutableStateOf(false) }
    val view = LocalView.current
    val window = (view.context as Activity).window
    val windowManager = remember {
        mutableStateOf(
            WindowCompat.getInsetsController(window, view)
        )
    }*/


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
        /*.pointerInput(Unit) {

            if (!isCounting) {
                isCounting = true
                object : CountDownTimer((countdownValue * 1000).toLong(), 1000) {

                    override fun onTick(millisUntilFinished: Long) {
                        countdownValue--
                       // windowManager.value.show(WindowInsetsCompat.Type.statusBars())
                    }

                    override fun onFinish() {
                        windowManager.value.hide(WindowInsetsCompat.Type.statusBars())
                        isCounting = false

                    }
                }.start()
            }
        }*/
    ) {


        val countY = size.height / scaleRuler

        val countX = size.width / scaleRuler

        //region Frame
        val frame = Path().apply {
            moveTo(0f, 0f) //P0
            lineTo(size.width, 0f) //P1

            lineTo(size.width, rulerSize.height * 0.5f) // P2

            lineTo(
                size.width - rulerSize.height * 0.5f,
                rulerSize.height
            ) //P3

            lineTo(rulerSize.width, rulerSize.height) // P4

            lineTo(
                rulerSize.width,
                size.height - rulerSize.width * 0.5f
            ) // P5

            lineTo(rulerSize.width * 0.5f, size.height)

            lineTo(0f, size.height) // P6
            close()
        }


        drawPath(frame, backgroundColor)
        //endregion

        //region Line Zero
        drawLine(
            color = principalLine.color,
            start = Offset.Zero,
            end = Offset(secondaryLine.width.toPx() * 0.3f, secondaryLine.width.toPx() * 0.3f),
            strokeWidth = principalLine.stroke
        )

        //endregion

        //region Vertical measure


        for (it in 0 until countY.toInt()) {
            val yPoint = (it + 1) * scaleRuler

            //Secondary line

            for (st in 1 until 10) {

                drawLine(
                    color = secondaryLine.color,
                    start = Offset(0f, yPoint - (scaleRuler.times(st).div(10f))),
                    end = Offset(
                        secondaryLine.width.toPx().div(2f),
                        yPoint - (scaleRuler.times(st).div(10f))
                    ),
                    strokeWidth = 2f
                )
            }

            drawLine(
                color = secondaryLine.color,
                start = Offset(0f, yPoint - (scaleRuler.div(2))),
                end = Offset(secondaryLine.width.toPx(), yPoint - (scaleRuler.div(2))),
                strokeWidth = secondaryLine.stroke
            )
            //Primary Line
            drawLine(
                color = principalLine.color,
                start = Offset(0f, yPoint),
                end = Offset(principalLine.width.toPx(), yPoint),
                strokeWidth = principalLine.stroke
            )

            //

            //Label measure

            rotate(
                degrees = 270f,
                pivot = Offset(
                    principalLine.width.toPx() + (9f * density),
                    yPoint - (3f * density)
                )
            ) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = "${it + 1}",
                    topLeft = Offset(
                        principalLine.width.toPx() + (3f * density),
                        yPoint - (9f * density)
                    ),
                    style = textStyle
                )

            }


        }


        //endregion

        //region Horizontal measure


        for (it in 0 until countX.toInt()) {
            val xPoint = (it + 1) * scaleRuler

            //Secondary line
            for (st in 1 until 10) {

                drawLine(
                    color = secondaryLine.color,
                    start = Offset(xPoint - (scaleRuler.times(st).div(10f)), 0f),
                    end = Offset(
                        xPoint - (scaleRuler.times(st).div(10f)),
                        secondaryLine.width.toPx().div(2f)
                    ),
                    strokeWidth = 2f
                )
            }
            drawLine(
                color = secondaryLine.color,
                start = Offset(xPoint - (scaleRuler.div(2f)), 0f),
                end = Offset(xPoint - (scaleRuler.div(2f)), secondaryLine.width.toPx()),
                strokeWidth = 2f
            )
            //Primary Line
            drawLine(
                color = principalLine.color,
                start = Offset(xPoint, 0f),
                end = Offset(xPoint, principalLine.width.toPx()),
                strokeWidth = principalLine.stroke
            )
            //Label measure

            drawText(
                textMeasurer = textMeasurer,
                text = "${it + 1}",
                topLeft = Offset(
                    xPoint - (3f * density),
                    principalLine.width.toPx() + (3f * density)
                ),
                style = textStyle
            )


        }


        //endregion

        //pintamos los dedos en la pantalla


    }

}
