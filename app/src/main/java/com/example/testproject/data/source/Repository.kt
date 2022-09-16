package com.example.testproject.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.testproject.data.Transaction
import com.example.testproject.data.source.mock.MockDataSource

class Repository(private val mockDataSource: MockDataSource) {
    suspend fun refreshTransactions(mContext: Context) {
        mockDataSource.refreshTransactions(mContext)
    }

    fun observeTransactionList(): LiveData<List<Transaction>> {
        return mockDataSource.observeTransactions()
    }
}