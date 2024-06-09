package com.blipblipcode.squaddemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.blipblipcode.squaddemo.data.SyncUpRepository
import com.blipblipcode.squaddemo.ui.graphNavigation.NavHosting
import com.blipblipcode.squaddemo.ui.theme.SquadDemoTheme
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig
    @Inject
    lateinit var syncUpRepository: SyncUpRepository

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido, puedes enviar notificaciones
        } else {
            // Permiso denegado, muestra un mensaje o toma una acción apropiada
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                    // Permiso ya concedido, puedes enviar notificaciones
                }
                else -> {
                    // Solicitar el permiso
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        super.onCreate(savedInstanceState)
        remoteConfig.let { remote ->
            remote.setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            })
            remote.setDefaultsAsync(R.xml.remote_config_defaults)
        }

        syncUpRepository.runWork()
        lifecycleScope.launch {
            syncUpRepository.liveData().collect{
                Toast.makeText(applicationContext, it[0].state.toString(), Toast.LENGTH_LONG).show()
            }
        }
        createNotificationChannel()

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

    private fun createNotificationChannel() {
        // Crea el canal de notificaciones solo para API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WorkManager Channel"
            val descriptionText = "Channel for WorkManager notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("WORK_MANAGER_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            // Registra el canal en el sistema
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}



