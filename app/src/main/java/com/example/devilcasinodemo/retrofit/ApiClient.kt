package com.example.devilcasinodemo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    //creat prod and distibucion
    //control de validacio de certificados
    //foto perfile
    private const val BASE_URL = "https://unsurmised-kimber-unswervingly.ngrok-free.dev/"    //prod
    //private const val BASE_URL = "http://10.0.2.2:8080/"  // para emulador ,a cambiar a cuando envias a produc

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}

