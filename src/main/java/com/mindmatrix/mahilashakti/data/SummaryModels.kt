package com.mindmatrix.mahilashakti.data

data class MemberSavingsSummary(
    val memberId: Long,
    val memberName: String,
    val totalPaid: Double,
    val pendingCount: Int
)

data class LoanWithMember(
    val loanId: Long,
    val memberName: String,
    val amount: Double,
    val interestRate: Double,
    val dueDate: String,
    val status: RepaymentStatus
) {
    val totalDue: Double
        get() = amount + (amount * interestRate / 100.0)
}
