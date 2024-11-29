package com.example.tecnisis.data.user

import com.google.gson.annotations.SerializedName

data class UserRoleResponse(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("rol_id")
    val rolId: Long
)
