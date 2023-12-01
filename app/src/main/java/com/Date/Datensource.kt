package com.Date

class Datensource {

    fun loadNotiz() : MutableList<Notiz> {

        val result = mutableListOf<Notiz>()

        for (i in 1..10){
            val newTeam = Notiz("Team $i", 0)
            result.add(newTeam)
        }

        return result

    }

}