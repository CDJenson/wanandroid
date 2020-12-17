package com.jenson.wanandroid.ui

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.data.repository.SpRepository
import com.jenson.wanandroid.databinding.FragmentLoginBinding
import com.jenson.wanandroid.ext.showProgressDialog
import com.jenson.wanandroid.viewmodel.LoginViewModel
import com.jenson.wanandroid.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 *  author: CDJenson
 *  date: 2020/9/11 10:01
 *	version: 1.0
 *  description: One-sentence description
 */
class LoginFragment : BaseMvvmFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    private val mSharedViewModel by activityViewModels<SharedViewModel>()
    private var mProgressDialog: AlertDialog? = null

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel).addExtra(BR.clickProxy, ClickProxy())

    override fun initLoadSirLayout(): View? = null

    override fun initView() {
        toolbar.run {
            title = getString(R.string.title_login)
            inflateMenu(R.menu.login_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_close -> findNavController().navigateUp()
                }
                true
            }
        }
    }

    override fun initUiEvent() {
        mViewModel.result.observe(this) {
            if (it.isSuccess) {
                SpRepository.INSTANCE.saveUsername(mViewModel.username.get().toString())
                SpRepository.INSTANCE.savePassword(mViewModel.password.get().toString())
                SpRepository.INSTANCE.saveUser(it.data)

                mSharedViewModel.user.value = it.data
                findNavController().navigateUp()
            }
        }
        mViewModel.showProgressBar.observe(this) {
            if (it) {
                mProgressDialog = context?.showProgressDialog(getString(R.string.login_dialog_message))
            } else {
                mProgressDialog?.hide()
            }
        }
    }

    inner class ClickProxy {

        fun onTextChangedOfUserName(text: CharSequence, start: Int, before: Int, count: Int) {
            login_til_username.error?.let {
                if (text.isNotEmpty() && it.isNotEmpty()) {
                    login_til_username.error = null
                }
            }
        }

        fun onTextChangedOfPassword(text: CharSequence, start: Int, before: Int, count: Int) {
            login_til_password.error?.let {
                if (text.isNotEmpty() && it.isNotEmpty()) {
                    login_til_password.error = null
                }
            }
        }

        fun login(view: View) {
            var canLogin = true
            val username = login_et_username.text.toString().trim()
            val password = login_et_password.text.toString().trim()
            if (username.isEmpty()) {
                login_til_username.error = "用户名不能为空"
                canLogin = false
            }
            if (password.isEmpty()) {
                login_til_password.error = "密码不能为空"
                canLogin = false
            }
            if (canLogin) {
                mViewModel.login(username, password)
            }
        }
    }
}