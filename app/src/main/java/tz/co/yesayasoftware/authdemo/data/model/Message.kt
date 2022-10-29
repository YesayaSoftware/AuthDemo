package tz.co.yesayasoftware.authdemo.data.model

import com.squareup.moshi.Json

data class Message(
    @Json(name = "message")
    val message: String
)