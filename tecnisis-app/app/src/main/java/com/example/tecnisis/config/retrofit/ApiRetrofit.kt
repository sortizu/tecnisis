package com.example.tecnisis.config.retrofit

import com.example.tecnisis.ui.list_artist_requests.data.ListUserRequestsService
import com.example.tecnisis.ui.login.data.LoginService
import com.example.tecnisis.ui.sign_up.data.SignUpService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://192.168.18.85:8080/api/auth/"

// Configura el interceptor para los logs de Retrofit
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

// Configura el cliente HTTP con el interceptor
private val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using Gson converter for JSON
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create()) // Cambiado a GsonConverterFactory
    .client(client) // Añade el cliente con el interceptor
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TecnisisApi {
    val signUpService: SignUpService by lazy {
        retrofit.create(SignUpService::class.java)
    }
    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }
    val listUserRequestsService: ListUserRequestsService by lazy {
        retrofit.create(ListUserRequestsService::class.java)
    }
}
