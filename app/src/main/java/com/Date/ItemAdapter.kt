package com.Date

import android.service.autofill.Dataset
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment_freitag_aufagbe.databinding.ListItemBinding

class ItemAdapter (
    val dataset: Dataset: List<Notiz>
): RecyclerView.Adapter<ItemAdapter.NotizViewHolder>() {
    inner class NotizViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.NotizViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemAdapter.NotizViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}