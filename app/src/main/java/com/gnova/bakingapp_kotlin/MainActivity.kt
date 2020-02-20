package com.gnova.bakingapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gnova.bakingapp_kotlin.api.JsonRecipesApi
import com.gnova.bakingapp_kotlin.api.ServiceBuilder
import com.gnova.bakingapp_kotlin.models.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var recipes: List<Recipe>? = null

    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadJSON()
    }


    fun loadJSON() {

        val jsonRecipesApi = ServiceBuilder.buildService(JsonRecipesApi::class.java)

        val call = jsonRecipesApi.getRecipes()

        call.enqueue(object: Callback<List<Recipe>> {

            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {

                    recipes = response.body()

                    Log.d(TAG, "RECIPES " + recipes)

                    //Uncomment when Recycler view is created
                    //recipesRecyclerView.setAdapter(RecipesAdapter(his@MainActivity, recipes, this@MainActivity))

                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {

            }
        })
    }

}
