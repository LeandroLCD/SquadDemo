package com.blipblipcode.squaddemo

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blipblipcode.squaddemo.ui.theme.SquadDemoTheme
import com.blipblipcode.squaddemo.ui.utilities.inToPx
import com.blipblipcode.squaddemo.ui.utilities.toCm
import com.blipblipcode.squaddemo.ui.utilities.toDp
import com.blipblipcode.squaddemo.ui.utilities.toInches
import dagger.hilt.android.AndroidEntryPoint
val TAG = "Measure"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SquadDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Greeting("Android") // Ctrl + Ship + Up / Dow
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(

        text = "Hello $name!",
        modifier = modifier.fillMaxSize(),
        onTextLayout = {
            var x = it.size
            Log.d(TAG, "Greeting: ${x.height}") // 4.859 in
            Log.d(TAG, "Greeting: ${x.width}")  // 2.454 in
            val density = Resources.getSystem().displayMetrics.density
            val densityDpi = Resources.getSystem().displayMetrics.densityDpi
            Log.d(TAG, "density: $density")
            Log.d(TAG, "densityDpi: $densityDpi")
            val h = it.size.height.toFloat().toInches()
            val w = it.size.width.toFloat().toCm()
            Log.d(TAG, "h: $h  w: $w")

        }

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SquadDemoTheme {
        Greeting("Android")
    }
}