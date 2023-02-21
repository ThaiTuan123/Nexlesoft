package com.example.loginapplicationnl.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapplicationnl.base.BaseViewModel
import com.example.loginapplicationnl.data.model.LoginRequest
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.repository.LoginRepository
import com.example.loginapplicationnl.data.repository.LoginRepositoryImp
import com.example.loginapplicationnl.di.viewModelModule
import com.example.loginapplicationnl.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _login = MutableLiveData<LoginState>()
    val login: LiveData<LoginState>
        get() = _login

    sealed class LoginState {
        object Loading : LoginState()
        class Successful(val loginResponses: LoginResponses) : LoginState()
        object Failure : LoginState()
    }

    fun loginUser(email: String, pwd: String) {
        _login.postValue(LoginState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val res = loginRepository.postLogin(loginRequest);
                _login.postValue(LoginState.Successful(res))
            } catch (ex: Exception) {
                _login.postValue(LoginState.Failure)
            }
        }
    }
}