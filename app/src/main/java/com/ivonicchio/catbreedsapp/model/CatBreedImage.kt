package com.ivonicchio.catbreedsapp.model

import com.squareup.moshi.Json

data class CatBreedImage(
    @field:Json(name = "url") val url: String
)