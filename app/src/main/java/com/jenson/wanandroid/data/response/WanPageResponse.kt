package com.jenson.wanandroid.data.response

import java.io.Serializable

/**
 *  author: CDJenson
 *  date: 2020/8/13 10:43
 *	version: 1.0
 *  description: One-sentence description
 */
data class WanPageResponse<T>(
    val datas: T,
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) : Serializable {

    fun isEmpty() = (datas as List<*>).isEmpty()

    fun isNoMore() = (datas as List<*>).size < size
}