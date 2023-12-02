package com.example.fragment_freitag_aufagbe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.Date.ItemAdapter
import com.Date.Notiz
import com.example.fragment_freitag_aufagbe.databinding.FragmentNotizListeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotizListeFragment : Fragment() {
    private lateinit var binding: FragmentNotizListeBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var mainActivity: MainActivity
    private var filteredDataset: MutableList<Notiz> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotizListeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity
        filteredDataset.addAll(mainActivity.dataset)
        adapter = ItemAdapter(filteredDataset) { notiz, position ->
            showOptionsDialog(notiz, position)
        }
        binding.notizRV.adapter = adapter

        binding.addFAB.setOnClickListener {
            addNotizDialog()
        }

        setupSearchAndFilter()
    }

    private fun setupSearchAndFilter() {
        binding.searchEditText.addTextChangedListener { text ->
            filterData(text.toString())
        }

        binding.btnNewest.setOnClickListener {
            sortDataNewestFirst()
        }

        binding.btnOldest.setOnClickListener {
            sortDataOldestFirst()
        }
    }

    private fun filterData(searchQuery: String) {
        filteredDataset = if (searchQuery.isEmpty()) {
            mainActivity.dataset
        } else {
            mainActivity.dataset.filter {
                it.titel.contains(searchQuery, ignoreCase = true) ||
                        it.detail.contains(searchQuery, ignoreCase = true)
            }.toMutableList()
        }
        sortData()
    }

    private fun sortDataNewestFirst() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        filteredDataset.sortByDescending {
            dateFormat.parse(it.erstelltAm) ?: Date()
        }
        adapter.newData(filteredDataset)
    }

    private fun sortDataOldestFirst() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        filteredDataset.sortBy {
            dateFormat.parse(it.erstelltAm) ?: Date()
        }
        adapter.newData(filteredDataset)
    }

    private fun sortData() {
        adapter.newData(filteredDataset)
    }

    private fun showOptionsDialog(notiz: Notiz, position: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Optionen")
            setItems(arrayOf("Bearbeiten", "Löschen")) { _, which ->
                when (which) {
                    0 -> editNotiz(position)
                    1 -> deleteNotiz(notiz, position)
                }
            }
            show()
        }
    }

    private fun editNotiz(position: Int) {
        val navController = findNavController()
        navController.navigate(
            NotizListeFragmentDirections.actionNotizListeFragmentToNotizDetailFragment(position)
        )
    }

    private fun deleteNotiz(notiz: Notiz, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Notiz löschen")
            .setMessage("Möchten Sie diese Notiz wirklich löschen?")
            .setPositiveButton("Löschen") { _, _ ->
                mainActivity.dataset.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Abbrechen", null)
            .show()
    }

    fun addNotizDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rounded, null)
        val notizET = dialogView.findViewById<EditText>(R.id.notizEditText)
        val notizDetails = dialogView.findViewById<EditText>(R.id.titelsEditText)

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Notiz hinzufügen")
        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("Hinzufügen") { _, _ ->
            val notiz = notizET.text.toString()
            val details = notizDetails.text.toString()
            val aktuellesDatum = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
            val myNotiz = Notiz(notiz, details, aktuellesDatum)
            mainActivity.addNotiz(myNotiz)
            filterData(binding.searchEditText.text.toString())
        }

        dialogBuilder.setNegativeButton("Abbrechen") { dialog, _ ->
            dialog.cancel()
        }

        dialogBuilder.show()
    }
}
