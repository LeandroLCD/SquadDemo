package com.blipblipcode.squaddemo.ui.start

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blipblipcode.squaddemo.ui.start.state.SplashUiState
import com.blipblipcode.squaddemo.ui.utilities.getPackageInfoCompat
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    @ApplicationContext private val context: Context
) : ViewModel() {
    //region Field - States
    var urlPlayStore by mutableStateOf("")
        private set

    var uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
        private set
    //endregion

    //region Methods
    fun onCheckedVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val versionMinima = remoteConfig.getString("version_minima")
                    urlPlayStore = remoteConfig.getString("url_play_store")
                    Log.d("SplashScreen", "versionMinima: $versionMinima")
                    Log.d("SplashScreen", "urlPlayStore: $urlPlayStore")

                    val version = context.getPackageInfoCompat().versionName

                    val compare = versionMinima.compareTo(version)
                    if (compare > 0) {
                        val defaults = hashMapOf<String, Any>(
                            "version_minima" to versionMinima,
                            "url_play_store" to urlPlayStore
                        )
                        val id = context.resources.getIdentifier(
                            "remote_config_defaults",
                            "xml",
                            context.packageName
                        )
                        val inputStream =
                            context.resources.openRawResource(id)
                        val xmlBytes = inputStream.readBytes()
                        val outputStream =
                            context.openFileOutput(
                                "remote_config_defaults.xml",
                                Context.MODE_PRIVATE
                            )
                        outputStream.write(xmlBytes)
                        for ((key, value) in defaults) {
                            outputStream.write("<entry>\n<key>$key</key>\n<value>$value</value>\n</entry>\n".toByteArray())
                        }
                        outputStream.flush()
                        outputStream.close()
                        inputStream.close()

                        viewModelScope.launch(Dispatchers.Main) {

                            uiState.emit(SplashUiState.RequireUpdate)
                        }

                    } else {
                        viewModelScope.launch(Dispatchers.Main) {
                            Log.d("TAG", "onCheckedVersion: ${task.isSuccessful}")
                            uiState.emit(SplashUiState.Navigate)
                        }
                    }

                } else {
                    Log.d("TAG", "onCheckedVersion: ${task.isComplete}")
                }


            }
        }
    }

    //region
}