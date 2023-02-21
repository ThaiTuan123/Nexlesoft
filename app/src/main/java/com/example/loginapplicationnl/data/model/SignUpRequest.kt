package com.example.loginapplicationnl.data.model

import com.google.gson.annotations.SerializedName

class SignUpRequest(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)

