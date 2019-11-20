package com.example.organalifer.feature.ui.bills

import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.net.FirebaseDatabase

class AccountStatementPresenter : BasePresenter.Impl<AccountStatementContract.View>(), AccountStatementContract.Presenter {
    override fun getExtractsList(
        collection: String,
        order: String,
        view: AccountStatementContract.View
    ) {
        FirebaseDatabase.getTransactionsAccordingToOrder(collection, order, view)
    }
}