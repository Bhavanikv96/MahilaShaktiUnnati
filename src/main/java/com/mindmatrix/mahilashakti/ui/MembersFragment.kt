package com.mindmatrix.mahilashakti.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindmatrix.mahilashakti.MahilaShaktiApp
import com.mindmatrix.mahilashakti.data.Member
import com.mindmatrix.mahilashakti.databinding.FragmentMembersBinding
import com.mindmatrix.mahilashakti.util.AppExecutors

class MembersFragment : Fragment() {
    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!
    private val adapter = SimpleTextAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.memberList.layoutManager = LinearLayoutManager(requireContext())
        binding.memberList.adapter = adapter
        binding.addMemberButton.setOnClickListener { addMember() }
        loadMembers()
    }

    private fun addMember() {
        val name = binding.nameInput.text?.toString()?.trim().orEmpty()
        val phone = binding.phoneInput.text?.toString()?.trim().orEmpty()
        val address = binding.addressInput.text?.toString()?.trim().orEmpty()
        if (name.isBlank() || phone.isBlank()) {
            Toast.makeText(requireContext(), "Name and phone are required", Toast.LENGTH_SHORT).show()
            return
        }

        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            dao.insertMember(Member(name = name, phone = phone, address = address))
            AppExecutors.main {
                if (_binding == null) return@main
                binding.nameInput.text?.clear()
                binding.phoneInput.text?.clear()
                binding.addressInput.text?.clear()
                Toast.makeText(requireContext(), "Member added", Toast.LENGTH_SHORT).show()
                loadMembers()
            }
        }
    }

    private fun loadMembers() {
        val dao = (requireActivity().application as MahilaShaktiApp).database.dao()
        AppExecutors.io {
            val rows = dao.getMembers().map {
                ListRow(it.name, "${it.phone}\n${it.address.ifBlank { "No address added" }}")
            }
            AppExecutors.main {
                if (_binding != null) adapter.submit(rows)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
