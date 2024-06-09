package com.blipblipcode.squaddemo.core.work

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.blipblipcode.squaddemo.R
import com.blipblipcode.squaddemo.data.MeasureRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.Date
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncUpWorker @AssistedInject constructor(@Assisted private val appContext:Context,
                    @Assisted workerParams: WorkerParameters,
    private val measureRepository: MeasureRepository):
    CoroutineWorker(appContext, workerParams) {

        val TAG = "SyncUpWorker"
    @SuppressLint("SuspiciousIndentation")
    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: Running")
        showNotification(TAG, " b")
       val list =  measureRepository.getAllMeasure().first()

           list.forEach {
                Log.d(TAG, it.toString())
                showNotification(TAG, "hay ")
            }
        showNotification(TAG, "hay ${list.size}, en la base de datos. ${Date()}")
        return Result.success()
    }

    companion object {
        val TAG = "SyncUpWorker"

        private const val DEFAULT_MIN_INTERVAL = 5L

        fun oneTimeWorkRequest(): OneTimeWorkRequest {
            val constrains = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            return OneTimeWorkRequestBuilder<SyncUpWorker>()
                .setConstraints(constrains)
                .addTag("sync_up_worker")
                .build()
        }

        fun periodicWorkRequest(): PeriodicWorkRequest {
            val constrains = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            return PeriodicWorkRequestBuilder<SyncUpWorker>(
                DEFAULT_MIN_INTERVAL,
                TimeUnit.MINUTES
            ).setConstraints(constrains)
                .build()
        }

    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification: Notification = NotificationCompat.Builder(applicationContext, "WORK_MANAGER_CHANNEL")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(androidx.core.R.drawable.notification_bg) // Usa un ícono válido de tu aplicación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }

}