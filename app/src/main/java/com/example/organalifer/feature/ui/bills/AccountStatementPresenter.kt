package com.example.organalifer.feature.ui.bills

import android.widget.ProgressBar
import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.net.FirebaseDatabase

class AccountStatementPresenter : BasePresenter.Impl<AccountStatementContract.View>(), AccountStatementContract.Presenter {
    override fun getExtractsList(
        collection: String,
        key: String,
        value: Any,
        view: AccountStatementContract.View,
        progressBar: ProgressBar
    ) {
        view.showLoading(progressBar)
        FirebaseDatabase.getTransactionsAccordingToOrder(collection, key, value, view)
    }
}