package com.Date

class Datensource {

    fun loadNotiz() : MutableList<Notiz> {

        val result = mutableListOf<Notiz>()
        val newNotiz = Notiz("","")
        result.add(newNotiz)
        return result
    }
}