package com.example.organalifer.feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.organalifer.R
import com.example.organalifer.data.model.Transaction
import kotlinx.android.synthetic.main.fragment_card.view.*

class AccountStatementAdapter(private val extractList: ArrayList<Transaction>) :
    RecyclerView.Adapter<AccountStatementAdapter.AccountStatementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountStatementViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_card, parent, false)
        return AccountStatementViewHolder(view)
    }


    override fun getItemCount(): Int = extractList.size

    override fun onBindViewHolder(holder: AccountStatementViewHolder, position: Int) {
        val transaction = extractList[position]
        val view = holder.view
        view.transaction_name.text = transaction.accountName
        view.transaction_category.text = transaction.category
        view.transaction_description.text = transaction.description
        view.transaction_periodicity.text = transaction.periodicity ?: "-"
        view.transaction_value.text = """R$${transaction.value}"""
        view.transaction_type.text = transaction.type
    }

    class AccountStatementViewHolder(val view: View): RecyclerView.ViewHolder(view)
}