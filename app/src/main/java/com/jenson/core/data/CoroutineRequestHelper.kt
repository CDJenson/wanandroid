package com.jenson.core.data

import com.jenson.core.data.base.BaseResponse
import kotlinx.coroutines.*

/**
 *  author: CDJenson
 *  date: 2020/8/17 14:51
 *	version: 1.0
 *  description: One-sentence description
 */
open class CoroutineRequestHelper<T> private constructor() {

    private var onStart: (() -> Unit)? = null

    private var onSuccess: ((T) -> Unit)? = null

    private var onError: ((AppException) -> Unit)? = null

    private var onComplete: (() -> Unit)? = null

    private var load: (suspend () -> T)? = null

    fun onStart(onStart: () -> Unit) {
        this.onStart = onStart
    }

    fun onSuccess(onSuccess: (T) -> Unit) {
        this.onSuccess = onSuccess
    }

    fun onError(onError: (AppException) -> Unit) {
        this.onError = onError
    }

    fun onComplete(onComplete: () -> Unit) {
        this.onComplete = onComplete
    }

    fun load(load: suspend () -> T) {
        this.load = load
    }

    private fun run(scope: CoroutineScope): Job {
        return scope.launch {
            onStart?.invoke()
            runCatching {
                withContext(Dispatchers.IO) {
                    load?.invoke()
                }
            }.onSuccess {
                if (it is BaseResponse<*>) {
                    if (it.isSuccess()) {
                        onSuccess?.invoke(it)
                    } else {
                        onError?.invoke(
                            AppException(
                                it.getResponseCode(),
                                it.getResponseMsg()
                            )
                        )
                    }
                } else {
                    if (it != null) {
                        onSuccess?.invoke(it)
                    }
                }
            }.onFailure {
                onError?.invoke(it.parseToAppException())
            }
            onComplete?.invoke()
        }
    }


    companion object {
        fun <T> doRequest(scope: CoroutineScope, block: CoroutineRequestHelper<T>.() -> Unit): Job {
            val coroutineRequestHelper = CoroutineRequestHelper<T>()
            block(coroutineRequestHelper)
            return coroutineRequestHelper.run(scope)
        }
    }
}