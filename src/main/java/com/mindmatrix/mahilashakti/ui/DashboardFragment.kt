package com.mindmatrix.mahilashakti.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindmatrix.mahilashakti.MahilaShaktiApp
import com.mindmatrix.mahilashakti.databinding.FragmentDashboardBinding
import com.mindmatrix.mahilashakti.util.AppExecutors
import com.mindmatrix.mahilashakti.util.Formatters

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    private fun load() {
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            val members = dao.countMembers()
            val savings = dao.totalPaidSavings()
            val activeLoans = dao.activeLoanCount()
            val loanDue = dao.totalActiveLoanDue()
            AppExecutors.main {
                if (_binding == null) return@main
                binding.memberCount.text = "Members\n$members registered"
                binding.savingsTotal.text = "Total paid savings\n${Formatters.money(savings)}"
                binding.activeLoans.text = "Active loans\n$activeLoans running"
                binding.loanDue.text = "Outstanding loan amount\n${Formatters.money(loanDue)}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
