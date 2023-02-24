package com.example.loginapplicationnl.ui.signUp

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapplicationnl.R
import com.example.loginapplicationnl.base.BaseViewModel
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.model.SignUpRequest
import com.example.loginapplicationnl.data.repository.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpRepository: SignUpRepository) : BaseViewModel(),TextWatcher {

    private val _validateFirstNameObs = MutableLiveData<ValidateFirstNameState>()
    val validateFirstNameObs: LiveData<ValidateFirstNameState>
        get() = _validateFirstNameObs

    private val _validateLastNameObs = MutableLiveData<ValidateLastNameState>()
    val validateLastNameObs: LiveData<ValidateLastNameState>
        get() = _validateLastNameObs

    private val _validateEmailObs = MutableLiveData<ValidateEmailState>()
    val validateEmailObs: LiveData<ValidateEmailState>
        get() = _validateEmailObs

    private val _validatePasswordObs = MutableLiveData<ValidatePasswordState>()
    val validatePasswordObs: LiveData<ValidatePasswordState>
        get() = _validatePasswordObs

    private val _signup = MutableLiveData<SignUpState>()
    val signup: LiveData<SignUpState>
        get() = _signup

    var strengthLevel: MutableLiveData<String> = MutableLiveData()
    var strengthColor: MutableLiveData<Int> = MutableLiveData()

    enum class ValidateEmailState {
        INVALID_EMAIL_EMPTY,
        INVALID_EMAIL_FORMAT,
    }

    enum class ValidateFirstNameState {
        INVALID_FIRST_NAME_EMPTY,
        INVALID_FIRST_NAME_CHAR,
    }

    enum class ValidateLastNameState {
        INVALID_LAST_NAME_EMPTY,
        INVALID_LAST_NAME_CHAR,
    }

    enum class ValidatePasswordState {
        INVALID_PWD_EMPTY,
        INVALID_PWD_FORMAT
    }

    sealed class SignUpState {
        object Loading : SignUpState()
        class Successful(val loginResponses: LoginResponses) : SignUpState()
        object Failure : SignUpState()
    }

    fun signUpUser(firstName: String, lastName: String, email: String, pwd: String) {
        _signup.postValue(SignUpState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val signUpRequest = SignUpRequest(
                    firstName = firstName,
                    lastName = lastName,
                    password = pwd,
                    email = email
                )
                val res = signUpRepository.postSignUp(signUpRequest)
                _signup.postValue(SignUpState.Successful(res))
            } catch (ex: Exception) {
                _signup.postValue(SignUpState.Failure)
            }
        }
    }

    fun validateInputEmail(emailValid: String): Boolean {
        if (emailValid.isEmpty()) {
            _validateEmailObs.postValue(ValidateEmailState.INVALID_EMAIL_EMPTY)
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValid).matches()) {
            _validateEmailObs.postValue(ValidateEmailState.INVALID_EMAIL_FORMAT)
            return false
        }
        return true
    }

    fun validateInputPassword(passwordValid: String): Boolean {
        if (passwordValid.isEmpty()) {
            _validatePasswordObs.postValue(ValidatePasswordState.INVALID_PWD_EMPTY)
            return false
        } else if (passwordValid.length < 8) {
            _validatePasswordObs.postValue(ValidatePasswordState.INVALID_PWD_FORMAT)
            return false
        }
        return true
    }

    fun validateInputFirstName(firstName: String): Boolean {
        if (firstName.isEmpty()) {
            _validateFirstNameObs.postValue(ValidateFirstNameState.INVALID_FIRST_NAME_EMPTY)
            return false
        } else if (firstName.length < 3) {
            _validateFirstNameObs.postValue(ValidateFirstNameState.INVALID_FIRST_NAME_CHAR)
            return false
        }
        return true
    }

    fun validateInputLastName(lastName: String): Boolean {
        if (lastName.isEmpty()) {
            _validateLastNameObs.postValue(ValidateLastNameState.INVALID_LAST_NAME_EMPTY)
            return false
        } else if (lastName.length < 3) {
            _validateLastNameObs.postValue(ValidateLastNameState.INVALID_LAST_NAME_CHAR)
            return false
        }
        return true
    }

    private fun calculateStrength(password: CharSequence) {
        when (password.length) {
            in 0..3 -> {
                strengthColor.postValue(R.color.weak)
                strengthLevel.postValue("Password is week")
            }
            in 4..6 -> {
                strengthColor.postValue(R.color.fair)
                strengthLevel.postValue("Password is week")
            }
            in 7..9 -> {
                strengthColor.postValue(R.color.good)
                strengthLevel.postValue("Password is good")
            }
            in 10..28 -> {
                strengthColor.postValue(R.color.strong)
                strengthLevel.postValue("Password is strong")
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}

    override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (char!=null){
            calculateStrength(char)
        }
    }

}