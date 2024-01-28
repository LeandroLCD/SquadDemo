package com.blipblipcode.squaddemo.ui.start

import android.annotation.SuppressLint
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @SuppressLint("StaticFieldLeak")
@Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val context: Context,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {
    //region Field - States
    var urlPlayStore by mutableStateOf("")
        private set

    var uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
        private set
    //endregion

    //region Methods
    fun onCheckedVersion() {
        viewModelScope.launch(dispatcherIO) {
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
                            delay(2000)
                            uiState.emit(SplashUiState.RequireUpdate)
                        }

                    } else {
                        viewModelScope.launch(Dispatchers.Main) {
                            delay(2000)
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