package com.ivonicchio.catbreedsapp.ui.adapter

import com.ivonicchio.catbreedsapp.model.CatBreed

interface ListItemClickListener {
    fun onListItemClick(catBreed: CatBreed)
}