package com.example.organalifer.data.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<Presenter : BasePresenter<*>> : AppCompatActivity(), BaseView {
    abstract var presenter: Presenter

    private var progressBar: ProgressBar? = null

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

    override fun hideLoading(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

    override fun showLoading(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }
}