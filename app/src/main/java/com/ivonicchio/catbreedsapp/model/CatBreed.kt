package com.ivonicchio.catbreedsapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatBreed(

    @field:Json(name = "id") val id: String,

    @field:Json(name = "name") val name: String,

    @field:Json(name = "origin") val origin: String,

    @field:Json(name = "temperament") val temperament: String,

    @field:Json(name = "description") val description: String



): Parcelable