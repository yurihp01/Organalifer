package com.example.organalifer.data.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<Presenter : BasePresenter<*>> : AppCompatActivity(), BaseView {
    abstract var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView())
        init()
    }

    abstract fun contentView(): Int
    abstract fun init()

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}