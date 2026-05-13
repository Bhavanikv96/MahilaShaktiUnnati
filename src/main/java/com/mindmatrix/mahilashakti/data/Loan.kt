package com.mindmatrix.mahilashakti.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "loans",
    foreignKeys = [
        ForeignKey(
            entity = Member::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("memberId")]
)
data class Loan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val memberId: Long,
    val amount: Double,
    val interestRate: Double,
    val dueDate: String,
    val status: RepaymentStatus
) {
    val interestAmount: Double
        get() = amount * interestRate / 100.0

    val totalDue: Double
        get() = amount + interestAmount
}

enum class RepaymentStatus {
    ACTIVE,
    REPAID
}
