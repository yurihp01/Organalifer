package com.example.organalifer.feature.ui.bills

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.organalifer.R
import com.example.organalifer.data.base.BaseActivity
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.data.net.FirebaseDatabase
import com.example.organalifer.feature.adapter.AccountStatementAdapter
import com.example.organalifer.feature.ui.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_account_statement.*
import java.util.*

class AccountStatementActivity : BaseActivity<AccountStatementPresenter>(),
    AccountStatementContract.View {
    override lateinit var presenter: AccountStatementPresenter
    private lateinit var recyclerView: RecyclerView

    override fun contentView(): Int = R.layout.activity_account_statement

    override fun init() {
        presenter = AccountStatementPresenter()
        presenter.view = this

        FirebaseDatabase.start()
        setSpinner()

        extract_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                setSelectedAdapter(p0!!.selectedItem.toString())
            }
        }

        extract_item.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                FirebaseDatabase.getTransactionsAccordingToOrder("transaction",
                    when (p0!!.selectedItem.toString()) {
                        "período" -> "periodicity"
                        "tipo de transação" -> "category"
                        else -> "type"
                    }, this@AccountStatementActivity)
            }
        }
    }

    override fun showError(throwable: Throwable) {
        println(throwable.message)
    }

    override fun setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getExtractsList(arrayList: ArrayList<Transaction>) {
        val viewAdapter = AccountStatementAdapter(arrayList)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_extract).apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }

    private fun setSpinner() {
        val extractAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_selectable_list_item,
            TransactionActivity.extractList
        )

        extract_type.adapter = extractAdapter
        extractAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    }

    private fun setSelectedAdapter(itemSelected: String) {

        val selectedExtractAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_selectable_list_item,
            when (itemSelected) {
                "tipo de transação" -> {
                    TransactionActivity.categoryList
                }
                "natureza de transação" -> {
                    TransactionActivity.transactionTypeList
                }
                else -> {
                    TransactionActivity.periodList
                }
            }
        )

        extract_item.adapter = selectedExtractAdapter
        selectedExtractAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

}