package com.nextgen.jetheroes.data

import com.nextgen.jetheroes.model.Hero
import com.nextgen.jetheroes.model.HeroesData

class Repository {
    fun getHeroes(): List<Hero>{
        return HeroesData.heroes
    }

    fun searchHeroes(query: String): List<Hero>{
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}