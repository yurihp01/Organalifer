package com.example.organalifer.feature.transaction

import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Transaction

interface TransactionContract {
    interface View : BaseView

    interface Presenter : BasePresenter<View> {
        fun setTransaction(transaction: Transaction)
    }
}