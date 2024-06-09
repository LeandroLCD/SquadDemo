package com.blipblipcode.squaddemo.ui.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import androidx.compose.ui.unit.Dp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Duration
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.TimeUnit

fun Float.toDp(): Float {
    return this / Resources.getSystem().displayMetrics.density
}
fun Dp.toInches(): Float {
    return this.toPx().toInches()
}
fun Float.toInches(): Float {
    return this / Resources.getSystem().displayMetrics.densityDpi

}
fun Float.toCm(): Float {
    return this.toInches() * inchToCmFactor

}
fun Float.inToPx(): Float {
    return this * Resources.getSystem().displayMetrics.densityDpi    // 1 = 2.54

}
fun Float.cmToPx(): Float {

    return (this / inchToCmFactor) * Resources.getSystem().displayMetrics.densityDpi

}

fun Dp.toPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.value,
        Resources.getSystem().displayMetrics
    )
}

fun Context.getPackageInfoCompat(flags: Int = 0): PackageInfo {
    val packageManager = packageManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(
            packageName,
            PackageManager.PackageInfoFlags.of(flags.toLong())
        )
    } else {
        packageManager.getPackageInfo(packageName, flags)
    }
}



@SuppressLint("SimpleDateFormat")
fun String.dateIsGreater(time:Long):Boolean{

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter =  DateTimeFormatter.ISO_DATE_TIME
        val recordDateTime = LocalDateTime.parse(this, formatter)
        val currentDateTime = LocalDateTime.now()
        val duration = Duration.between(recordDateTime, currentDateTime)
        val minutesDifference = duration.toMinutes()
        minutesDifference >= time
    } else {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val recordDate: Date? = dateFormat.parse(this)
        val currentDate = Date()
        val differenceInMillis = currentDate.time - recordDate!!.time
        val minutesDifference = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis)
        minutesDifference >= time
    }
}