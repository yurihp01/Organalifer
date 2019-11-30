package com.example.organalifer.data.net

import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Account
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.feature.ui.bills.AccountStatementContract
import com.example.organalifer.feature.ui.home.HomeContract
import com.example.organalifer.feature.ui.register.RegisterContract
import com.example.organalifer.feature.ui.transaction.TransactionContract
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

object FirebaseDatabase {
    private var firestore: FirebaseFirestore? = null
    private var totalBalance: Double = 0.0
    private var partialBalance: Double = 0.0

    fun start() : FirebaseFirestore =
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance()
            firestore!!
        } else {
            firestore!!
        }

    fun saveData(collection: String, data: Any, view: Any) {
        firestore?.apply {
            this.collection(collection).document().set(data)
                .addOnCompleteListener {
                    if (collection == "transaction") {
                        getTotalBalance(collection, view as TransactionContract.View)
                    }
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

    fun getAccountList(collection: String, view: HomeContract.View) {
        firestore?.apply {

            val docRef = this.collection(collection)
            docRef.get()
                .addOnSuccessListener { result ->
                    val arrayList = arrayListOf<Account>()

                    for (document in result) {
                        arrayList.add(document.toObject(Account::class.java))
                    }

                    view.getAccountList(arrayList)
                }
                .addOnFailureListener {
                    view.showError(it)
                }
        }
    }

    fun getTransactionsAccordingToOrder(collection: String, key: String, value: Any, view: AccountStatementContract.View) {
        val arrayList = arrayListOf<Transaction>()
        firestore?.apply {

            when (value) {
                is String -> {
                    this.collection(collection).whereEqualTo(key, value)
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                arrayList.add(document.toObject(Transaction::class.java))
                            }
                            view.getExtractsList(arrayList)
                        }
                        .addOnFailureListener {
                            view.showError(it)
                        }
                }
                else -> {
                    this.collection(collection).whereLessThanOrEqualTo(key, value)
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                val transaction = document.toObject(Transaction::class.java)
                                val date = SimpleDateFormat("dd/MM/yyyy").format(transaction.periodicity)

                                transaction.periodicity = SimpleDateFormat("dd/MM/yyyy").parse(date)
                                arrayList.add(transaction)
                            }
                            view.getExtractsList(arrayList)
                        }
                        .addOnFailureListener {
                            view.showError(it)
                        }
                }
            }
        }
    }

    //implementar esta funcionalidade para spinner selecionado
    fun getPartialBalance(collection: String, name: String, view: HomeContract.View) {
        val accountName: String = if (collection == "accounts") {
            "description"
        } else {
            "accountName"
        }

        val valueName: String = if (collection == "accounts") {
            "balance"
        } else {
            "value"
        }

        firestore?.apply {
            val docRef = this.collection(collection)
            docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.data[accountName] == name)
                            partialBalance += document.get(valueName)!!.toString().toDouble()
                    }

                    if (collection == "transactions") {
                        getPartialBalance("accounts", name, view)
                    } else {
                        view.setPartialBalance(partialBalance)
                        partialBalance = 0.0
                    }
                }
                .addOnFailureListener {
                    view.showError(it)
                }
        }
    }

     fun getTotalBalance(collection: String, view: BaseView) {
        var firestorePath: String

        firestore?.apply {

            val docRef = this.collection(collection)
            docRef.get()
                .addOnSuccessListener { result ->

                    if (collection == "accounts") {
                        firestorePath = "balance"

                        for (document in result) {
                            totalBalance += document.get(firestorePath)!!.toString().toDouble()
                        }

                        if (view is HomeContract.View) {
                            view.setTotalBalance(totalBalance)
                            totalBalance = 0.0
                        }

                    } else {
                        firestorePath = "value"

                        for (document in result) {
                            totalBalance += document.getDouble(firestorePath)!!
                        }

                        getTotalBalance("accounts", view)

                    }
                }
                .addOnFailureListener {
                    view.showError(it)
                }
        }
    }
}