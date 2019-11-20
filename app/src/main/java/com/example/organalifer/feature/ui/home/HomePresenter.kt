package com.example.organalifer.feature.ui.home

import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.net.FirebaseDatabase

class HomePresenter : BasePresenter.Impl<HomeContract.View>(), HomeContract.Presenter {
    override fun getPartialBalance(name: String) {
        FirebaseDatabase.getPartialBalance("transactions", name, view!!)
    }

    override fun getTotalBalance() {
        FirebaseDatabase.getTotalBalance("transactions", view as BaseView)
    }

    override fun getAccountSpinnerList() {
        FirebaseDatabase.getAccountList("accounts", view as HomeContract.View)
    }
}