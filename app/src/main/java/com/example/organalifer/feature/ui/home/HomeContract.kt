package com.example.organalifer.feature.ui.home

import android.widget.ProgressBar
import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Account

interface HomeContract {
    interface View: BaseView {
        fun getAccountList(arrayList: ArrayList<Account>)
        fun setTotalBalance(balance: Double)
        fun setPartialBalance(balance: Double)
    }

    interface Presenter: BasePresenter<View> {
        fun getAccountSpinnerList(progressBar: ProgressBar)
        fun getTotalBalance(progressBar: ProgressBar)
        fun getPartialBalance(name: String, progressBar: ProgressBar)
    }
}