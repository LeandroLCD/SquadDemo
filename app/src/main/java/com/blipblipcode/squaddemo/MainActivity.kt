package com.blipblipcode.squaddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.blipblipcode.squaddemo.ui.graphNavigation.NavHosting
import com.blipblipcode.squaddemo.ui.theme.SquadDemoTheme
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
            // minimumFetchIntervalInSeconds = 3600
        })
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        super.onCreate(savedInstanceState)
        setContent {
            SquadDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHosting()
                }
            }
        }
    }
}



