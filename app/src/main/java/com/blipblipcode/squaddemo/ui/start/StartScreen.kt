package com.blipblipcode.squaddemo.ui.start

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blipblipcode.squaddemo.R
import com.blipblipcode.squaddemo.ui.graphNavigation.Screens
import com.blipblipcode.squaddemo.ui.start.state.SplashUiState

@Composable
fun SplashScreen(viewModel: StartViewModel = hiltViewModel(), navGo: (String) -> Unit) {
    var urlPlayStore by remember {
        mutableStateOf("")
    }
    var isRequireUpdate by remember {
        mutableStateOf(false)
    }
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    viewModel.onCheckedVersion()
    when (uiState.value) {
        SplashUiState.Loading -> {
            Log.d("TAG", "SplashScreen: loading")
        }

        SplashUiState.Navigate -> {
            navGo(Screens.Home.route)
        }

        SplashUiState.RequireUpdate -> {
            isRequireUpdate = true
            urlPlayStore = viewModel.urlPlayStore
        }
    }



    Box(modifier = Modifier.fillMaxSize()) {
        LoadingScreen()
        UpdateDialog(isVisible = isRequireUpdate, onUpdateClicked = {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.urlPlayStore))
            ContextCompat.startActivity(context, intent, null)

        })
    }

}

@Composable
fun TitleDialog(text: String) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Upgrade,
            contentDescription = "",
            tint = Color.Yellow,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Yellow
        )
    }
}

@Composable
fun ButtonDialog(text: String, onClick: () -> Unit) {

    TextButton(onClick = {
        onClick()
    }, colors = ButtonDefaults.textButtonColors(contentColor = Color.Yellow)) {
        Text(text = text, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun UpdateDialog(isVisible: Boolean, onUpdateClicked: () -> Unit) {
    if (isVisible) {
        AlertDialog(onDismissRequest = {},
            title = {
                TitleDialog(text = stringResource(R.string.app_name))
            },

            text = {
                Text(
                    text = stringResource(R.string.requiere_update),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                ButtonDialog(text = stringResource(R.string.update)) {
                    onUpdateClicked()
                }
            }
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.measurement))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        LottieAnimation(
            composition,
            progress,
            modifier = Modifier
                .width(400.dp)
                .height(400.dp)
        )
    }
}