package com.eduard034.lmsinterpret.core.network

import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.LoginRequestDto
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.LoginResponseDto
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.RegisterRequestDto
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.RegisterResponseDto
import com.eduard034.lmsinterpret.feature.profile.data.remote.UpdatePasswordRequest
import com.eduard034.lmsinterpret.feature.profile.data.remote.UpdateProfileRequest
import com.eduard034.lmsinterpret.feature.profile.data.remote.UserProfileDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): RegisterResponseDto

    @GET("auth/user/{username}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): UserProfileDto

    @PUT("auth/update-profile/{user_id}")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Path("user_id") userId: Int,
        @Body request: UpdateProfileRequest
    ): Any

    @PUT("auth/update-password/{user_id}")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Path("user_id") userId: Int,
        @Body request: UpdatePasswordRequest
    ): Any

    @DELETE("auth/user/{user_id}")
    suspend fun deleteAccount(
        @Header("Authorization") token: String,
        @Path("user_id") userId: Int
    ): Any
}