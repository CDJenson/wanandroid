package com.jenson.wanandroid.data.bean

/**
 *  author: CDJenson
 *  date: 2020/9/4 11:15
 *	version: 1.0
 *  description: One-sentence description
 */
data class ProjectType(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)