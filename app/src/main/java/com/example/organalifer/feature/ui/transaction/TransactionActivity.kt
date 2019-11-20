package com.example.organalifer.feature.ui.transaction

import android.app.Activity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.organalifer.R
import com.example.organalifer.data.base.BaseActivity
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.feature.ui.home.HomeActivity.Companion.TRANSACTION_KEY
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : BaseActivity<TransactionContract.Presenter>(), TransactionContract.View {
    override fun getTotalBalance(balance: Double) {
        this.balance = balance
    }


    override fun setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override lateinit var presenter: TransactionContract.Presenter
    private var balance: Double = 0.0


    override fun contentView() = R.layout.activity_transaction

    override fun init() {
        presenter = TransactionPresenter()
        presenter.view = this

        setAdapters()

        lateinit var transaction: Transaction

        transaction_repeatable_group.setOnCheckedChangeListener { _, id ->
            when (id) {
                yes_radio_button.id -> periodity_input.visibility = View.VISIBLE
                else -> periodity_input.visibility = View.GONE
            }
        }

        transaction_button.setOnClickListener {
            when (transaction_payment_group.checkedRadioButtonId) {
                doubt_radio_button.id -> {

                    transaction = Transaction(
                        account_spinner.selectedItem.toString(),
                        description_input.editText!!.text.toString(),
                        category_spinner.selectedItem.toString(),
                        doubt_radio_button.text.toString(),
                        value_input.editText!!.text.toString().toDouble().unaryMinus(),
                        periodity_input.editText!!.text.toString()
                    )
                }
                else -> {
                    transaction = Transaction(
                        account_spinner.selectedItem.toString(),
                        description_input.editText!!.text.toString(),
                        category_spinner.selectedItem.toString(),
                        doubt_radio_button.text.toString(),
                        value_input.editText!!.text.toString().toDouble(),
                        periodity_input.editText!!.text.toString()
                    )
                }
            }

            presenter.setTransaction(transaction)
            intent.putExtra(TRANSACTION_KEY, balance)
            setResult(Activity.RESULT_OK, intent)
            finish()

            // calculado os valores no debito e credito
            // foi feito a logica da visibilidade se for repetivel.
            // fazer conexao com o banco e o resto kkkk
        }
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.apply {
            setToast(this)
        }
    }


    companion object {
        val categoryList = listOf(
            "alimentação", "saúde", "transporte", "moradia", "educação", "lazer",
            "tarifas bancárias", "luz", "água", "telefone", "outros")

        val extractList = listOf("conta", "natureza de transação", "tipo de transação")

        val transactionTypeList = listOf("débito", "crédito")

        val periodList = listOf("período")
    }

    // seta ambos os adapters do layout
    private fun setAdapters() {
        val categoryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_selectable_list_item,
            categoryList
        )

        val accountAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_selectable_list_item,
            intent.getStringArrayListExtra("list")
        )

        account_spinner.adapter = accountAdapter
        category_spinner.adapter = categoryAdapter

        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

}
