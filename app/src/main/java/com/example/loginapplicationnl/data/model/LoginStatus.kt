package com.example.loginapplicationnl.data.model

data class LoginStatus(
    val errors: Errors,
    val statusCode: Int,
    val success: Boolean
)