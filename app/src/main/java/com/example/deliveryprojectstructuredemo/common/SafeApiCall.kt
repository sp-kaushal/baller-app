package com.example.deliveryprojectstructuredemo.common

import com.example.deliveryprojectstructuredemo.data.ErrorResponse
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                /*    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)*/
                    val code = throwable.code()
                    val errorBody = throwable.response()?.errorBody()?.string()
//                    Timber.d(code.toString())
//                    Timber.d(errorBody)
                    val gsonErrorBody = Gson().fromJson(
                        errorBody,
                        ErrorResponse::class.java
                    )
                    val message = gsonErrorBody.errors.toString()
                    ResultWrapper.GenericError(code, message)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }

        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}