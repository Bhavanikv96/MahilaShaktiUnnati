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
import com.mindmatrix.mahilashakti.data.Member
import com.mindmatrix.mahilashakti.data.PaymentStatus
import com.mindmatrix.mahilashakti.data.SavingsEntry
import com.mindmatrix.mahilashakti.databinding.FragmentSavingsBinding
import com.mindmatrix.mahilashakti.util.AppExecutors
import com.mindmatrix.mahilashakti.util.Formatters

class SavingsFragment : Fragment() {
    private var _binding: FragmentSavingsBinding? = null
    private val binding get() = _binding!!
    private val listAdapter = SimpleTextAdapter()
    private var members: List<Member> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.savingsList.layoutManager = LinearLayoutManager(requireContext())
        binding.savingsList.adapter = listAdapter
        binding.saveSavingsButton.setOnClickListener { saveSavings() }
        load()
    }

    private fun saveSavings() {
        val member = members.getOrNull(binding.memberSpinner.selectedItemPosition)
        val amount = binding.amountInput.text?.toString()?.toDoubleOrNull()
        if (member == null || amount == null || amount <= 0.0) {
            Toast.makeText(requireContext(), "Select member and enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val status = if (binding.paidRadio.isChecked) PaymentStatus.PAID else PaymentStatus.PENDING
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            dao.insertSavings(SavingsEntry(memberId = member.id, amount = amount, status = status, date = Formatters.today()))
            AppExecutors.main {
                if (_binding == null) return@main
                binding.amountInput.text?.clear()
                Toast.makeText(requireContext(), "Savings recorded", Toast.LENGTH_SHORT).show()
                load()
            }
        }
    }

    private fun load() {
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            val loadedMembers = dao.getMembers()
            val summaries = dao.getSavingsSummaries().map {
                ListRow(
                    it.memberName,
                    "Paid savings: ${Formatters.money(it.totalPaid)}\nPending entries: ${it.pendingCount}"
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
                listAdapter.submit(summaries)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
