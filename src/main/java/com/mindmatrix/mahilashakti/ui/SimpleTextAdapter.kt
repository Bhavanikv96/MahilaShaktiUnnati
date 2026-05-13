package com.mindmatrix.mahilashakti.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindmatrix.mahilashakti.databinding.ListItemTwoLineBinding

data class ListRow(
    val title: String,
    val subtitle: String,
    val actionText: String? = null,
    val onAction: (() -> Unit)? = null
)

class SimpleTextAdapter : RecyclerView.Adapter<SimpleTextAdapter.RowViewHolder>() {
    private val rows = mutableListOf<ListRow>()

    fun submit(items: List<ListRow>) {
        rows.clear()
        rows.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val binding = ListItemTwoLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        holder.bind(rows[position])
    }

    override fun getItemCount(): Int = rows.size

    class RowViewHolder(private val binding: ListItemTwoLineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(row: ListRow) {
            binding.titleText.text = row.title
            binding.subtitleText.text = row.subtitle
            if (row.actionText == null || row.onAction == null) {
                binding.actionButton.visibility = View.GONE
                binding.actionButton.setOnClickListener(null)
            } else {
                binding.actionButton.visibility = View.VISIBLE
                binding.actionButton.text = row.actionText
                binding.actionButton.setOnClickListener { row.onAction.invoke() }
            }
        }
    }
}
