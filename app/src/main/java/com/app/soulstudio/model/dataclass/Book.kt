package com.app.soulstudio.model.dataclass

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("kind") val kind : String,
    @SerializedName("totalItems") val totalItems : Int,
    @SerializedName("items") val items : List<Items>?
)
