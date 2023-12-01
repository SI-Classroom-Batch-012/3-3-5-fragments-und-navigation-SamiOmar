package com.Date

class Datensource {

    fun loadNotiz() : MutableList<Notiz> {

        val result = mutableListOf<Notiz>()

        for (i in 0..1){
            val newNotiz = Notiz("")
            result.add(newNotiz)
        }

        return result

    }

}