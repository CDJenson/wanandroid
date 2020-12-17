package com.jenson.wanandroid.data.repository

import com.jenson.core.data.base.BaseRepository
import com.jenson.wanandroid.Const
import com.jenson.wanandroid.data.api.WanRetrofitClient
import com.jenson.wanandroid.data.api.WanService
import com.jenson.wanandroid.data.bean.Article
import com.jenson.wanandroid.data.response.WanPageResponse
import com.jenson.wanandroid.data.response.WanResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 *  author: CDJenson
 *  date: 2020/8/13 14:09
 *	version: 1.0
 *  description: One-sentence description
 */
class WanRepository private constructor() : BaseRepository() {

    private val wanService: WanService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        WanRetrofitClient.INSTANCE.getService(WanService::class.java, Const.BASE_URL)
    }

    suspend fun getArticleList(page: Int) = wanService.getArticleList(page)

    suspend fun getArticleListTop() = wanService.getArticleListTop()

    suspend fun getBanner() = wanService.getBanner()

    suspend fun getHomeArticleList(page: Int): WanResponse<WanPageResponse<ArrayList<Article>>> {
        return withContext(Dispatchers.IO) {
            val request = async { getArticleList(page) }
            if (page == 0) {
                val requestTop = async { getArticleListTop() }
                request.await().data.datas.addAll(0, requestTop.await().data)
                request.await()
            } else {
                request.await()
            }
        }
    }

    suspend fun getProjectTypeList() = wanService.getProjectTypeList()

    suspend fun getProjectList(page: Int, cid: Int) = wanService.getProjectList(page, cid)

    suspend fun login(username:String,password:String) = wanService.login(username,password)

    companion object {
        val INSTANCE by lazy {
            WanRepository()
        }
    }
}
