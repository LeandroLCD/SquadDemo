package com.blipblipcode.squaddemo.ui.home.models

import com.blipblipcode.squaddemo.ui.home.Udm
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MeasureModel(
    val id: Int = 0,
    val date: String = getCurrentDate(),
    val name: String,
    val measure: Float,
    val udm: Udm
) {
    companion object {
        private fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}