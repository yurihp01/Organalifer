package com.example.organalifer.feature.ui.home

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
        fun getAccountSpinnerList()
        fun getTotalBalance()
        fun getPartialBalance(name: String)
    }
}