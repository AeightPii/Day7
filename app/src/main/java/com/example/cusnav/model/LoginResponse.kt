package com.example.cusnav.model

data class LoginResponse(
    val message: String,
    val user: List<UserX>
)