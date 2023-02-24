package com.example.loginapplicationnl.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.R
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.databinding.FragmentSignUpBinding
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel.ValidateEmailState.INVALID_EMAIL_EMPTY
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel.ValidateEmailState.INVALID_EMAIL_FORMAT
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel.ValidateFirstNameState.INVALID_FIRST_NAME_CHAR
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel.ValidateFirstNameState.INVALID_FIRST_NAME_EMPTY
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel.ValidateLastNameState.INVALID_LAST_NAME_CHAR
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel.ValidateLastNameState.INVALID_LAST_NAME_EMPTY
import com.example.loginapplicationnl.utils.ViewUtils.hideKeyboard
import com.example.loginapplicationnl.utils.exts.invisible
import com.example.loginapplicationnl.utils.exts.onClickListenerDelay
import com.example.loginapplicationnl.utils.exts.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    private val viewModel: SignUpViewModel by viewModel()

    private var color:Int = R.color.weak

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentSignUpBinding.inflate(inflater)

    override fun setUpView() {

    }

    override fun registerLiveData() {
        /*validation first name*/
        viewModel.validateFirstNameObs.observe(this) {
            when (it) {
                INVALID_FIRST_NAME_EMPTY -> {
                    viewBinding.txtFirstNameErrorMessage.show()
                    viewBinding.edtFirstName.requestFocus()
                }
                INVALID_FIRST_NAME_CHAR -> {
                    viewBinding.txtFirstNameErrorMessage.text =
                        getString(R.string.msg_char_first_name)
                    viewBinding.txtFirstNameErrorMessage.show()
                }
                else -> showToast("Error")
            }
        }

        /*validation last name*/
        viewModel.validateLastNameObs.observe(this) {
            when (it) {
                INVALID_LAST_NAME_EMPTY -> {
                    viewBinding.txtLastNameErrorMessage.show()
                    viewBinding.edtLastName.requestFocus()
                }
                INVALID_LAST_NAME_CHAR -> {
                    viewBinding.txtLastNameErrorMessage.text =
                        getString(R.string.mes_char_last_name)
                    viewBinding.txtLastNameErrorMessage.show()
                }
                else -> showToast("Error")
            }
        }

        /*validation email*/
        viewModel.validateEmailObs.observe(this) {
            when (it) {
                INVALID_EMAIL_EMPTY -> {
                    viewBinding.txtEmailErrorMessage.show()
                    viewBinding.edtEmail.requestFocus()
                }
                INVALID_EMAIL_FORMAT -> {
                    viewBinding.txtEmailErrorMessage.text = getString(R.string.msg_email_valid)
                    viewBinding.txtEmailErrorMessage.show()
                }
                else -> showToast("Error")
            }
        }
        /*validation password*/
        viewModel.validatePasswordObs.observe(this) {
            when (it) {
                SignUpViewModel.ValidatePasswordState.INVALID_PWD_EMPTY -> {
                    viewBinding.txtPasswordErrorMessage.show()
                    viewBinding.edtPassword.requestFocus()
                }
                SignUpViewModel.ValidatePasswordState.INVALID_PWD_FORMAT -> {
                    viewBinding.txtPasswordErrorMessage.text =
                        getString(R.string.msg_password_message)
                    viewBinding.txtPasswordErrorMessage.show()
                }
                else -> showToast("Error")
            }
        }
        /*check signup button*/
        viewModel.signup.observe(this) {
            when (it) {
                is SignUpViewModel.SignUpState.Loading -> showLoading()
                is SignUpViewModel.SignUpState.Successful -> {
                    hideLoading()
                    findNavController().navigate(
                        R.id.action_signUpFragment_to_welcomeFragment,
                        args = Bundle().apply {
                            putParcelable("LoginResponse", it.loginResponses)
                        })
                }
                is SignUpViewModel.SignUpState.Failure -> {
                    showToast("Error get API")
                    hideLoading()
                }
            }
        }

        viewModel.strengthLevel.observe(this) {
            viewBinding.txtStatusPassword.text = it.toString()
        }

        viewModel.strengthColor.observe(this) {
            viewBinding.txtStatusPassword.textColors
            viewBinding.txtStatusPassword.setTextColor(it)
        }
    }

    override fun registerEvent() {
        signIn()
        handlePasswordStatus()
        checkValidation()
    }

    private fun handlePasswordStatus() {
        val passwordCalculator = viewModel
        viewBinding.edtPassword.addTextChangedListener(passwordCalculator)
    }

    private fun checkValidation() {
        viewBinding.btnSignUp.onClickListenerDelay() {
            hideKeyboard()
            viewBinding.txtStatusPassword.visibility = View.GONE
            viewBinding.txtFirstNameErrorMessage.invisible()
            viewBinding.txtLastNameErrorMessage.invisible()
            viewBinding.txtEmailErrorMessage.invisible()
            viewBinding.txtPasswordErrorMessage.invisible()
            val stringFirstName = viewBinding.edtFirstName.text.toString().trim()
            val stringLastName = viewBinding.edtLastName.text.toString().trim()
            val stringEmail = viewBinding.edtEmail.text.toString().trim()
            val stringPassword = viewBinding.edtPassword.text.toString().trim()
            val cbAgree = viewBinding.cbAgree.isChecked
            if (!viewModel.validateInputFirstName(stringFirstName)
                or !viewModel.validateInputLastName(stringLastName)
                or !viewModel.validateInputEmail(stringEmail)
                or !viewModel.validateInputPassword(stringPassword)
            ) {
                showToast("Validation error")
                return@onClickListenerDelay
            } else if (!cbAgree) {
                showToast(getString(R.string.mes_checkbox_press_agree))
            } else {
                viewModel.signUpUser(
                    firstName = stringFirstName,
                    lastName = stringLastName,
                    email = stringEmail,
                    pwd = stringPassword
                )
            }
        }
    }

    private fun signIn() {
        viewBinding.txtSignIn.setOnClickListener() {
            findNavController().navigateUp()
        }
    }
}