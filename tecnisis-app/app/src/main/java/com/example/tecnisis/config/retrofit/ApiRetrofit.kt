package com.example.tecnisis.config.retrofit

import com.example.tecnisis.data.artwork.ArtworkService
import com.example.tecnisis.data.evaluations.EvaluationService
import com.example.tecnisis.data.person.PersonService
import com.example.tecnisis.data.request.RequestService
import com.example.tecnisis.data.specialist.SpecialistService
import com.example.tecnisis.data.technique.TechniquesService
import com.example.tecnisis.ui.login.data.LoginService
import com.example.tecnisis.ui.sign_up.data.SignUpService
import com.example.tecnisis.ui.start_request.data.StartRequestService
import com.example.tecnisis.ui.view_request.data.ViewRequestService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://192.168.18.13:8080/"

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
    val artworkService: ArtworkService by lazy {
        retrofit.create(ArtworkService::class.java)
    }
    val techniquesService: TechniquesService by lazy {
        retrofit.create(TechniquesService::class.java)
    }
    val evaluationService: EvaluationService by lazy {
        retrofit.create(EvaluationService::class.java)
    }
    val specialistService: SpecialistService by lazy {
        retrofit.create(SpecialistService::class.java)
    }
    val requestService: RequestService by lazy {
        retrofit.create(RequestService::class.java)
    }
    val personService: PersonService by lazy {
        retrofit.create(PersonService::class.java)
    }
}
