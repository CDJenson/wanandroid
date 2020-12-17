package com.jenson.core.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jenson.core.widget.loadsir.EmptyCallback
import com.jenson.core.widget.loadsir.ErrorCallback
import com.jenson.core.widget.loadsir.LoadingCallback
import com.kingja.loadsir.core.LoadService
import java.lang.reflect.ParameterizedType

/**
 *  author: CDJenson
 *  date: 2020/8/12 14:32
 *	version: 1.0
 *  description: One-sentence description
 */

@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VM
}

fun < VM : BaseViewModel> Fragment.getViewModel(): VM {
    return ViewModelProvider(this).get(getVmClazz(this))
}

fun <VM : BaseViewModel> AppCompatActivity.getViewModel(): VM {
    return ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory(application)
    ).get(getVmClazz(this))
}

fun LoadService<*>.showEmpty(){
    showCallback(EmptyCallback::class.java)
}

fun LoadService<*>.showError(){
    showCallback(ErrorCallback::class.java)
}

fun LoadService<*>.showLoading(){
    showCallback(LoadingCallback::class.java)
}