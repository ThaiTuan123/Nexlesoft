package com.example.loginapplicationnl.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapplicationnl.base.BaseViewModel
import com.example.loginapplicationnl.data.model.LoginRequest
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    private val _validateEmailObs = MutableLiveData<ValidateEmailState>()
    val validateEmailObs: LiveData<ValidateEmailState>
        get() = _validateEmailObs

    private val _validatePasswordObs = MutableLiveData<ValidatePasswordState>()
    val validatePasswordObs: LiveData<ValidatePasswordState>
        get() = _validatePasswordObs

    private val _login = MutableLiveData<LoginState>()
    val login: LiveData<LoginState>
        get() = _login

    enum class ValidateEmailState {
        INVALID_EMAIL_EMPTY,
        INVALID_EMAIL_FORMAT,
    }

    enum class ValidatePasswordState {
        INVALID_PWD_EMPTY,
        INVALID_PWD_FORMAT
    }

    sealed class LoginState {
        object Loading : LoginState()
        class Successful(val loginResponses: LoginResponses) : LoginState()
        object Failure : LoginState()
    }

    fun loginUser(email: String, pwd: String, saveStatus: Boolean) {
        _login.postValue(LoginState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val res = loginRepository.postLogin(loginRequest);
                _login.postValue(LoginState.Successful(res))
                // Setting remember
                if (saveStatus) {
                    // TODO save remember
                }
            } catch (ex: Exception) {
                _login.postValue(LoginState.Failure)
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
}