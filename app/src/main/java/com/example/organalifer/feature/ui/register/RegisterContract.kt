package com.example.organalifer.feature.ui.register

import android.widget.ProgressBar
import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Account

interface RegisterContract {
    interface View : BaseView {
        fun callIntent()
    }

    interface Presenter : BasePresenter<View> {
        fun saveAccount(account: Account, progressBar: ProgressBar)
    }
}