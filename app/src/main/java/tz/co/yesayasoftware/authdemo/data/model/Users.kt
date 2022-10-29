package tz.co.yesayasoftware.authdemo.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "email_verified_at")
    val emailVerifiedAt: String? = null,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "updated_at")
    val updatedAt: String? = null
) : Parcelable