package com.example.organalifer.feature.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.organalifer.R
import com.example.organalifer.data.model.Account
import com.example.organalifer.feature.home.HomeActivity.Companion.ACCOUNT_KEY
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            val account = Account(
                description = account_input.editText!!.text.toString(),
                balance = value_input.editText!!.text.toString()
            )

            intent.putExtra(ACCOUNT_KEY, account)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
