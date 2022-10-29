package tz.co.yesayasoftware.authdemo.data.utils

sealed class Result <T>(val data: T? = null) {
    class Authorized<T>(data: T? = null): Result<T>(data)
    class Unauthorized<T>: Result<T>()
    class ApiError<T>(data: T? = null): Result<T>(data)
}
