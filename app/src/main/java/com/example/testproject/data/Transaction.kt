package com.example.testproject.data

import androidx.annotation.DrawableRes
import com.example.testproject.utilities.Utilities

data class Transaction(
    private var date: Long,
    var title: String,
    var type: Type,
    private var price: String,
    @DrawableRes var icon: Int
) {
    val priceString: String
        get() = Utilities.getPriceString(price)
    val dateString: String
        get() = Utilities.formatDate(date)

    enum class Type {
        Deposit, Internet, Withdraw, Transfer
    }
}