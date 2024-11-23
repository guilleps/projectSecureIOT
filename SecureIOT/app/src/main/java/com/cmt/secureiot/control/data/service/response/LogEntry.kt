package com.cmt.secureiot.control.data.service.response

data class LogEntry(
    val timestamp: String,
    val category: String,
    val location: String,
    val action: String
)
