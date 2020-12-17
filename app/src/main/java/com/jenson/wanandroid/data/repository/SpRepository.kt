package com.jenson.wanandroid.data.repository

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.jenson.core.data.base.BaseRepository
import com.jenson.wanandroid.Const
import com.jenson.wanandroid.data.bean.User

/**
 *  author: CDJenson
 *  date: 2020/9/25 16:30
 *	version: 1.0
 *  description: One-sentence description
 */
class SpRepository : BaseRepository() {

    fun saveUsername(username: String) {
        SP.put(Const.SP_USERNAME, username)
    }

    fun loadUsername(): String {
        return SP.getString(Const.SP_USERNAME)
    }

    fun savePassword(password: String) {
        SP.put(Const.SP_PASSWORD, password)
    }

    fun loadPassword(): String {
        return SP.getString(Const.SP_PASSWORD)
    }

    fun saveUser(user: User?) {
        user?.let { SP.put(Const.SP_USER, GsonUtils.toJson(user)) }
    }

    fun loadUser(): User? {
        val strUser = SP.getString(Const.SP_USER, null)
        return if (strUser != null) GsonUtils.fromJson(strUser, User::class.java) else null
    }

    companion object {
        val INSTANCE by lazy {
            SpRepository()
        }

        private val SP by lazy { SPUtils.getInstance() }
    }
}