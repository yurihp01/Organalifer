package com.example.organalifer.feature.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.organalifer.R
import com.example.organalifer.data.model.Account
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.data.net.FirebaseDatabase
import com.example.organalifer.feature.bills.AccountStatementActivity
import com.example.organalifer.feature.register.RegisterActivity
import com.example.organalifer.feature.transaction.TransactionActivity
import com.google.firebase.firestore.FirebaseFirestore
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
        FirebaseDatabase.start()
        setContentView(R.layout.activity_home)

        setSpinner()

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
            intent.putExtra("list", getAccountSpinnerList())
            startActivityForResult(
                intent,
                TRANSACTION_CODE
            )
        }

        financial_transaction_image.setOnClickListener {
            val intent = Intent(this@HomeActivity, TransactionActivity::class.java)
            intent.putExtra("list", getAccountSpinnerList())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // SALVAR AMBOS NO BANCO DE DADOS PARA ARMAZENA-LOS E ACESSA-LOS DEPOIS.
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REGISTER_CODE -> {
                    data?.run {
                        this.extras?.getParcelable<Account>(ACCOUNT_KEY)?.run {
                            // passar aqui as contas salvas no firebase
                            accountsList.add(this)
                            accountsValue += this.balance.toDouble()
                            setSpinner()
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

    // Retorna a lista de Contas para o Spinner
    private fun getAccountSpinnerList() : ArrayList<String> {
        val accountsDescription = arrayListOf<String>()
        for (a in accountsList) {
            accountsDescription.add(a.description)
        }
        return accountsDescription
    }

    // Orquestra o Spinner
    private fun setSpinner() {
        val isAccountListEmpty = accountsList.isNotEmpty()

        if (isAccountListEmpty) {
            setTransactionButtonAndSpinnerEnable(isAccountListEmpty)
            setAdapter()
        } else {
            setTransactionButtonAndSpinnerEnable(accountsList.isNotEmpty())
        }
    }

    // seta o adapter
    private fun setAdapter() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getAccountSpinnerList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accounts_spinner.adapter = adapter
    }

    // (Des)Habilita os componentes de acordo com a lista
    private fun setTransactionButtonAndSpinnerEnable(isEnabled: Boolean) {
        financial_transaction.isEnabled = isEnabled
        financial_transaction_image.isEnabled = isEnabled
        accounts_spinner.isEnabled = isEnabled
    }
}
