package com.delet_dis.madmeditation.http.retrofit

import com.delet_dis.madmeditation.model.LoginRequest
import com.delet_dis.madmeditation.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServices {

  @POST("user/login")
  fun postLoginData(
    @Body loginRequest: LoginRequest
  ): Call<LoginResponse>
}