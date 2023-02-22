package com.example.loginapplicationnl.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.R
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.databinding.FragmentLoginBinding
import com.example.loginapplicationnl.utils.ViewUtils.hideKeyboard
import com.example.loginapplicationnl.utils.exts.onClickListenerDelay
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    private val viewModel: LoginViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentLoginBinding.inflate(inflater)

    override fun setUpView() {
    }

    override fun registerLiveData() {
        viewModel.login.observe(this) {
            when (it) {
                is LoginViewModel.LoginState.Loading -> showLoading()
                is LoginViewModel.LoginState.Successful -> {
                    hideLoading()
                    findNavController().navigate(
                        R.id.action_loginFragment_to_welcomeFragment,
                        args = Bundle().apply {
                            putParcelable("LoginResponse", it.loginResponses)
                        })
                }
                is LoginViewModel.LoginState.Failure -> {
                    showToast("Error get API")
                    hideLoading()
                }
            }
        }
    }

    override fun registerEvent() {
        viewBinding.txtCreateAccount.setOnClickListener() {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        viewBinding.btnLogin.onClickListenerDelay() {
            hideKeyboard()
            val stringEmail = viewBinding.edtEmail.text.toString().trim()
            val stringPassword = viewBinding.edtPassword.text.toString().trim()
            if (!validateUserEmailorMobile() or !validateUserPassword()) {
                showToast("Validation error")
                return@onClickListenerDelay
            } else {
                viewModel.loginUser(email = stringEmail, pwd = stringPassword)
            }
        }
    }

    //TODO move to ViewModel
    private fun validateUserEmailorMobile(): Boolean {
        if (viewBinding.edtEmail.text.toString().isEmpty()
        ) {
            viewBinding.txtEmailErrorMessage.visibility = View.VISIBLE
            return false
        } else {
            viewBinding.txtEmailErrorMessage.visibility = View.INVISIBLE
            viewBinding.txtEmailErrorMessage.error = null
        }
        return true
    }

    //TODO move to ViewModel
    private fun validateUserPassword(): Boolean {
        if (viewBinding.edtPassword.text.toString().isEmpty()
        ) {
            viewBinding.txtPasswordErrorMessage.visibility = View.VISIBLE
            return false
        } else {
            viewBinding.txtPasswordErrorMessage.visibility = View.INVISIBLE
            viewBinding.txtPasswordErrorMessage.error = null
        }
        return true
    }
}