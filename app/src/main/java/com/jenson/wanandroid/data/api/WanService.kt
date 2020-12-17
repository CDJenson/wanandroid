package com.jenson.wanandroid.data.api

import com.jenson.wanandroid.data.bean.Article
import com.jenson.wanandroid.data.bean.BannerBean
import com.jenson.wanandroid.data.bean.ProjectType
import com.jenson.wanandroid.data.bean.User
import com.jenson.wanandroid.data.response.WanPageResponse
import com.jenson.wanandroid.data.response.WanResponse
import retrofit2.http.*

/**
 *  author: CDJenson
 *  date: 2020/8/13 10:05
 *	version: 1.0
 *  description: One-sentence description
 */
interface WanService {

    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<ArrayList<BannerBean>>

    @GET("/article/top/json")
    suspend fun getArticleListTop(): WanResponse<ArrayList<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): WanResponse<WanPageResponse<ArrayList<Article>>>

    @GET("/project/tree/json")
    suspend fun getProjectTypeList(): WanResponse<ArrayList<ProjectType>>

    @GET("/project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int):WanResponse<WanPageResponse<ArrayList<Article>>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") username:String,@Field("password") password:String):WanResponse<User>
}