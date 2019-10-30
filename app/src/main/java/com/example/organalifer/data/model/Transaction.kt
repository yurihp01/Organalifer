package com.example.organalifer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val accountId: Int,
    val description: String,
    val value: Double,
    val periodicity: String? = null
) : Parcelable