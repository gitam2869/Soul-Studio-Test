package com.app.soulstudio.model.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageLinks(
    @SerializedName("smallThumbnail") val smallThumbnail: String,
    @SerializedName("thumbnail") val thumbnail: String
) : Parcelable
