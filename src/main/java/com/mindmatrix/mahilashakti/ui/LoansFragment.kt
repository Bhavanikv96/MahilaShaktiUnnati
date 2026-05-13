package com.mindmatrix.mahilashakti.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindmatrix.mahilashakti.MahilaShaktiApp
import com.mindmatrix.mahilashakti.data.Loan
import com.mindmatrix.mahilashakti.data.Member
import com.mindmatrix.mahilashakti.data.RepaymentStatus
import com.mindmatrix.mahilashakti.databinding.FragmentLoansBinding
import com.mindmatrix.mahilashakti.util.AppExecutors
import com.mindmatrix.mahilashakti.util.Formatters

class LoansFragment : Fragment() {
    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!
    private val listAdapter = SimpleTextAdapter()
    private var members: List<Member> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loanList.layoutManager = LinearLayoutManager(requireContext())
        binding.loanList.adapter = listAdapter
        binding.dueDateInput.setText(Formatters.today())
        binding.addLoanButton.setOnClickListener { addLoan() }
        load()
    }

    private fun addLoan() {
        val member = members.getOrNull(binding.memberSpinner.selectedItemPosition)
        val amount = binding.loanAmountInput.text?.toString()?.toDoubleOrNull()
        val interest = binding.interestInput.text?.toString()?.toDoubleOrNull()
        val dueDate = binding.dueDateInput.text?.toString()?.trim().orEmpty()
        if (member == null || amount == null || interest == null || amount <= 0.0 || interest < 0.0 || dueDate.isBlank()) {
            Toast.makeText(requireContext(), "Complete loan details", Toast.LENGTH_SHORT).show()
            return
        }

        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            if (dao.activeLoanCount(member.id) > 0) {
                AppExecutors.main {
                    if (_binding != null) {
                        Toast.makeText(requireContext(), "This member already has an active loan", Toast.LENGTH_LONG).show()
                    }
                }
                return@io
            }

            dao.insertLoan(
                Loan(
                    memberId = member.id,
                    amount = amount,
                    interestRate = interest,
                    dueDate = dueDate,
                    status = RepaymentStatus.ACTIVE
                )
            )
            AppExecutors.main {
                if (_binding == null) return@main
                binding.loanAmountInput.text?.clear()
                binding.interestInput.text?.clear()
                Toast.makeText(requireContext(), "Loan issued", Toast.LENGTH_SHORT).show()
                load()
            }
        }
    }

    private fun markRepaid(loanId: Long) {
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            val loan = dao.getLoan(loanId) ?: return@io
            dao.updateLoan(loan.copy(status = RepaymentStatus.REPAID))
            AppExecutors.main {
                if (_binding == null) return@main
                Toast.makeText(requireContext(), "Loan marked repaid", Toast.LENGTH_SHORT).show()
                load()
            }
        }
    }

    private fun load() {
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            val loadedMembers = dao.getMembers()
            val rows = dao.getLoansWithMembers().map { loan ->
                val active = loan.status == RepaymentStatus.ACTIVE
                ListRow(
                    loan.memberName,
                    "Amount: ${Formatters.money(loan.amount)}\nInterest: ${loan.interestRate}% | Total due: ${Formatters.money(loan.totalDue)}\nDue: ${loan.dueDate} | ${loan.status.name}",
                    if (active) "Mark Repaid" else null,
                    if (active) ({ markRepaid(loan.loanId) }) else null
                )
            }
            AppExecutors.main {
                if (_binding == null) return@main
                members = loadedMembers
                binding.memberSpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    loadedMembers.map { it.name }
                )
                listAdapter.submit(rows)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
