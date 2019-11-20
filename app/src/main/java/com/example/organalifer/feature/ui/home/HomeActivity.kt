package com.example.organalifer.feature.ui.home

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.organalifer.R
import com.example.organalifer.data.base.BaseActivity
import com.example.organalifer.data.model.Account
import com.example.organalifer.data.net.FirebaseDatabase
import com.example.organalifer.feature.ui.bills.AccountStatementActivity
import com.example.organalifer.feature.ui.register.RegisterActivity
import com.example.organalifer.feature.ui.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<HomePresenter>(), HomeContract.View {
    override lateinit var presenter: HomePresenter

    private var accountsValue = 0.0
    private var accountsList = arrayListOf<String>()

    override fun setPartialBalance(balance: Double) {
        account_value.text = "Saldo da conta $balance"
    }

    override fun setTotalBalance(balance: Double) {
        accountsValue = balance
        general_account_value.text = "Saldo entre Contas: R$${accountsValue}"
    }

    override fun contentView(): Int = R.layout.activity_home

    override fun init() {
        presenter = HomePresenter()
        presenter.view = this

        FirebaseDatabase.start()
        setPresenter()
        setOnClickListeners()
        accounts_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                presenter.getPartialBalance(p0!!.getItemAtPosition(p2).toString())
            }

        }
    }

    override fun showError(throwable: Throwable) {
        setToast(throwable.message!!)
    }

    override fun setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.getAccountSpinnerList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // SALVAR AMBOS NO BANCO DE DADOS PARA ARMAZENA-LOS E ACESSA-LOS DEPOIS.
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REGISTER_CODE -> {
                    // activity result
                }
                TRANSACTION_CODE -> {
                    data?.run {
                        this.extras?.getDouble(TRANSACTION_KEY)?.run {
                            println("Transaction Voltou!")
                        }
                    }
                }
            }
        }
    }

    override fun getAccountList(arrayList: ArrayList<Account>) {
        accountsList.clear()
        for (a in arrayList) {
                accountsList.add(a.description)
        }

        presenter.getTotalBalance()
        setSpinner()
    }

    // Retorna a lista de Contas para o Spinner


    // Orquestra o Spinner
    private fun setSpinner() {
        val isAccountListEmpty = accountsList.isNotEmpty()

        if (accountsList.isNotEmpty()) {
            setTransactionButtonAndSpinnerEnable(isAccountListEmpty)
            setAdapter()
        } else {
            setTransactionButtonAndSpinnerEnable(accountsList.isNotEmpty())
        }
    }

    // seta o adapter
    private fun setAdapter() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            accountsList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accounts_spinner.adapter = adapter
    }

    // (Des)Habilita os componentes de acordo com a lista
    private fun setTransactionButtonAndSpinnerEnable(isEnabled: Boolean) {
        financial_transaction.isEnabled = isEnabled
        financial_transaction_image.isEnabled = isEnabled
        accounts_spinner.isEnabled = isEnabled
    }

    private fun setPresenter() {
        presenter = HomePresenter()
        presenter.view = this
    }

    // Seta os eventos de Clique
    private fun setOnClickListeners() {
        register_account.setOnClickListener {
            startActivityForResult(
                Intent(this@HomeActivity, RegisterActivity::class.java),
                REGISTER_CODE
            )
        }

        register_account_image.setOnClickListener {
            startActivityForResult(
                Intent(this@HomeActivity, RegisterActivity::class.java),
                REGISTER_CODE
            )
        }

        financial_transaction.setOnClickListener {
            val intent = Intent(this@HomeActivity, TransactionActivity::class.java)
            intent.putExtra("list", accountsList)
            startActivityForResult(
                intent,
                TRANSACTION_CODE
            )
        }

        financial_transaction_image.setOnClickListener {
            val intent = Intent(this@HomeActivity, TransactionActivity::class.java)
            intent.putExtra("list", accountsList)
            startActivityForResult(
                intent,
                TRANSACTION_CODE
            )
        }

        account_statement_image.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AccountStatementActivity::class.java))
        }

        account_statement.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AccountStatementActivity::class.java))
        }
    }

    companion object {
        const val REGISTER_CODE = 2
        const val TRANSACTION_CODE = 3
        const val ACCOUNT_KEY = "ACCOUNT_KEY"
        const val TRANSACTION_KEY = "TRANSACTION_KEY"
    }
}
