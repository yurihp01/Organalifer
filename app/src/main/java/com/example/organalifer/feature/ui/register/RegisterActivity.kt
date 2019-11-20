package com.example.organalifer.feature.ui.register

import android.app.Activity
import android.widget.Toast
import com.example.organalifer.R
import com.example.organalifer.data.base.BaseActivity
import com.example.organalifer.data.model.Account
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity() : BaseActivity<RegisterContract.Presenter>(), RegisterContract.View {
    override lateinit var presenter: RegisterContract.Presenter


    override fun contentView(): Int = R.layout.activity_register

    override fun init() {
        presenter = RegisterPresenter()
        presenter.view = this

        register_button.setOnClickListener {
            val account = Account(
                description = account_input.editText!!.text.toString(),
                balance = value_input.editText!!.text.toString()
            )

            presenter.saveAccount(account)
        }
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.apply {
            setToast(this)
        }

    }

    override fun setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun callIntent() {
        setToast("Conta salva com sucesso!")
        setResult(Activity.RESULT_OK)
        finish()
    }
}
