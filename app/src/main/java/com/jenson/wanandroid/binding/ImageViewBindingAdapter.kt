package com.jenson.wanandroid.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *  author: CDJenson
 *  date: 2020/9/8 17:50
 *	version: 1.0
 *  description: One-sentence description
 */

@BindingAdapter("url")
fun ImageView.load(url:String){
    Glide.with(this).load(url).into(this)
}