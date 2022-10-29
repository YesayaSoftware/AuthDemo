package tz.co.yesayasoftware.authdemo.data.model

import com.squareup.moshi.Json

data class Errors(
    @Json(name = "email")
    val email: List<String>,
    @Json(name = "password")
    val password: List<String>
)