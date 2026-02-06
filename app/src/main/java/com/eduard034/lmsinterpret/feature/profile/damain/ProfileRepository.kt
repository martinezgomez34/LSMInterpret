package com.eduard034.lmsinterpret.feature.profile.domain

import com.eduard034.lmsinterpret.feature.profile.data.remote.UserProfileDto

interface ProfileRepository {
    suspend fun getUserProfile(token: String, username: String): Result<UserProfileDto>
    suspend fun updateUsername(token: String, userId: Int, newName: String): Result<Unit>
    suspend fun updatePassword(token: String, userId: Int, newPass: String): Result<Unit>
    suspend fun deleteAccount(token: String, userId: Int): Result<Unit>
}