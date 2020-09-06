package com.ivonicchio.catbreedsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivonicchio.catbreedsapp.R
import com.ivonicchio.catbreedsapp.model.CatBreed

class CatBreedsListAdapter(
    private val values: List<CatBreed>,
    private val itemClickListener: ListItemClickListener
) :
    RecyclerView.Adapter<CatBreedsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.breed_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.catBreedName.text = item.name

        holder.itemView.setOnClickListener {
            itemClickListener.onListItemClick(item)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val catBreedName: TextView = view.findViewById(R.id.tv_cat_breed_name)
    }
}