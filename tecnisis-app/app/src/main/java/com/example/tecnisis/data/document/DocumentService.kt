package com.example.tecnisis.data.document

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DocumentService {
    @POST("documents/v1/api")
    suspend fun uploadDocument(@Header("Authorization") token: String, @Body documentRequest: DocumentRequest): Response<DocumentResponse>
    @GET("documents/v1/api/{id}")
    suspend fun getDocuments(@Header("Authorization") token: String, @Path("id") id: Long): Response<DocumentResponse>


}