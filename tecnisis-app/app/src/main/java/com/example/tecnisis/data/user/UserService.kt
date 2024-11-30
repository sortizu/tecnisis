package com.example.tecnisis.data.user

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface UserService {
    @GET("users/v1/api/rol-by-id/{id}")
    suspend fun getUserRoleById(@Path("id") id: Long): Response<UserRoleResponse>
}