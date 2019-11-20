package com.example.organalifer.feature.ui.register

import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Account
import com.example.organalifer.data.net.FirebaseDatabase

class RegisterPresenter : RegisterContract.Presenter, BasePresenter.Impl<RegisterContract.View>() {

    override fun saveAccount(account: Account) {
        FirebaseDatabase.saveData(
            DB_COLLECTION_NAME,
            account,
            view as BaseView
        )
    }

    companion object {
        const val DB_COLLECTION_NAME = "accounts"
        const val FIRESTORE_SUCCESS_MESSAGE = "Conta salva com sucesso!"
    }


}