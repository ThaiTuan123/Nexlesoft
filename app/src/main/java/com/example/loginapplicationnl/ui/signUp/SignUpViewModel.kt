package com.example.loginapplicationnl.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapplicationnl.base.BaseViewModel
import com.example.loginapplicationnl.data.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel: BaseViewModel() {

    private val _signup = MutableLiveData<LoginState>()
    val signup: LiveData<LoginState>
        get() = _signup

    sealed class LoginState {
        object Loading : LoginState()
        object Successful : LoginState()
        object Failure : LoginState()
    }

    fun signUpUser(fistName:String,lastName:String,email: String, pwd: String) {
//        _login.postValue(LoginState.Loading)
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val loginRequest = LoginRequest(
//                    password = pwd,
//                    email = email
//                )
//                loginRepository.postLogin(loginRequest);
//                _login.postValue(LoginState.Successful)
//            } catch (ex: Exception) {
//                _login.postValue(LoginState.Failure)
//            }
//        }
    }

}