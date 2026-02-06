package com.eduard034.lmsinterpret.core.di

import android.content.Context
import com.eduard034.lmsinterpret.core.data.local.UserSession
import com.eduard034.lmsinterpret.core.network.LsmApi
import com.eduard034.lmsinterpret.core.network.UserApi
import com.eduard034.lmsinterpret.feature.interpret.data.repositories.InterpretRepositoryImpl
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository
import com.eduard034.lmsinterpret.feature.login.data.repositories.UserRepositoryImpl
import com.eduard034.lmsinterpret.feature.login.domain.repositories.UserRepository
import com.eduard034.lmsinterpret.feature.profile.data.repository.ProfileRepositoryImpl
import com.eduard034.lmsinterpret.feature.profile.domain.ProfileRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val retrofitInterpret: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitAuth: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val lsmApi: LsmApi by lazy {
        retrofitInterpret.create(LsmApi::class.java)
    }

    val userApi: UserApi by lazy {
        retrofitAuth.create(UserApi::class.java)
    }

    val interpretRepository: InterpretRepository by lazy {
        InterpretRepositoryImpl(lsmApi)
    }

    val authRepository: UserRepository by lazy {
        UserRepositoryImpl(userApi)
    }

    val userSession: UserSession by lazy {
        UserSession(context)
    }

    val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(userApi)
    }
}