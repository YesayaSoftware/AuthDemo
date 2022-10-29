package tz.co.yesayasoftware.authdemo.data.repository

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import tz.co.yesayasoftware.authdemo.data.model.ErrorResponse
import tz.co.yesayasoftware.authdemo.data.model.LoginRequest
import tz.co.yesayasoftware.authdemo.data.model.RegisterRequest
import tz.co.yesayasoftware.authdemo.data.network.ApiErrorException
import tz.co.yesayasoftware.authdemo.data.network.AuthApiService
import tz.co.yesayasoftware.authdemo.data.utils.Result
import java.io.IOException

class AuthRepositoryImpl(
    private val retrofit: Retrofit,
    private val service: AuthApiService,
    private val prefs: SharedPreferences
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<out Any> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.login(
                    request = LoginRequest(
                        email,
                        password
                    )
                )

                if (response.isSuccessful) {
                    prefs.edit()
                        .putString("token_type", response.body()?.tokenType)
                        .putString("access_token", response.body()?.accessToken)
                        .putString("expires_in", response.body()?.expiresIn)
                        .putString("refresh_token", response.body()?.refreshToken)
                        .apply()

                    Result.Authorized(
                        data = getCurrentAuthUser().data
                    )
                } else {
                    val errors = convertErrorBody(
                        response.errorBody()
                    )

                    Result.ApiError(
                        data = errors
                    )
                }
            } catch (e: HttpException) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.Unauthorized()
            } catch (e: IOException) {
                Result.ApiError(
                    data = ErrorResponse(
                        message = "Please connect to the internet"
                    )
                )
            } catch (e: Exception) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.Unauthorized()
            }
        }
    }

    private fun convertErrorBody(
        errorBody: ResponseBody?
    ): ErrorResponse? {
        val converter: Converter<ResponseBody, ErrorResponse> =
            retrofit.responseBodyConverter(ErrorResponse::class.java, arrayOfNulls<Annotation>(0))

        var apiError: ErrorResponse? = null

        try {
            apiError = converter.convert(errorBody)
        } catch (e: ApiErrorException) {
            e.printStackTrace()
        }

        return apiError
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        password_confirmation: String
    ): Result<out Any> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.register(
                    request = RegisterRequest(
                        name,
                        email,
                        password,
                        password_confirmation
                    )
                )

                if (response.isSuccessful) {
                    prefs.edit()
                        .putString("token_type", response.body()?.tokenType)
                        .putString("access_token", response.body()?.accessToken)
                        .putString("expires_in", response.body()?.expiresIn)
                        .putString("refresh_token", response.body()?.refreshToken)
                        .apply()

                    Result.Authorized(
                        data = getCurrentAuthUser().data
                    )
                } else {
                    val errors = convertErrorBody(
                        response.errorBody()
                    )

                    Result.ApiError(
                        data = errors
                    )
                }
            } catch (e: HttpException) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.Unauthorized()
            } catch (e: IOException) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.ApiError(
                    data = ErrorResponse(
                        message = "Please connect to the internet"
                    )
                )
            } catch (e: Exception) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.Unauthorized()
            }
        }
    }

    override suspend fun getCurrentAuthUser(): Result<out Any> {
        return withContext(Dispatchers.IO) {
            try {
                val token = prefs.getString("access_token", null)

                val response = service.users("Bearer $token")

                if (response.isSuccessful) {
                    Result.Authorized(
                        data = response.body()
                    )
                } else {
                    val errors = convertErrorBody(
                        response.errorBody()
                    )

                    Result.ApiError(
                        data = errors
                    )
                }
            } catch (e: HttpException) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.Unauthorized()
            } catch (e: IOException) {
                Result.ApiError(
                    data = ErrorResponse(
                        message = "Please to the internet"
                    )
                )
            } catch (e: Exception) {
                Log.w("AuthRepositoryImpl", e.toString())
                Result.Unauthorized()
            }
        }
    }

    override suspend fun logout(): Result<out Any> {
        return withContext(Dispatchers.IO) {
            try {
                val token = prefs.getString("access_token", null)

                val response = service.logout("Bearer $token")

                if (response.isSuccessful) {
                    prefs.edit()
                        .putString("token_type", null)
                        .putString("access_token", null)
                        .putString("expires_in", null)
                        .putString("refresh_token", null)
                        .apply()

                    Result.Unauthorized()
                } else {
                    Result.Authorized()
                }
            } catch (e: HttpException) {
                Result.Unauthorized()
            } catch (e: IOException) {
                Result.ApiError(
                    data = ErrorResponse(
                        message = "Please to the internet"
                    )
                )
            } catch (e: Exception) {
                Result.Unauthorized()
            }
        }
    }
}