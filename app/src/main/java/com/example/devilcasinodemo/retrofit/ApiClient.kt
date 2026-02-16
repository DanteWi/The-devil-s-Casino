package com.example.devilcasinodemo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.devilcasinodemo.BuildConfig

object ApiClient {
    //creat prod and distibucion
    //control de validacio de certificados
    //foto perfile
    //private const val BASE_URL = "https://unsurmised-kimber-unswervingly.ngrok-free.dev"    //prod
    public const val BASE_URL = BuildConfig.BASE_URL  // para emulador ,a cambiar a cuando envias a produc

    var api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}

