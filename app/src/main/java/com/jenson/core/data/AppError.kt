package com.jenson.core.data

/**
 *  author: CDJenson
 *  date: 2020/8/14 17:55
 *	version: 1.0
 *  description: One-sentence description
 */
enum class AppError private constructor(val code: Int, val message: String) {

    UNKNOWN(1000,"未知异常"),

    PASE_ERROR(1001,"数据异常"),

    SERVER_ERROR(1002,"服务器请求异常"),

    NETWORK_ERROR(1003,"网络连接异常"),
}