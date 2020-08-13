package com.gnova.bakingapp_kotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.api.models.Recipe
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class RecipeAdapter(private val onClickListener: OnClickListener) : ListAdapter<Recipe, RecipeAdapter.RecipeHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        return RecipeHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipes = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(recipes)
        }
        holder.bind(recipes)
    }

    class RecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(recipe: Recipe) {

            itemView.recipe_name.text = recipe.name

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (recipe: Recipe) -> Unit) {
        fun onClick(recipe: Recipe) = clickListener(recipe)
    }

}