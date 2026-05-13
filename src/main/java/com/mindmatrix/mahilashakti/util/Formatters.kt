package com.mindmatrix.mahilashakti.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Formatters {
    private val currency = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

    fun money(value: Double): String = currency.format(value)

    fun today(): String = dateFormat.format(Date())
}
