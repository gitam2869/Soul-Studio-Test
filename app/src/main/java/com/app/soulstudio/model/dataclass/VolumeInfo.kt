package com.app.soulstudio.model.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VolumeInfo(
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String?,
    @SerializedName("imageLinks") val imageLinks : ImageLinks?
) : Parcelable
