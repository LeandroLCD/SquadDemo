package com.blipblipcode.squaddemo.data

import com.blipblipcode.squaddemo.core.room.entity.MeasureEntity
import com.blipblipcode.squaddemo.ui.home.models.MeasureModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun MeasureEntity.toMeasureModel(): MeasureModel {
    val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(date))
    return MeasureModel(id, formattedDate, name, measure, udm)
}

fun MeasureModel.toMeasureEntity(): MeasureEntity {
    val parsedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(date)
    val dateLong = parsedDate?.time ?: 0L
    return MeasureEntity(id, dateLong, name, measure, udm)
}
