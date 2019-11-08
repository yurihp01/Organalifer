package com.example.organalifer.feature.transaction

import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.data.net.FirebaseDatabase

class TransactionPresenter : BasePresenter.Impl<TransactionContract.View>(), TransactionContract.Presenter {

    override fun setTransaction(transaction: Transaction) {
        FirebaseDatabase.saveData(
            DB_COLLECTION_NAME,
            transaction,
            FIRESTORE_SUCCESS_MESSAGE,
            view as BaseView
        )
    }

    companion object {
        const val DB_COLLECTION_NAME = "transactions"
        const val FIRESTORE_SUCCESS_MESSAGE = "Transação salva com sucesso!"
    }

}