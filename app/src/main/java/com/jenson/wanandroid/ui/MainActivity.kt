package com.jenson.wanandroid.ui

import android.view.View
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.jenson.core.base.BaseMvvmActivity
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.databinding.ActivityMainBinding
import com.jenson.wanandroid.viewmodel.SharedViewModel

class MainActivity : BaseMvvmActivity<ActivityMainBinding,SharedViewModel>(R.layout.activity_main) {

    private var mExitTime = 0L

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel)

    override fun initLoadSirLayout(): View?  = null

    override fun initView() {
        onBackPressedDispatcher.addCallback(this,onBackPressed = {
            val nav = Navigation.findNavController(this@MainActivity, R.id.main_nav_host_fragment)
            if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainFragment) {
                nav.navigateUp()
            } else {
                if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
                    ToastUtils.showShort(R.string.toast_exit)
                    mExitTime = System.currentTimeMillis()
                } else {
                    finish()
                }
            }
        })
    }

    override fun initUiEvent() {

    }

    companion object{
        const val EXIT_TIME = 2000
    }
}