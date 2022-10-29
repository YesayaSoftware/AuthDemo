package tz.co.yesayasoftware.authdemo.data.model

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "errors")
    var errors: Map<String, List<String>>? = null,

    @Json(name = "message")
    var message: String? = null
)