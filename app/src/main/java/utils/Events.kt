package utils

import Model.NewsModel



sealed class Events<T>(val data: T? = null, val msg: String? = "") {
    class Success<T>(data: T?) : Events<T>(data)
    class Loading<T>(data: T? = null) : Events<T>()
    class Error<T>(data: T? = null, msg: String? = null) : Events<T>(data, msg)
}
