package com.example.loginapplicationnl.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.R
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.databinding.FragmentLoginBinding
import com.example.loginapplicationnl.ui.login.LoginViewModel.ValidateEmailState.INVALID_EMAIL_EMPTY
import com.example.loginapplicationnl.ui.login.LoginViewModel.ValidateEmailState.INVALID_EMAIL_FORMAT
import com.example.loginapplicationnl.ui.login.LoginViewModel.ValidatePasswordState.INVALID_PWD_EMPTY
import com.example.loginapplicationnl.ui.login.LoginViewModel.ValidatePasswordState.INVALID_PWD_FORMAT
import com.example.loginapplicationnl.utils.ViewUtils.hideKeyboard
import com.example.loginapplicationnl.utils.exts.invisible
import com.example.loginapplicationnl.utils.exts.onClickListenerDelay
import com.example.loginapplicationnl.utils.exts.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    private val viewModel: LoginViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentLoginBinding.inflate(inflater)

    override fun setUpView() {
        // Fake data login
        viewBinding.run {
            edtEmail.setText("test1@gmail.com")
            edtPassword.setText("12345678")
        }
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
        viewModel.validateEmailObs.observe(this) {
            when (it) {
                INVALID_EMAIL_EMPTY -> {
                    viewBinding.run {
                        txtEmailErrorMessage.show()
                        edtEmail.requestFocus()
                    }
                }
                INVALID_EMAIL_FORMAT -> {
                    viewBinding.txtEmailErrorMessage.text = getString(R.string.msg_email_valid)
                    viewBinding.txtEmailErrorMessage.show()
                }
                else -> showToast("Error")
            }
        }
        viewModel.validatePasswordObs.observe(this) {
            when (it) {
                INVALID_PWD_EMPTY -> {
                    viewBinding.run {
                        txtPasswordErrorMessage.show()
                        edtPassword.requestFocus()
                    }
                }
                INVALID_PWD_FORMAT -> {
                    viewBinding.txtPasswordErrorMessage.text =
                        getString(R.string.msg_password_message)
                    viewBinding.txtPasswordErrorMessage.show()
                }
                else -> showToast(getString(R.string.msg_error))
            }
        }
    }

    override fun registerEvent() {
        // Navigation to create account
        viewBinding.txtCreateAccount.setOnClickListener() {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        // Handle button login
        viewBinding.btnLogin.onClickListenerDelay() {
            hideKeyboard()
            viewBinding.txtEmailErrorMessage.invisible()
            viewBinding.txtPasswordErrorMessage.invisible()
            val stringEmail = viewBinding.edtEmail.text.toString().trim()
            val stringPassword = viewBinding.edtPassword.text.toString().trim()
            val booleanRemember = viewBinding.cbRemember.isChecked
            if (!viewModel.validateInputEmail(stringEmail)
                or !viewModel.validateInputPassword(stringPassword)
            ) {
                showToast("Validation error")
                return@onClickListenerDelay
            } else {
                viewModel.loginUser(
                    email = stringEmail,
                    pwd = stringPassword,
                    saveStatus = booleanRemember
                )
            }
        }
    }
}