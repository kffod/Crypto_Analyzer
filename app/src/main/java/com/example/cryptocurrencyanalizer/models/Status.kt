package com.example.cryptocurrencyanalizer.models

data class Status(
    val credit_count: Double,
    val elapsed: String,
    val error_code: String,
    val error_message: String,
    val timestamp: String
)