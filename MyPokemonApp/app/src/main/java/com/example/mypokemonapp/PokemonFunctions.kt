package com.example.mypokemonapp

object PokemonFunctions {
    fun getImageId(url:String):Int{
        val splitUrl = url.split("/")
        return splitUrl[splitUrl.lastIndex-1].toInt()
    }
}