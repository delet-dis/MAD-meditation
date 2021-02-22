package com.delet_dis.madmeditation.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
  @SerializedName("id") val id: String?,
  @SerializedName("email") val email: String?,
  @SerializedName("nickName") val nickName: String?,
  @SerializedName("avatar") val avatar: String?,
  @SerializedName("token") val token: String?,
  @SerializedName("error") val error: String?,
  @SerializedName("success") val success: Boolean?
)