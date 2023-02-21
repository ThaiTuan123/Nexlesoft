package com.example.loginapplicationnl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class LoginResponses(
    @SerializedName("_id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("admin")
    val admin: Boolean,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("refreshToken")
    val freshToken: String,
) : Parcelable