package com.jenson.core.data.base

/**
 *  author: CDJenson
 *  date: 2020/8/13 10:19
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class BaseResponse<out T> {

    abstract fun isSuccess(): Boolean

    abstract fun getResponseCode():Int

    abstract fun getResponseMsg():String

    abstract fun getResponseData():T
}