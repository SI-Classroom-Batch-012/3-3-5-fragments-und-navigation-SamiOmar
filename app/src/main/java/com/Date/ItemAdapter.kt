package com.Date

import android.service.autofill.Dataset
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment_freitag_aufagbe.NotizListeFragmentDirections
import com.example.fragment_freitag_aufagbe.databinding.ListItemBinding

 class ItemAdapter (
     private var dataset: List<Notiz>,
     private val onItemLongClick: (Notiz, Int) -> Unit // Hinzugefügt

 ): RecyclerView.Adapter<ItemAdapter.NotizViewHolder>() {
     inner class NotizViewHolder(val binding: ListItemBinding) :
         RecyclerView.ViewHolder(binding.root)

     fun newData(data: List<Notiz>) {
         dataset = data
         notifyDataSetChanged()
     }

     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): ItemAdapter.NotizViewHolder {
         val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
         return NotizViewHolder(binding)
     }

     override fun getItemCount(): Int {
         return dataset.size
     }

     override fun onBindViewHolder(holder: NotizViewHolder, position: Int) {
         val notiz = dataset[position]

         holder.binding.nameTV.text = notiz.titel
         holder.binding.titelsTV.text = notiz.detail

         // Klick-Listener für die Ansicht
         holder.binding.notizCV.setOnClickListener {
             Log.d("CardViewTest", "CardView clicked at position $position")
             val navcontroller = holder.binding.root.findNavController()
             navcontroller.navigate(
                 NotizListeFragmentDirections.actionNotizListeFragmentToNotizDetailFragment(position)
             )
         }


         // Lang-Klick-Listener für die gesamte Zeile
         holder.itemView.setOnLongClickListener {
             onItemLongClick(notiz, position)
             true // Gibt an, dass der Klick verarbeitet wurde
         }
     }
 }