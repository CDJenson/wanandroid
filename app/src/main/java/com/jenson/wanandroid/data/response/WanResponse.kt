package com.jenson.wanandroid.data.response

import com.jenson.core.data.base.BaseResponse

/**
 *  author: CDJenson
 *  date: 2020/8/13 10:26
 *	version: 1.0
 *  description: One-sentence description
 */
data class WanResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) :
    BaseResponse<T>() {

    override fun isSuccess() = errorCode == 0

    override fun getResponseCode() = errorCode

    override fun getResponseMsg() = errorMsg

    override fun getResponseData() = data
}