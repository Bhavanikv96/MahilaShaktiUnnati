package com.mindmatrix.mahilashakti.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppDao {
    @Insert
    fun insertMember(member: Member): Long

    @Query("SELECT * FROM members ORDER BY name")
    fun getMembers(): List<Member>

    @Query("SELECT COUNT(*) FROM members")
    fun countMembers(): Int

    @Insert
    fun insertSavings(entry: SavingsEntry): Long

    @Query("SELECT COALESCE(SUM(amount), 0) FROM savings WHERE status = 'PAID'")
    fun totalPaidSavings(): Double

    @Query(
        """
        SELECT members.id AS memberId,
               members.name AS memberName,
               COALESCE(SUM(CASE WHEN savings.status = 'PAID' THEN savings.amount ELSE 0 END), 0) AS totalPaid,
               COALESCE(SUM(CASE WHEN savings.status = 'PENDING' THEN 1 ELSE 0 END), 0) AS pendingCount
        FROM members
        LEFT JOIN savings ON members.id = savings.memberId
        GROUP BY members.id, members.name
        ORDER BY members.name
        """
    )
    fun getSavingsSummaries(): List<MemberSavingsSummary>

    @Insert
    fun insertLoan(loan: Loan): Long

    @Update
    fun updateLoan(loan: Loan)

    @Query("SELECT COUNT(*) FROM loans WHERE memberId = :memberId AND status = 'ACTIVE'")
    fun activeLoanCount(memberId: Long): Int

    @Query("SELECT COUNT(*) FROM loans WHERE status = 'ACTIVE'")
    fun activeLoanCount(): Int

    @Query("SELECT COALESCE(SUM(amount + (amount * interestRate / 100.0)), 0) FROM loans WHERE status = 'ACTIVE'")
    fun totalActiveLoanDue(): Double

    @Query(
        """
        SELECT loans.id AS loanId,
               members.name AS memberName,
               loans.amount AS amount,
               loans.interestRate AS interestRate,
               loans.dueDate AS dueDate,
               loans.status AS status
        FROM loans
        INNER JOIN members ON members.id = loans.memberId
        ORDER BY loans.status, loans.dueDate
        """
    )
    fun getLoansWithMembers(): List<LoanWithMember>

    @Query("SELECT * FROM loans WHERE id = :loanId")
    fun getLoan(loanId: Long): Loan?
}
