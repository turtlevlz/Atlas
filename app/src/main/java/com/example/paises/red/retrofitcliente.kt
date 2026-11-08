package com.example.paises.red

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCliente {
    private const val URL_BASE = "https://countries.dev/"
    val api: PaisesApi = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PaisesApi::class.java)
}