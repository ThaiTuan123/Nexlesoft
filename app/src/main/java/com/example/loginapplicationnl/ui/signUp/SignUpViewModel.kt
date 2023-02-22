package com.example.loginapplicationnl.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapplicationnl.base.BaseViewModel
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.model.SignUpRequest
import com.example.loginapplicationnl.data.repository.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpRepository: SignUpRepository) : BaseViewModel() {

    private val _signup = MutableLiveData<SignUpState>()
    val signup: LiveData<SignUpState>
        get() = _signup

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
}