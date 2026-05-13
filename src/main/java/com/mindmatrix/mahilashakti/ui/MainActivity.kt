package com.mindmatrix.mahilashakti.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mindmatrix.mahilashakti.R
import com.mindmatrix.mahilashakti.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> show("Dashboard", DashboardFragment())
                R.id.nav_members -> show("Members", MembersFragment())
                R.id.nav_savings -> show("Savings", SavingsFragment())
                R.id.nav_loans -> show("Loans", LoansFragment())
                R.id.nav_reports -> show("Reports", ReportsFragment())
            }
            true
        }

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_dashboard
        }
    }

    private fun show(title: String, fragment: Fragment) {
        binding.toolbarTitle.text = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
