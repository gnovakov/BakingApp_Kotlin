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
import com.gnova.bakingapp_kotlin.api.models.Steps
import kotlinx.android.synthetic.main.ingredients_list_item.view.*
import kotlinx.android.synthetic.main.steps_list_item.view.*

class StepsAdapter() : ListAdapter<Steps, StepsAdapter.StepsHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.steps_list_item, parent, false)
        Log.d("TAG", "onCreateViewHolder Steps")
        return StepsHolder(view)
    }

    override fun onBindViewHolder(holder: StepsHolder, position: Int) {
        val steps = getItem(position)
        holder.bind(steps)
    }

    class StepsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(steps: Steps) {
            Log.d("TAG", "showTrailers Ingredients " + steps.id)

            itemView.steps_name.text = steps.shortDescription

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Steps>() {

        override fun areItemsTheSame(oldItem: Steps, newItem: Steps): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Steps, newItem: Steps): Boolean {
            return oldItem == newItem
        }
    }

}