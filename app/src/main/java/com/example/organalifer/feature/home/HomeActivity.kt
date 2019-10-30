package com.example.organalifer.feature.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.organalifer.R
import com.example.organalifer.data.model.Account
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.feature.bills.AccountStatementActivity
import com.example.organalifer.feature.register.RegisterActivity
import com.example.organalifer.feature.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        const val REGISTER_CODE = 2
        const val TRANSACTION_CODE = 3
        const val ACCOUNT_KEY = "ACCOUNT_KEY"
        const val TRANSACTION_KEY = "TRANSACTION_KEY"
    }

    private var accountsValue = 0.0
    private var accountsList = arrayListOf<Account>()

    override fun onResume() {
        super.onResume()

        general_account_value.text = "Saldo entre Contas: R$${accountsValue}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setAccountsSpinner()

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
            startActivityForResult(
                Intent(this@HomeActivity, TransactionActivity::class.java),
                TRANSACTION_CODE
            )
        }

        financial_transaction_image.setOnClickListener {
            startActivityForResult(
                Intent(this@HomeActivity, TransactionActivity::class.java),
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // SALVAR AMBOS NO BANCO DE DADOS PARA ARMAZENA-LOS E ACESSA-LOS DEPOIS.
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REGISTER_CODE -> {
                    data?.run {
                        this.extras?.getParcelable<Account>(ACCOUNT_KEY)?.run {
                            accountsList.add(this)
                            accountsValue += this.balance.toDouble()
                            setAccountsSpinner()
                        }
                    }
                }
                TRANSACTION_CODE -> {
                    data?.run {
                        this.extras?.getParcelable<Transaction>(TRANSACTION_KEY)?.run {
                            accountsValue += this.value
                        }
                    }
                }
            }
        }
    }

    private fun setAccountsSpinner() {
        if (accountsList.isNotEmpty()) {
            accounts_spinner.isEnabled = true
            financial_transaction.isEnabled = true
            financial_transaction_image.isEnabled = true
            val accountsDescription = arrayListOf<String>()
            for (a in accountsList) {
                accountsDescription.add(a.description)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, accountsDescription)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            accounts_spinner.adapter = adapter
        } else {
            accounts_spinner.isEnabled = false
            financial_transaction.isEnabled = false
            financial_transaction_image.isEnabled = false
        }

    }
}
