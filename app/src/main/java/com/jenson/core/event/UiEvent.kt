package com.jenson.core.event

/**
 *  author: CDJenson
 *  date: 2020/9/27 15:56
 *	version: 1.0
 *  description: One-sentence description
 */
class UiEvent {

    val showProgressBarDialog = SingleLiveEvent<Boolean>()

    val showToast = SingleLiveEvent<String>()
}