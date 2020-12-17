package com.jenson.wanandroid

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.jenson.core.widget.loadsir.EmptyCallback
import com.jenson.core.widget.loadsir.ErrorCallback
import com.jenson.core.widget.loadsir.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator

/**
 *  author: CDJenson
 *  date: 2020/8/11 17:46
 *	version: 1.0
 *  description: One-sentence description
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        //工具类
        Utils.init(this)
        LogUtils.getConfig().run {
            setLogHeadSwitch(false)
            setBorderSwitch(false)
        }

        //全局配置
        with(LoadSir.beginBuilder()){
            addCallback(LoadingCallback())
            addCallback(EmptyCallback())
            addCallback(ErrorCallback())
            setDefaultCallback(SuccessCallback::class.java)
            commit()
        }
    }

    companion object{
        lateinit var CONTEXT:Context

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator { context, _ ->  MaterialHeader(context)})
            SmartRefreshLayout.setDefaultRefreshFooterCreator(DefaultRefreshFooterCreator { context, _ ->  ClassicsFooter(context)})
        }
    }
}
