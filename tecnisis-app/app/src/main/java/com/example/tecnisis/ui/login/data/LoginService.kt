package com.example.tecnisis.ui.login.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface LoginService {
    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
