package com.blipblipcode.squaddemo.data

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.blipblipcode.squaddemo.core.work.SyncUpWorker
import kotlinx.coroutines.flow.Flow

class SyncUpRepository (private val appContext: Context) {


    private val workManager = WorkManager.getInstance(appContext)

    fun liveData(): Flow<MutableList<WorkInfo>> {
       return workManager.getWorkInfosForUniqueWorkFlow(SyncUpWorker.TAG)
    }
    fun runWork() {
        Log.d("SyncUpWorker", "runWork: ")
        val workManager = workManager
        workManager.enqueueUniqueWork(
            "sync_work",
            ExistingWorkPolicy.REPLACE,
            SyncUpWorker.oneTimeWorkRequest()
        )
        Log.d("SyncUpWorker", "runWork: ")
    }

    fun runPeriodicWork() {
        val workManager = WorkManager.getInstance(appContext)
        workManager.enqueueUniquePeriodicWork(
            SyncUpWorker.TAG,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            SyncUpWorker.periodicWorkRequest()
        )
    }


}