package com.example.testproject.data.source.mock

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testproject.data.Transaction
import com.example.testproject.data.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MockDataSource() : DataSource {
    private val transactionsLiveData = MutableLiveData<List<Transaction>>()

    override fun observeTransactions(): LiveData<List<Transaction>> {
        return transactionsLiveData
    }

    override suspend fun refreshTransactions(mContext: Context) {
        delay(3000)
        val array = Transactions.createMockData(mContext).also { it.shuffle() }
        transactionsLiveData.value = array
    }
}