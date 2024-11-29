package com.example.tecnisis.data.user

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface UserService {
    @GET("users/v1/api/rol-by-id")
    suspend fun getUserRoleById(@Query("id") id: Long): Response<UserRoleResponse>
}