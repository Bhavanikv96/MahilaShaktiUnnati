package com.mindmatrix.mahilashakti.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromPaymentStatus(value: PaymentStatus): String = value.name

    @TypeConverter
    fun toPaymentStatus(value: String): PaymentStatus = PaymentStatus.valueOf(value)

    @TypeConverter
    fun fromRepaymentStatus(value: RepaymentStatus): String = value.name

    @TypeConverter
    fun toRepaymentStatus(value: String): RepaymentStatus = RepaymentStatus.valueOf(value)
}
