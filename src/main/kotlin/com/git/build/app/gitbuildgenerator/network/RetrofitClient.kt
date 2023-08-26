package com.git.build.app.gitbuildgenerator.network

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://git.mpokket.org/api/v4/projects/18/"

    private val client = OkHttpClient.Builder().build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

inline fun <reified T> Call<T>.enqueue(
    crossinline onResponse: (response: T) -> Unit,
    crossinline onFailure: (t: Throwable) -> Unit
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                onResponse(response.body()!!)
            } else {
                onFailure(Throwable("Response not successful"))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }
    })
}