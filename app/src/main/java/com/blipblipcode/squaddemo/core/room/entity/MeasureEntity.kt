package com.blipblipcode.squaddemo.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blipblipcode.squaddemo.ui.home.Udm

@Entity
data class MeasureEntity(
    @PrimaryKey(true)
    val id: Int,
    val date: Long,
    val name: String,
    val measure: Float,
    val udm: Udm
)