package com.example.mypokemonapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.interfaces.OnClickPokemon
import com.squareup.picasso.Picasso

class MyPokemonAdapter(var pokemonClick:OnClickPokemon) : RecyclerView.Adapter<MyPokemonAdapter.MyPokemonViewHolder>() {
    private var element: ArrayList<Result> = ArrayList()

    inner class MyPokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var image: ImageView = itemView.findViewById(R.id.imageView)
        var name: TextView = itemView.findViewById(R.id.textView_name)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                pokemonClick.onClick(position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPokemonViewHolder {
        val inflated = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return MyPokemonViewHolder(inflated)
    }

    override fun onBindViewHolder(holder: MyPokemonViewHolder, position: Int) {
       val currentPos = element[position]
//        Picasso.get().load(currentPos?.url).into(holder.image)
        holder.name.text = currentPos.name
        val urlId = PokemonFunctions.getImageId(currentPos.url)
        Picasso
            .get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$urlId.png")
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return element.size
    }

    fun setListOfData(arr: List<Result>) {
        element.addAll(arr)
        notifyDataSetChanged()

    }
}