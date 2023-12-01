package com.example.fragment_freitag_aufagbe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.Date.Datensource
import com.Date.Notiz
import com.example.fragment_freitag_aufagbe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var dataset: MutableList<Notiz> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataset = Datensource().loadNotiz().toMutableList()
    }

    fun addNotiz(newNotiz: Notiz) {
        dataset.add(0, newNotiz) // Fügt newNotiz an der Position 0 hinzu
    }
}
