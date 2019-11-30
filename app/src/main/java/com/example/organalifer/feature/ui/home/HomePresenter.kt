package com.example.organalifer.feature.ui.home

import android.widget.ProgressBar
import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.net.FirebaseDatabase

class HomePresenter : BasePresenter.Impl<HomeContract.View>(), HomeContract.Presenter {
    override fun getPartialBalance(name: String, progressBar: ProgressBar) {
        view!!.showLoading(progressBar)
        FirebaseDatabase.getPartialBalance("transactions", name, view!!)
    }

    override fun getTotalBalance(progressBar: ProgressBar) {
        view!!.showLoading(progressBar)
        FirebaseDatabase.getTotalBalance("transactions", view as BaseView)
    }

    override fun getAccountSpinnerList(progressBar: ProgressBar) {
        view!!.showLoading(progressBar)
        FirebaseDatabase.getAccountList("accounts", view as HomeContract.View)
    }
}