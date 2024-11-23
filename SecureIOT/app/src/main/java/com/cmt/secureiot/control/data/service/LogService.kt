package com.cmt.secureiot.control.data.service

import android.annotation.SuppressLint
import com.cmt.secureiot.control.data.service.response.LogEntry
import javax.inject.Singleton

@Singleton
object LogService {
    private val logs = mutableListOf<LogEntry>()

    @SuppressLint("SimpleDateFormat")
    fun addLog(category: String, location: String, action: String) {
        logs.add(LogEntry(timestamp = System.currentTimeMillis().toString(), category, location, action))
    }

    fun getLogs(): List<LogEntry> = logs

    fun clearLogs() = logs.clear()
}