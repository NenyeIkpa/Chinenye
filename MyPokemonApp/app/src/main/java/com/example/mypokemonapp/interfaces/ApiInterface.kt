package com.example.mypokemonapp.interfaces

import com.example.mypokemonapp.MyPokemonData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("pokemon?limit=100&offset=500")
    fun getDataFromApi() : Call<MyPokemonData>
}