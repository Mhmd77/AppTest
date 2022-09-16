package com.example.testproject.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.testproject.data.Transaction

interface DataSource {
    fun observeTransactions(): LiveData<List<Transaction>>

    suspend fun refreshTransactions(mContext: Context)

}