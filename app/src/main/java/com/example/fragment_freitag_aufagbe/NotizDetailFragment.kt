package com.example.fragment_freitag_aufagbe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.Date.ItemAdapter
import com.Date.Notiz
import com.example.fragment_freitag_aufagbe.databinding.FragmentNotizDetailBinding

class NotizDetailFragment : Fragment() {
    private lateinit var binding: FragmentNotizDetailBinding
    private val args: NotizDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotizDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Daten laden
        val position = args.notizArgument
        val mainActivity = activity as MainActivity
        val notiz = mainActivity.dataset[position]

        // Details in Views setzen
        binding.notizEV.text = Editable.Factory.getInstance().newEditable(notiz.titel)

        // Initialer Zustand des Buttons
        updateButtonState()

        // TextWatcher
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }
        }

        // TextWatcher zu EditText hinzufügen
        binding.notizEV.addTextChangedListener(textWatcher)
        binding.titleEV.addTextChangedListener(textWatcher)

        // Button-Klick-Listener
        binding.button.setOnClickListener {
            // Tastatur verbergen
            hideKeyboard()

            // Speicherlogik
            saveNotizChanges(position, notiz)

            // Zurück zum NotizListeFragment navigieren
            findNavController().navigateUp()
        }
    }

    private fun updateButtonState() {
        if (binding.notizEV.text.isNotEmpty() || binding.titleEV.text.isNotEmpty()) {
            binding.button.isEnabled = true
            binding.button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_activ))
            binding.button.text = "Speichern" // Text ändern
        } else {
            binding.button.isEnabled = false
            binding.button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_inactiv))
            binding.button.text = "Abbrechen" // Text ändern
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun saveNotizChanges(position: Int, notiz: Notiz) {
        notiz.titel = binding.notizEV.text.toString()
        notiz.titel =  binding.titleEV.text.toString()

        // Aktualisieren Sie das Element an der entsprechenden Position
        val mainActivity = activity as MainActivity
        mainActivity.dataset[position] = notiz

        // Optional: Falls die Änderungen auch persistent gespeichert werden sollen,
        // können Sie eine entsprechende Methode in MainActivity aufrufen.
        // Beispiel:
        // mainActivity.saveNotiz(notiz)
    }
}
