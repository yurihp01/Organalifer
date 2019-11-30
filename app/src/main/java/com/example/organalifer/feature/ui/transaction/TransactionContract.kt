package com.example.organalifer.feature.ui.transaction

import android.widget.ProgressBar
import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Transaction

interface TransactionContract {
    interface View : BaseView {
        fun getTotalBalance(balance: Double)
    }

    interface Presenter : BasePresenter<View> {
        fun setTransaction(transaction: Transaction, progressBar: ProgressBar)
    }
}