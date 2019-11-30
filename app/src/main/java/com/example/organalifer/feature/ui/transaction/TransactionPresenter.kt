package com.example.organalifer.feature.ui.transaction

import android.widget.ProgressBar
import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.data.net.FirebaseDatabase

class TransactionPresenter : BasePresenter.Impl<TransactionContract.View>(), TransactionContract.Presenter {

    override fun setTransaction(transaction: Transaction, progressBar: ProgressBar) {
        view!!.showLoading(progressBar)
        FirebaseDatabase.saveData(
            DB_COLLECTION_NAME,
            transaction,
            view as BaseView
        )
    }

    companion object {
        const val DB_COLLECTION_NAME = "transactions"
    }

}