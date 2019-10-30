package com.example.organalifer.feature.transaction

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.organalifer.R
import com.example.organalifer.data.model.Transaction
import com.example.organalifer.feature.home.HomeActivity
import com.example.organalifer.feature.home.HomeActivity.Companion.TRANSACTION_KEY
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        var isPeriodicityRepeatable: Boolean
        lateinit var transaction: Transaction

        transaction_button.setOnClickListener {
            transaction_repeatable_group.setOnCheckedChangeListener { _, id ->
                when (id) {
                    yes_radio_button.id -> {
                        transaction_payment_group.setOnCheckedChangeListener { _, id ->
                            when (id) {
                                doubt_radio_button.id -> {
                                    transaction = Transaction(0,//getAccountId(),
                                        description_input.editText.toString(),
                                        value_input.editText.toString().toDouble().unaryMinus(),
                                        periodity_input.editText.toString())
                                }
                                else -> {
                                    transaction = Transaction(0,//getAccountId(),
                                        description_input.editText.toString(),
                                        value_input.editText.toString().toDouble(),
                                        periodity_input.editText.toString())
                                }
                            }
                        }
                    }
                    else -> {
                        transaction_payment_group.setOnCheckedChangeListener { _, id ->
                            when (id) {
                                doubt_radio_button.id -> {
                                    transaction = Transaction(0,//getAccountId(),
                                        description_input.editText.toString(),
                                        value_input.editText.toString().toDouble().unaryMinus())
                                }
                                else -> {
                                    transaction = Transaction(0,//getAccountId(),
                                        description_input.editText.toString(),
                                        value_input.editText.toString().toDouble())
                                }
                            }
                        }
                    }
                }

                intent.putExtra(TRANSACTION_KEY, transaction)
                setResult(Activity.RESULT_OK, intent)
                finishActivity(3)
            }

            // calculado os valores no debito e credito
            // foi feito a logica da visibilidade se for repetivel.
            // fazer conexao com o banco e o resto kkkk
        }

    }

}
