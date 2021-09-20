package com.example.mypokemonapp

data class MyPokemonData(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)