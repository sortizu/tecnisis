package com.example.tecnisis.config.retrofit
import com.example.tecnisis.ui.sign_up.data.SignUpService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL =
    ""

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TecnisisApi {
    val signUpService: SignUpService by lazy {
        retrofit.create(SignUpService::class.java)
    }
}