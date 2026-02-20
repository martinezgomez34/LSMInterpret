package com.eduard034.lmsinterpret.feature.profile.data.repository

import com.eduard034.lmsinterpret.core.network.UserApi
import com.eduard034.lmsinterpret.feature.profile.data.remote.UpdatePasswordRequest
import com.eduard034.lmsinterpret.feature.profile.data.remote.UpdateProfileRequest
import com.eduard034.lmsinterpret.feature.profile.domain.ProfileRepository
import javax.inject.Inject // <-- 1. ¡NUEVO IMPORT AQUÍ!

class ProfileRepositoryImpl @Inject constructor(
    private val api: UserApi
) : ProfileRepository {

    override suspend fun getUserProfile(token: String, username: String) = runCatching {
        api.getUserProfile("Bearer $token", username)
    }

    override suspend fun updateUsername(token: String, userId: Int, newName: String) = runCatching {
        api.updateProfile("Bearer $token", userId, UpdateProfileRequest(newName))
        Unit
    }

    override suspend fun updatePassword(token: String, userId: Int, newPass: String) = runCatching {
        api.updatePassword("Bearer $token", userId, UpdatePasswordRequest(newPass))
        Unit
    }

    override suspend fun deleteAccount(token: String, userId: Int) = runCatching {
        api.deleteAccount("Bearer $token", userId)
        Unit
    }
}