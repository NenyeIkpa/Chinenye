package com.example.mypokemonapp.ui

import android.content.Intent
import com.example.mypokemonapp.interfaces.ApiInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.MyPokemonAdapter
import com.example.mypokemonapp.MyPokemonData
import com.example.mypokemonapp.R
import com.example.mypokemonapp.interfaces.OnClickPokemon
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory



const val TAG = "mainActivity"
const val BASE_URL = "https://pokeapi.co/api/v2/"
class MainActivity : AppCompatActivity(), OnClickPokemon{

    lateinit var errorMsg: TextView
    private lateinit var rvView: RecyclerView
    lateinit var myAdapter: MyPokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        errorMsg = findViewById(R.id.textView_errorMsg)
        rvView = findViewById(R.id.rv_pokemon)

        myAdapter = MyPokemonAdapter(this)
        rvView.adapter = myAdapter
        rvView.setHasFixedSize(true)
        rvView.layoutManager = GridLayoutManager(this, 2)




        val itemDecoration1 = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        val itemDecoration2 = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rvView.addItemDecoration(itemDecoration1)
        rvView.addItemDecoration(itemDecoration2)

        getMyPokemonData()
    }

    private fun getMyPokemonData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getDataFromApi()
        retrofitData.enqueue(object :Callback<MyPokemonData>{
            override fun onResponse(call: Call<MyPokemonData>, response: Response<MyPokemonData>) {
                val responseBody = response.body()
                Log.d(TAG,"response: $responseBody")
                if (responseBody != null){
                    val arr = responseBody.results
                    myAdapter.setListOfData(arr)
                }
            }

            override fun onFailure(call: Call<MyPokemonData>, t: Throwable) {
                Log.d(TAG, "on Failure" + t.message)
              errorMsg.visibility = View.VISIBLE
            }

        })


    }

    override fun onClick(position: Int) {
        val pos = position + 501
        val intent = Intent(this, PokemonAttribute::class.java)
        intent.putExtra("position",pos.toString())
        startActivity(intent)
    }
}
//retrofitData.enqueue(object : Callback<ArrayList<Result>?> {
//    override fun onResponse(
//        call: Call<ArrayList<Result>?>,
//        response: Response<ArrayList<Result>?>
//    ) {
//
//    }
//
//    override fun onFailure(call: Call<ArrayList<Result>?>, t: Throwable) {
////
//
//    }
//})