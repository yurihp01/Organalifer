package com.example.organalifer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction constructor(
    val accountName: String = "",
    val description: String = "",
    val category: String = "",
    val type: String = "",
    val value: Double = 0.0,
    val periodicity: String? = null
) : Parcelable