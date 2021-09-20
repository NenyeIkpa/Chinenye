package com.example.mypokemonapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.mypokemonapp.R
import com.squareup.picasso.Picasso

class PokemonAttribute : AppCompatActivity() {
    private lateinit var ivImage:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_attribute)
        ivImage = findViewById(R.id.ivImage)
        val num:Int = intent.getStringExtra("position")!!.toInt()
        Picasso
            .get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$num.png")
            .into(ivImage)
    }
}