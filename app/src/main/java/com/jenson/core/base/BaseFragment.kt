package com.jenson.core.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 *  author: CDJenson
 *  date: 2020/8/7 14:45
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId), IBaseView {

    protected var mAppCompatActivity: AppCompatActivity? = null
    protected var mLoadService: LoadService<Any>? = null
    protected var mIsFirst = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mAppCompatActivity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        if(mIsFirst){
            mIsFirst = false
            initLoadSirLayout()?.let {
                if (mLoadService == null) {
                    mLoadService = LoadSir.getDefault().register(it) {
                        initData()
                    }
                }
            }
            initUiEvent()
            initData()
        }
    }
}