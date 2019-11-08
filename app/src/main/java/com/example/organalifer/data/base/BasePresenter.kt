package com.example.organalifer.data.base

interface BasePresenter<View : BaseView> {
    var view: View?

    fun detach()

    abstract class Impl<View: BaseView> : BasePresenter<View> {
        override var view: View? = null

        override fun detach() {
            view = null
        }


        protected fun showError(throwable: Throwable) {
            view?.showError(throwable)
        }
    }
}