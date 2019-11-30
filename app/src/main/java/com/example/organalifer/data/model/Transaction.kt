package com.example.organalifer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Transaction constructor(
    val accountName: String = "",
    val description: String = "",
    val category: String = "",
    val type: String = "",
    val value: Double = 0.0,
    var periodicity: Date? = null
) : Parcelable