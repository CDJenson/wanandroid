package com.jenson.core.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.jenson.core.event.UiEvent

/**
 *  author: CDJenson
 *  date: 2020/8/12 11:28
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class BaseViewModel : ViewModel() {

    protected var mArguments:Bundle ? = null

    fun setArguments(arguments:Bundle?){
        mArguments = arguments
    }

    abstract fun initData()

    fun showProgressBar(){

    }

    fun showToast(message:String){

    }
}

