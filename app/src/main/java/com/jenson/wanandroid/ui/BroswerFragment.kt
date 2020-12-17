package com.jenson.wanandroid.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.databinding.FragmentBroswerBinding
import com.jenson.wanandroid.viewmodel.BroswerViewModel
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.fragment_broswer.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 *  author: CDJenson
 *  date: 2020/9/2 9:25
 *	version: 1.0
 *  description: One-sentence description
 */
class BroswerFragment:BaseMvvmFragment<FragmentBroswerBinding,BroswerViewModel>(R.layout.fragment_broswer) {

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel)

    override fun initLoadSirLayout(): View? = null

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        toolbar.run {
            title = getString(R.string.title_broswer)

            mAppCompatActivity?.setSupportActionBar(this)
            mAppCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            setNavigationOnClickListener {
                onBackPressedCallback()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(this,onBackPressed = {
            onBackPressedCallback()
        })

        broswer_webview.settings.run {
            //支持Javascript 与js交互
            javaScriptEnabled = true
            //支持通过JS打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
            //设置可以访问文件
            allowFileAccess = true
            //支持缩放
            setSupportZoom(true)
            //设置内置的缩放控件
            builtInZoomControls = true
            //自适应屏幕
            useWideViewPort = true
            //多窗口
            setSupportMultipleWindows(true)
            //设置编码格式
            defaultTextEncodingName = "utf-8"
            setAppCacheEnabled(true)
            domStorageEnabled = true
            setAppCacheMaxSize(Long.MAX_VALUE)
            //缓存模式
            cacheMode = WebSettings.LOAD_NO_CACHE
        }

        broswer_webview.run {
            webViewClient = object :WebViewClient(){
                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
                    mViewModel.visibility.set(View.VISIBLE)
                }

                override fun onPageFinished(p0: WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    mViewModel.visibility.set(View.GONE)
                }

                override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                    //防止加载网页时调起系统浏览器
                    p0?.loadUrl(p1)
                    return super.shouldOverrideUrlLoading(p0, p1)
                }

                override fun onReceivedSslError(p0: WebView?, p1: SslErrorHandler?, p2: SslError?) {
                    p1?.proceed()//忽略SSL证书错误
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    mViewModel.progress.set(p1)
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { mAppCompatActivity?.supportActionBar?.title = p1 }
                }
            }

            broswer_webview.loadUrl(arguments?.getString(URL))
        }
    }


    override fun initUiEvent() {

    }

    private val onBackPressedCallback = {
        if (broswer_webview.canGoBack()){
            broswer_webview.goBack()
        }else{
            findNavController().navigateUp()
        }
    }

    companion object{
        const val URL = "url"
    }

}