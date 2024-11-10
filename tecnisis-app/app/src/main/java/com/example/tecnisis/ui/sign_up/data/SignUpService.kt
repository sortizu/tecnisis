package com.example.tecnisis.ui.sign_up.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface SignUpService {
    @POST("signup")
    suspend fun registerPerson(@Body personRequest: PersonRequest): Response<SignUpResponse>
}