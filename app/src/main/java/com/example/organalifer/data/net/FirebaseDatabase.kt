package com.example.organalifer.data.net

import com.example.organalifer.data.base.BaseView
import com.example.organalifer.feature.register.RegisterContract
import com.example.organalifer.feature.transaction.TransactionContract
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDatabase {
    private var firestore: FirebaseFirestore? = null

    fun start() : FirebaseFirestore =
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance()
            firestore!!
        } else {
            firestore!!
        }

    fun saveData(collection: String, data: Any, message: String, view: Any) {
        firestore?.apply {
            this.collection(collection).add(data)
                .addOnCompleteListener {
                    selectClass(view)
                }
                .addOnFailureListener {
                    (view as BaseView).showError(it)
                }
        }


    }

    private fun selectClass(view: Any) {
        when(view) {
            is RegisterContract.View -> {
                view.callIntent()
            }
            is TransactionContract.View -> {
                view.setToast("Transação salva por sucesso")
            }
        }
    }

    fun getData() : Any? {
        return null
    }

}