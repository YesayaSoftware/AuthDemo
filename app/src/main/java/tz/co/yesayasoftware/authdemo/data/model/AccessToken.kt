package tz.co.yesayasoftware.authdemo.data.model

import com.squareup.moshi.Json

data class AccessToken(
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_in")
    val expiresIn: String,
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "refresh_token")
    val refreshToken: String
)