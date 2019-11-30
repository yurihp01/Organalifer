package com.example.organalifer.data.base

import android.widget.ProgressBar

interface BaseView {
    fun showError(throwable: Throwable)
    fun setToast(message: String)
    fun hideLoading(progressBar: ProgressBar)
    fun showLoading(progressBar: ProgressBar)
}