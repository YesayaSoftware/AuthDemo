package tz.co.yesayasoftware.authdemo.data.repository

import tz.co.yesayasoftware.authdemo.data.utils.Result

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ) : Result<out Any>

    suspend fun register(
        name: String,
        email: String,
        password: String,
        password_confirmation: String
    ): Result<out Any>

    suspend fun getCurrentAuthUser(): Result<out Any>

    suspend fun logout(): Result<out Any>
}