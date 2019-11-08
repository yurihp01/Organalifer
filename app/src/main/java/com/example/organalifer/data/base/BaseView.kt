package com.example.organalifer.data.base

interface BaseView {
    fun showError(throwable: Throwable)
    fun setToast(message: String)
}