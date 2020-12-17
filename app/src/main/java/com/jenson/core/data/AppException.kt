package com.jenson.core.data

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException


/**
 *  author: CDJenson
 *  date: 2020/8/14 17:33
 *	version: 1.0
 *  description: One-sentence description
 */
data class AppException(var code: Int, override var message: String, var log: String) :
    Exception() {

    constructor(code: Int, message: String) : this(
        code,
        message,
        message
    )

    constructor(error: AppError, throwable: Throwable) : this(
        error.code,
        error.message,
        throwable.toString()
    )
}


fun Throwable.parseToAppException(): AppException {
    val appException: AppException
    when (this@parseToAppException) {
        is HttpException -> {
            appException = AppException(AppError.SERVER_ERROR, this)
        }
        is ConnectException, is SocketTimeoutException, is SSLException, is UnknownHostException -> {
            appException = AppException(AppError.NETWORK_ERROR, this)
        }
        is JsonParseException, is JSONException, is ParseException, is IOException, is IllegalArgumentException -> {
            appException = AppException(AppError.PASE_ERROR, this)
        }
        else -> {
            appException = AppException(AppError.UNKNOWN, this)
        }
    }
    return appException
}