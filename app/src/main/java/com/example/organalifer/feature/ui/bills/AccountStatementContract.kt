package com.example.organalifer.feature.ui.bills

import com.example.organalifer.data.base.BasePresenter
import com.example.organalifer.data.base.BaseView
import com.example.organalifer.data.model.Transaction
import java.util.ArrayList

interface AccountStatementContract {
    interface View : BaseView {
        fun getExtractsList(arrayList: ArrayList<Transaction>)
    }

    interface Presenter : BasePresenter<View> {
        fun getExtractsList(collection: String, order: String, view: View)
    }
}