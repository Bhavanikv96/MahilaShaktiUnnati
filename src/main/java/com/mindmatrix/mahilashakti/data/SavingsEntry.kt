package com.mindmatrix.mahilashakti.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "savings",
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
data class SavingsEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val memberId: Long,
    val amount: Double,
    val status: PaymentStatus,
    val date: String
)

enum class PaymentStatus {
    PAID,
    PENDING
}
