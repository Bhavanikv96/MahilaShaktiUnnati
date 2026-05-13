package com.mindmatrix.mahilashakti.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindmatrix.mahilashakti.MahilaShaktiApp
import com.mindmatrix.mahilashakti.databinding.FragmentReportsBinding
import com.mindmatrix.mahilashakti.util.AppExecutors
import com.mindmatrix.mahilashakti.util.Formatters

class ReportsFragment : Fragment() {
    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private var report: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.shareReportButton.setOnClickListener { shareReport() }
        loadReport()
    }

    private fun loadReport() {
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            val members = dao.countMembers()
            val savings = dao.totalPaidSavings()
            val loanDue = dao.totalActiveLoanDue()
            val savingsRows = dao.getSavingsSummaries()
            val loanRows = dao.getLoansWithMembers()
            val builder = StringBuilder()
                .appendLine("Mahila-Shakti Unnati Report")
                .appendLine("Date: ${Formatters.today()}")
                .appendLine()
                .appendLine("Members: $members")
                .appendLine("Total Paid Savings: ${Formatters.money(savings)}")
                .appendLine("Outstanding Loan Due: ${Formatters.money(loanDue)}")
                .appendLine()
                .appendLine("Savings Summary")
            savingsRows.forEach {
                builder.appendLine("- ${it.memberName}: ${Formatters.money(it.totalPaid)} paid, ${it.pendingCount} pending")
            }
            builder.appendLine().appendLine("Loan Summary")
            loanRows.forEach {
                builder.appendLine("- ${it.memberName}: ${Formatters.money(it.totalDue)} due, ${it.status.name}, due date ${it.dueDate}")
            }

            AppExecutors.main {
                if (_binding == null) return@main
                report = builder.toString()
                binding.reportText.text = report.ifBlank { "No records yet." }
            }
        }
    }

    private fun shareReport() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, report)
        }
        startActivity(Intent.createChooser(intent, "Share report"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
