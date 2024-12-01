package com.example.tecnisis.data.user

import com.google.gson.annotations.SerializedName

data class UserRoleResponse(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("role_id")
    val rolId: Long
)
