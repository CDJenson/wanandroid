package com.jenson.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(var name: String, var url: String) : Parcelable