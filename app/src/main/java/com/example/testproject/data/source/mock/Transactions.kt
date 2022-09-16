package com.example.testproject.data.source.mock

import android.content.Context
import com.example.testproject.R
import com.example.testproject.data.Transaction

object Transactions {
    fun createMockData(context: Context): ArrayList<Transaction> {
        val transactions = ArrayList<Transaction>()
        for (i in 0..4) {
            with(
                Transaction(
                    System.currentTimeMillis(),
                    context.getString(R.string.Deposit),
                    Transaction.Type.Deposit,
                    "150000",
                    R.drawable.ic_atm
                )
            ) { transactions.add(this) }
        }
        for (i in 0..4) {
            with(
                Transaction(
                    System.currentTimeMillis(),
                    context.getString(R.string.Internet),
                    Transaction.Type.Internet,
                    "220000",
                    R.drawable.ic_fee
                )
            ) { transactions.add(this) }
        }
        for (i in 0..4) {
            with(
                Transaction(
                    System.currentTimeMillis(),
                    context.getString(R.string.Withdraw),
                    Transaction.Type.Withdraw,
                    "3790000",
                    R.drawable.ic_deposit_gift
                )
            )
            { transactions.add(this) }
        }
        for (i in 0..4) {
            with(
                Transaction(
                    System.currentTimeMillis(),
                    context.getString(R.string.Transfer),
                    Transaction.Type.Transfer,
                    "380000",
                    R.drawable.ic_remittance_transfer
                )
            )
            { transactions.add(this) }
        }
        return transactions
    }
}