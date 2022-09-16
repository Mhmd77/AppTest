package com.example.testproject.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.R
import com.example.testproject.databinding.CellTransactionBinding
import com.example.testproject.data.Transaction

class TransactionAdapter(
    private val mContext: Context
) :
    RecyclerView.Adapter<TransactionAdapter.Holder>() {

    private var transactionsList: ArrayList<Transaction> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CellTransactionBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(transactionsList[position])
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }

    fun setData(data: List<Transaction>) {
        transactionsList.clear()
        transactionsList.addAll(data)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: CellTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction) {
            with(binding) {
                imageViewIcon.setImageResource(item.icon)
                textViewTitle.text = item.title
                textViewPrice.text = item.priceString
                textViewDate.text = item.dateString
                textViewPrice.setBackgroundColor(
                    if (item.type == Transaction.Type.Withdraw) ContextCompat.getColor(
                        mContext,
                        R.color.highlight_tint_color
                    ) else 0
                )
            }
        }
    }
}