package com.jenson.wanandroid.data.result

/**
 *  author: CDJenson
 *  date: 2020/8/19 16:32
 *	version: 1.0
 *  description: One-sentence description
 */
class ListResult<T> {

    var data: ArrayList<T> = arrayListOf()

    var isSuccess: Boolean = false

    var isRefresh: Boolean = false
        set(value) {
            field = value
            if (field) isLoadMore = false
        }

    var isLoadMore: Boolean = false
        set(value) {
            field = value
            if (field) isRefresh = false
        }

    var isNoMore: Boolean = false
}