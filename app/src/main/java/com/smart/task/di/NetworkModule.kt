package com.smart.task.di

import com.smart.task.data.ApiClient
import com.smart.task.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkModule {

    private fun provideUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    private fun provideRetrofit():Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideUnsafeOkHttpClient())
            .build()
    }

    /**
     * Provides an instance of the `ApiClient` interface for making network API calls.
     *
     * This function creates a Retrofit service based on the `ApiClient` interface,
     * allowing the application to communicate with the backend APIs.
     * It relies on the `provideRetrofit()` method to supply a configured Retrofit instance.
     *
     * @return An implementation of the `ApiClient` interface.
     * @see ApiClient Defines the API endpoints and their respective HTTP methods.
     */
    fun provideApiClient(): ApiClient {
        return provideRetrofit().create(ApiClient::class.java)
    }

}