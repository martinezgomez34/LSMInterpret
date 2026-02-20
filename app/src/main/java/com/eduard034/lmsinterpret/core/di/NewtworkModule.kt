package com.eduard034.lmsinterpret.core.di

import com.eduard034.lmsinterpret.core.network.LsmApi // Asegúrate de tener este archivo
import com.eduard034.lmsinterpret.core.network.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @InterpretRetrofit
    fun provideInterpretRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideLsmApi(@InterpretRetrofit retrofit: Retrofit): LsmApi {
        return retrofit.create(LsmApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(@AuthRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

}