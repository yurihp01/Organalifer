package com.example.organalifer.feature.ui.bills

import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.activity_account_statement.periodity_image
import kotlinx.android.synthetic.main.activity_account_statement.periodity_input
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.fragment_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class AccountStatementActivity : BaseActivity<AccountStatementPresenter>(),
    AccountStatementContract.View {
    override lateinit var presenter: AccountStatementPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var extractItemKey: String

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
                p0?.apply {
                    val extractItem = this.selectedItem.toString()
                    extractItemKey = when (extract_type.selectedItem.toString()) {
                        "conta" -> {
                            periodity_image.visibility = View.VISIBLE
                            periodity_input.visibility = View.VISIBLE
                            "periodicity"
                        }
                        "tipo de transação" -> {
                            periodity_input.visibility = View.GONE
                            periodity_image.visibility = View.GONE
                            "category"
                        }
                        else -> {
                            periodity_image.visibility = View.GONE
                            periodity_input.visibility = View.GONE
                            "type"
                        }
                    }

                    if (periodity_image.visibility == View.GONE) {
                        presenter.getExtractsList(
                            "transactions",
                            extractItemKey,
                            extractItem,
                            this@AccountStatementActivity, progress_bar_asa
                        )
                    }
                }
            }
        }

        periodity_image.setOnClickListener {
            extractItemKey.apply {
                setDate(this)
            }
        }

        periodity_input.setOnClickListener {
            extractItemKey.apply {
                setDate(this)
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
        hideLoading(progress_bar_asa)
        val viewAdapter = AccountStatementAdapter(arrayList)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_extract).apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }

    private fun setDate(extractItemKey: String) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, mYear, monthOfYear, dayOfMonth ->
            val date = GregorianCalendar(mYear, monthOfYear, dayOfMonth).time
            presenter.getExtractsList(
                "transactions",
                extractItemKey,
                date,
                this@AccountStatementActivity,
                progress_bar_asa
            )

            periodity_input.text = SimpleDateFormat("dd/MM/yyyy", Locale("pt-br")).format(date)
        }, year, month, day)

        dpd.show()
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