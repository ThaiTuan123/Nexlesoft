package com.example.loginapplicationnl.ui.signUp

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.loginapplicationnl.base.BaseFragment
import com.example.loginapplicationnl.databinding.FragmentSignUpBinding
import com.example.loginapplicationnl.utils.ViewUtils.hideKeyboard
import com.example.loginapplicationnl.utils.exts.onClickListenerDelay
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    private val viewModel: SignUpViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentSignUpBinding.inflate(inflater)

    override fun setUpView() {

    }

    override fun registerLiveData() {
        //TODO("Not yet implemented")
    }

    override fun registerEvent() {
        signIn()
        checkValidation()
    }

    private fun checkValidation() {
        viewBinding.btnSignUp.onClickListenerDelay() {
            hideKeyboard()
            val stringFirstName = viewBinding.edtFirstName.text.toString().trim()
            val stringLastName = viewBinding.edtLastName.text.toString().trim()
            val stringEmail = viewBinding.edtEmail.text.toString().trim()
            val stringPassword = viewBinding.edtPassword.text.toString().trim()
            if (!validateFirstName() or !validateLastName() or !validateUserEmailorMobile() or !validateUserPassword()) {
                showToast("Validation error")
                return@onClickListenerDelay
            } else {
                viewModel.signUpUser(firstName = stringFirstName, lastName = stringLastName,email = stringEmail, pwd = stringPassword)
            }
        }
    }

    private fun signIn() {
        viewBinding.txtSignIn.setOnClickListener() {
            findNavController().navigateUp()
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

    //TODO move to ViewModel
    private fun validateFirstName(): Boolean {
        if (viewBinding.edtFirstName.text.toString().isEmpty()
        ) {
            viewBinding.txtFirstNameErrorMessage.visibility = View.VISIBLE
            return false
        } else {
            viewBinding.txtFirstNameErrorMessage.visibility = View.INVISIBLE
            viewBinding.txtFirstNameErrorMessage.error = null
        }
        return true
    }

    //TODO move to ViewModel
    private fun validateLastName(): Boolean {
        if (viewBinding.edtLastName.text.toString().isEmpty()
        ) {
            viewBinding.txtLastNameErrorMessage.visibility = View.VISIBLE
            return false
        } else {
            viewBinding.txtLastNameErrorMessage.visibility = View.INVISIBLE
            viewBinding.txtLastNameErrorMessage.error = null
        }
        return true
    }
}