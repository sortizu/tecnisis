package com.example.tecnisis.ui.login.data

import com.example.tecnisis.data.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface LoginService {
    @POST("api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
