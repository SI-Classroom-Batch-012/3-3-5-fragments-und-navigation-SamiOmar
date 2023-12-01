package com.example.fragment_freitag_aufagbe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.Date.ItemAdapter
import com.Date.Notiz
import com.example.fragment_freitag_aufagbe.databinding.FragmentNotizListeBinding


class NotizListeFragment : Fragment() {
    private lateinit var binding: FragmentNotizListeBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var mainActivity: MainActivity



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotizListeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisieren Sie hier die Klassenvariable `mainActivity`
        mainActivity = activity as MainActivity

        val dataset = mainActivity.dataset
        adapter = ItemAdapter(dataset) // Achten Sie darauf, dass Sie hier `adapter` initialisieren, nicht eine neue lokale Variable erstellen
        binding.notizRV.adapter = adapter

        binding.addFAB.setOnClickListener {
            addTeamDialog()
        }
    }

        fun addTeamDialog() {

            val dialogBuilder = AlertDialog.Builder(requireContext())

            //Layout bestimmen
            dialogBuilder.setTitle("Notiz hinzufügen")
            val nameET = EditText(requireContext())
            dialogBuilder.setView(nameET)

            //Aktionen bestimmen
            dialogBuilder.setPositiveButton("Hinzufügen") { dialogInterface, number ->

                //Ein neues Team hinzufügen
                val name = nameET.text.toString()
                val myNotiz: Notiz = Notiz(name)

                //In den Daten aktualisiert
                mainActivity.addNotiz(myNotiz)

                //Daten neu laden und Adapter informieren
                adapter.newData(mainActivity.dataset)

                //Scroll an den Anfang
                binding.notizRV.scrollToPosition(0)
//        val lastPosition = adapter.itemCount - 1

                //adapter.sortTeams()
            }

            dialogBuilder.setNegativeButton("Abbrechen") { dialogInterface, _ ->
                dialogInterface.cancel()
            }

            dialogBuilder.show()

        }
    }

