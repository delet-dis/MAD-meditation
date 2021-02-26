package com.delet_dis.madmeditation.model

import com.google.gson.annotations.SerializedName

data class Quote(
  @SerializedName("id") val id: Int,
  @SerializedName("title") val title: String,
  @SerializedName("image") val image: String,
  @SerializedName("description") val description: String
)