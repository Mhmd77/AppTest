package com.example.testproject.utilities

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.lang.StringBuilder

object Utilities {
    fun formatDate(date: Long): String {
        val persianDate = PersianDate(date)
        val persianDateFormat =
            PersianDateFormat("l j F H:i Y", PersianDateFormat.PersianDateNumberCharacter.FARSI)
        return persianDateFormat.format(persianDate)
    }

    fun getPriceString(s: String): String {
        val result = StringBuilder()
        var counter = 0
        val space = ","
        val clean: String = s.replace(space, "", true)
        for (i in clean.length - 1 downTo 0) {
            result.append(clean[i])
            counter++
            if (counter == 3) {
                result.append(space)
                counter = 0
            }
        }
        if (result.length > 3) {
            val end = result[result.length - 1]
            if (end == space[0]) {
                result.deleteCharAt(result.length - 1)
            }
        }
        return toPersian(result.reverse().toString()) + " ریال"
    }

    private fun toPersian(c: String): String {
        val enN = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val faN = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
        var result: String = c
        enN.zip(faN).forEach {
            result = result.replace(it.first, it.second)
        }
        return result
    }
}