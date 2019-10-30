package com.example.organalifer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    val description: String,
    val balance: String
) : Parcelable