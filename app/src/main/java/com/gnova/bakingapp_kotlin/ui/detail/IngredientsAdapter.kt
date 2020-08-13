package com.gnova.bakingapp_kotlin.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.api.models.Ingredients
import kotlinx.android.synthetic.main.ingredients_list_item.view.*

class IngredientsAdapter() : ListAdapter<Ingredients, IngredientsAdapter.IngredientsHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_list_item, parent, false)
        Log.d("TAG", "onCreateViewHolder Ingredients")
        return IngredientsHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        val ingredients = getItem(position)
        holder.bind(ingredients)
    }

    class IngredientsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(ingredients: Ingredients) {
            Log.d("TAG", "showTrailers Ingredients " + ingredients.ingredient)

            itemView.ingredient_quantity.text = ingredients.quantity.toString() + " " + ingredients.measure
            itemView.ingredient_name.text = ingredients.ingredient


        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ingredients>() {

        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem.ingredient == newItem.ingredient
        }

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem == newItem
        }
    }

}