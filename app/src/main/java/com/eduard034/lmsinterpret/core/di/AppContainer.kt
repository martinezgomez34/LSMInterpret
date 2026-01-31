package com.eduard034.lmsinterpret.core.di

import android.content.Context
import com.eduard034.lmsinterpret.core.network.LsmApi
import com.eduard034.lmsinterpret.feature.interpret.data.repositories.InterpretRepositoryImpl
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    // Apunta a tu servidor Node.js (10.0.2.2 es localhost desde el emulador)
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val lsmApi: LsmApi by lazy {
        retrofit.create(LsmApi::class.java)
    }

    val interpretRepository: InterpretRepository by lazy {
        InterpretRepositoryImpl(lsmApi)
    }
}